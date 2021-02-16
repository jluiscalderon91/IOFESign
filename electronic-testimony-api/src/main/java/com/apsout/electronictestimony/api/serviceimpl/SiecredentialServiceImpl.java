package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.entity.Siecredential;
import com.apsout.electronictestimony.api.entity.common.MoreAboutSiecredential;
import com.apsout.electronictestimony.api.exception.SiecredentialNotFoundException;
import com.apsout.electronictestimony.api.repository.SiecredentialRepository;
import com.apsout.electronictestimony.api.service.EnterpriseService;
import com.apsout.electronictestimony.api.service.SiecertificateService;
import com.apsout.electronictestimony.api.service.SiecredentialService;
import com.apsout.electronictestimony.api.util.allocator.SiecertificateAllocator;
import com.apsout.electronictestimony.api.util.allocator.SiecredentialAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SiecredentialServiceImpl implements SiecredentialService {
    private static final Logger logger = LoggerFactory.getLogger(SiecredentialServiceImpl.class);
    @Autowired
    private SiecredentialRepository repository;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private SiecertificateService siecertificateService;

    @Override
    public Siecredential save(Siecredential siecredential) {
        return repository.save(siecredential);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Siecredential save(int enterpriseId, String username, String password, MultipartFile certificate, String passwordCertificate) {
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        Optional<Siecredential> optional = this.findLastActiveBy(enterprise);
        int updatedRows = this.disableAllBy(enterprise);
        Siecredential siecredential = SiecredentialAllocator.build(enterprise, optional, username, password);
        this.save(siecredential);
        int updatedRows1 = siecertificateService.disableAllBy(enterprise);
        Siecertificate siecertificate = SiecertificateAllocator.build(siecredential, certificate, passwordCertificate);
        siecertificateService.save(siecertificate);
        return siecredential;
    }

    @Override
    @Transactional
    public Integer disableAllBy(Enterprise enterprise) {
        return repository.disableAllBy(enterprise.getId());
    }

    public Optional<Siecredential> findLastActiveBy(Enterprise enterprise) {
        return repository.findFirstByEnterpriseByEnterpriseIdAndActiveAndDeletedOrderByIdDesc(enterprise, States.ACTIVE, States.EXISTENT);
    }

    public Page<Siecredential> getBy(int enterpriseId, Pageable pageable) {
        Enterprise enterprise = enterpriseService.getBy(enterpriseId);
        List<Integer> enterpriseIds = enterpriseService.getEnterpriseIdsBy(enterprise);
        final Page<Siecredential> siecredentialPage = repository.findBy(enterpriseIds, pageable);
        siecredentialPage.getContent().stream().forEach(siecredential -> {
            Enterprise enterprise1 = siecredential.getEnterpriseByEnterpriseId();
            Siecertificate siecertificate = siecertificateService.getBy(siecredential);
            MoreAboutSiecredential moreAboutSiecredential = new MoreAboutSiecredential();
            SiecredentialAllocator.loadSieCertInfo(enterprise1, siecertificate, moreAboutSiecredential);
            siecredential.setMoreAboutSiecredential(moreAboutSiecredential);
        });
        return siecredentialPage;
    }

    public Siecredential getLastActiveBy(Enterprise enterprise) {
        Optional<Siecredential> optional = findLastActiveBy(enterprise);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new SiecredentialNotFoundException(String.format("Config SIE not found, siecredential not found for enterpriseId: %d", enterprise.getId()));
    }
}
