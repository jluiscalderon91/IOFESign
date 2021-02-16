package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.common.MoreAboutEnterprise;
import com.apsout.electronictestimony.api.exception.EnterpriseFoundedException;
import com.apsout.electronictestimony.api.exception.EnterpriseNotFoundException;
import com.apsout.electronictestimony.api.repository.EnterpriseRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.allocator.EnterpriseAllocator;
import com.apsout.electronictestimony.api.util.allocator.JobAllocator;
import com.apsout.electronictestimony.api.util.statics.Default;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    @Autowired
    private EnterpriseRepository repository;
    @Autowired
    private DocumentresourceService documentresourceService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private JobService jobService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private PersonService personService;

    public Enterprise save(Enterprise enterprise) {
        return repository.save(enterprise);
    }

    @Override
    @Transactional
    public Enterprise register(int partnerId, Enterprise enterprise, HttpServletRequest request) {
        Person person = personService.getBy(request);
        Partner partner = partnerService.getBy(partnerId);
        EnterpriseAllocator.build(enterprise, person);
        Optional<Enterprise> optional = this.findBy(partner, enterprise.getDocumentNumber());
        if (!optional.isPresent()) {
            final Enterprise saved = save(enterprise);
            saveInitialJobFor(saved);
            return saved;
        }
        throw new EnterpriseFoundedException("La empresa ya se encuentra registrada.");
    }

    @Override
    public Enterprise getBy(int enterpriseId) {
        Optional<Enterprise> optional = repository.findById(enterpriseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EnterpriseNotFoundException(String.format("Enterprise not found for enterpriseId: %d", enterpriseId));
    }

    @Override
    public Optional<Enterprise> findBy(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Enterprise getBy(String documentNumber) {
        Optional<Enterprise> optional = findBy(documentNumber);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EnterpriseNotFoundException(String.format("Enterprise not found for documentNumber: %s", documentNumber));
    }

    @Override
    public Page<Enterprise> getAllBy(int partnerId, String onlyCustomers, String term2Search, Pageable pageable) {
        Page<Enterprise> enterprises;
        boolean isCustomer = onlyCustomers.equals("yes");
        if (term2Search.isEmpty()) {
            if (onlyCustomers.equals("all")) {
                enterprises = repository.findAllByPartnerIdAndActiveAndDeleted(partnerId, States.ACTIVE, States.EXISTENT, pageable);
            } else {
                enterprises = repository.findAllByPartnerIdAndIsCustomerAndActiveAndDeleted(partnerId, isCustomer, States.ACTIVE, States.EXISTENT, pageable);
            }
        } else {
            if (onlyCustomers.equals("all")) {
                enterprises = repository.findAllBy(partnerId, term2Search, States.ACTIVE, States.EXISTENT, pageable);
            } else {
                enterprises = repository.findAllBy(partnerId, isCustomer, term2Search, States.ACTIVE, States.EXISTENT, pageable);
            }
        }
        enterprises.stream().forEach(enterprise -> {
            Optional<Headbalanceallocation> optional = headbalanceallocationService.findFirstAvailableBy(enterprise);
            MoreAboutEnterprise moreAboutEnterprise = new MoreAboutEnterprise();
            loadMoreInfo(moreAboutEnterprise, optional);
            enterprise.setMoreAboutEnterprise(moreAboutEnterprise);
        });
        return enterprises;
    }

    @Override
    @Transactional
    public Enterprise update(Enterprise enterprise, HttpServletRequest request) {
        Person person = personService.getBy(request);
        Partner partner = partnerService.getBy(enterprise.getPartnerId());
        Enterprise enterpriseDb = getBy(enterprise.getId());
        Optional<Enterprise> optional = this.findBy(partner, enterprise.getDocumentNumber());
        if (optional.isPresent()) {
            EnterpriseAllocator.forUpdate(enterpriseDb, enterprise, person);
            repository.save(enterpriseDb);
            logger.info(String.format("Enterprise updated with enterpriseId: %d", enterpriseDb.getId()));
            return enterpriseDb;
        }
        throw new EnterpriseNotFoundException(String.format("Enterprise not found by partnerId: %d, documentNumber: %s", partner.getId(), enterprise.getDocumentNumber()));
    }

    @Override
    @Transactional
    public Enterprise delete(Enterprise enterprise) {
        enterprise.setDeleted(States.DELETED);
        logger.info(String.format("Enterprise deleted with enterpriseId: %d", enterprise.getId()));
        return repository.save(enterprise);
    }

    @Override
    public List<Enterprise> findAllActive() {
        return repository.findAllByActiveAndDeleted(States.ACTIVE, States.EXISTENT);
    }

    @Override
    public List<Enterprise> findAll() {
        return repository.findAllByDeletedOrderByExcludedDescNameAsc(States.EXISTENT)
                .stream()
                .filter(enterprise -> Default.ENTERPRISE_ID_VIEW != enterprise.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Enterprise> findAllBy(int partnerId) {
        return repository.findAllByPartnerIdAndActiveAndDeletedOrderByExcludedDescNameAsc(partnerId, States.ACTIVE, States.EXISTENT)
                .stream()
                .filter(enterprise -> Default.ENTERPRISE_ID_VIEW != enterprise.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Enterprise> getAllBy(int enterpriseId, int participantType, String nameOrDocnumber, HttpServletRequest request) {
        return repository.findByDistinctEnterpriseIdAndLikeNameOrDocumentnumber(enterpriseId, nameOrDocnumber);
    }

    public Enterprise findOf(int resourceId) {
        Resource resource = resourceService.getBy(resourceId);
        Documentresource documentresource = documentresourceService.getBy(resource);
        Document document = documentresource.getDocumentByDocumentId();
        Person person = document.getPersonByPersonId();
        return person.getEnterpriseByEnterpriseIdView();
    }

    private void saveInitialJobFor(Enterprise enterprise) {
        Job job = JobAllocator.buildInitialJob(enterprise);
        jobService.save(job);
    }

    // TODO warning, rewrite
    @Override
    public List<Integer> getEnterpriseIdsBy(Enterprise enterprise) {
        if (States.EXCLUDED == enterprise.getExcluded()) {
            return this.findAllBy(enterprise.getPartnerId())
                    .stream()
                    .filter(enterprise1 -> States.EXCLUDED != enterprise1.getExcluded() || Default.ENTERPRISE_ID_VIEW == enterprise1.getId())
                    .map(enterprise1 -> enterprise1.getId())
                    .collect(Collectors.toList());
        }
        return Arrays.asList(enterprise.getId());
    }

    @Override
    public List<Integer> getEnterpriseIdsBy(int pertnerId, Enterprise enterprise) {
        if (States.EXCLUDED == enterprise.getExcluded()) {
            return this.findAllBy(pertnerId)
                    .stream()
                    .filter(enterprise1 -> States.EXCLUDED != enterprise1.getExcluded() || Default.ENTERPRISE_ID_VIEW == enterprise1.getId())
                    .map(enterprise1 -> enterprise1.getId())
                    .collect(Collectors.toList());
        }
        return Arrays.asList(enterprise.getId());
    }

    private void loadMoreInfo(MoreAboutEnterprise moreAboutEnterprise, Optional<Headbalanceallocation> optional) {
        if (optional.isPresent()) {
            Headbalanceallocation headbalanceallocation = optional.get();
            moreAboutEnterprise.setBalance(headbalanceallocation.getBalance());
        } else {
            moreAboutEnterprise.setBalance(new BigDecimal(0.0));
        }
    }

    @Override
    public Enterprise getBy(HttpServletRequest request) {
        return personService.getBy(request).getEnterpriseByEnterpriseId();
    }

    public Optional<Enterprise> findPartnerEnterpriseBy(int partnerId) {
        return repository.findPartnerEnterpriseBy(partnerId);
    }

    public Optional<Enterprise> findBy(Partner partner, String documentNumber) {
        return repository.findBy(partner, documentNumber);
    }
}
