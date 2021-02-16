package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.exception.InexistentBalanceException;
import com.apsout.electronictestimony.api.exception.InsuficientBalanceException;
import com.apsout.electronictestimony.api.repository.HeadbalancedallocationRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.DetailbalanceallocationAllocator;
import com.apsout.electronictestimony.api.util.allocator.HeadbalanceallocationAllocator;
import com.apsout.electronictestimony.api.util.statics.Constant;
import com.apsout.electronictestimony.api.util.statics.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.apsout.electronictestimony.api.util.statics.Roles.PARTNER;
import static com.apsout.electronictestimony.api.util.statics.Roles.SUPERADMIN;

@Service
public class HeadbalanceallocationServiceImpl implements HeadbalanceallocationService {
    private static final Logger logger = LoggerFactory.getLogger(HeadbalanceallocationServiceImpl.class);

    @Autowired
    private HeadbalancedallocationRepository repository;
    @Autowired
    private ServiceweightService serviceweightService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private DetailbalanceallocationService detailbalanceallocationService;
    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private HistoricalbalanceallocationService historicalbalanceallocationService;
    @Autowired
    private PersonService personService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Headbalanceallocation register(Headbalanceallocation headbalanceallocation, HttpServletRequest request) {
        boolean isPartner = userService.requestUserHasRole(request, PARTNER);
        boolean isSuper = false;
        if (!isPartner) {
            isSuper = userService.requestUserHasRole(request, SUPERADMIN);
        }
        Person person = personService.getBy(request);
        Enterprise enterpriseSource = enterpriseService.getBy(request);
        Enterprise enterpriseTarget = enterpriseService.getBy(headbalanceallocation.getEnterpriseId());
        return transferBalance(person, headbalanceallocation, enterpriseSource, enterpriseTarget, isSuper, isPartner);
    }

    @Override
    public Headbalanceallocation transferBalance(Person person, Headbalanceallocation headbalanceallocation, Enterprise enterpriseSource, Enterprise enterpriseTarget, boolean isSuper, boolean isPartner) {
        Optional<Headbalanceallocation> optionalOldTarget = this.findFirstAvailableBy(enterpriseTarget);
        if (optionalOldTarget.isPresent()) {
            if (isSuper) {
                this.updatePartnerBalance(Default.HEAD_BALLANCE_ALLOCATION_ID, headbalanceallocation, person);
            } else if (isPartner) {
                this.updatePartnerBalance(enterpriseSource.getId(), headbalanceallocation, person);
            }
            Headbalanceallocation headbalanceallocationOld = optionalOldTarget.get();
            Headbalanceallocation headbalanceallocationTarget = this.createNew(headbalanceallocationOld, headbalanceallocation);
            HeadbalanceallocationAllocator.forDisable(headbalanceallocationOld);
            this.save(headbalanceallocationOld);
            if (headbalanceallocation.getQuantity().floatValue() < 0) {
                historicalbalanceallocationService.register(headbalanceallocationOld.getId(), person, enterpriseSource.getId(), headbalanceallocation.getQuantity(), headbalanceallocationTarget.getBalance(), Constant.IS_RETURNED_BALANCE);
            } else {
                historicalbalanceallocationService.register(headbalanceallocationOld.getId(), person, enterpriseSource.getId(), headbalanceallocation.getQuantity(), headbalanceallocationTarget.getBalance());
            }
            logger.info(String.format("Balance allocated successfully for enterpriseId: %d", headbalanceallocationTarget.getEnterpriseId()));
            return this.save(headbalanceallocationTarget);
        } else {
            if (isSuper) {
                this.updatePartnerBalance(Default.HEAD_BALLANCE_ALLOCATION_ID, headbalanceallocation, person);
            } else if (isPartner) {
                this.updatePartnerBalance(enterpriseSource.getId(), headbalanceallocation, person);
            }
            HeadbalanceallocationAllocator.forInitialBalance(headbalanceallocation);
            headbalanceallocationService.save(headbalanceallocation);
            historicalbalanceallocationService.register(headbalanceallocation.getId(), person, enterpriseSource.getId(), headbalanceallocation.getBalance(), headbalanceallocation.getBalance());
        }
        logger.info(String.format("New balance allocated successfully for enterpriseId: %d", headbalanceallocation.getEnterpriseId()));
        return headbalanceallocation;
    }

    @Override
    public Headbalanceallocation save(Headbalanceallocation headbalanceallocation) {
        return repository.save(headbalanceallocation);
    }

    @Override
    public Optional<Headbalanceallocation> findFirstAvailableBy(int entepriseId) {
        return repository.findFirstAvailableBy(entepriseId);
    }

    @Override
    public Optional<Headbalanceallocation> findFirstAvailableBy(Enterprise enterprise) {
        return this.findFirstAvailableBy(enterprise.getId());
    }

    /**
     * Registra el consumo de saldo al firmar un recurso/documento de una empresa, en función al peso que tenga asignado.
     *
     * @param enterprise
     * @param document
     * @param person
     */
    @Override
    public void consumeBalance(Enterprise enterprise, Document document, Person person, com.apsout.electronictestimony.api.util.enums.Service service) {
        Serviceweight serviceweight = serviceweightService.getBy(service);
        BigDecimal weight = serviceweight.getWeight();
        Optional<Headbalanceallocation> optional1 = this.findFirstAvailableBy(enterprise);
        if (optional1.isPresent()) {
            Headbalanceallocation headbalanceallocation = optional1.get();
            Detailbalanceallocation detailbalanceallocation = DetailbalanceallocationAllocator.build(headbalanceallocation, document, person, serviceweight);
            detailbalanceallocationService.save(detailbalanceallocation);
            this.assignBalance(headbalanceallocation, -weight.floatValue(), false);
            this.save(headbalanceallocation);
            DetailbalanceallocationAllocator.forUpdate(detailbalanceallocation, headbalanceallocation.getBalance());
            logger.info(String.format("Successfull use balance for enterpriseId: %d, documentId: %d, resourceId: %d", enterprise.getId(), document.getId(), person.getId()));
        }
//        else {
//            logger.info(String.format("Client hasn't assigned balance: %s", enterprise.getName()));
//            throw new InexistentBalanceException(String.format("Client hasn't assigned balance: %s", enterprise.getName()));
//        }
    }

    private void assignBalance(Headbalanceallocation headbalanceallocation, float quantity, boolean reset) {
        BigDecimal actualBalance = headbalanceallocation.getBalance();
        if (quantity < 0) {
            float positiveQuantity = -quantity;
            if (actualBalance.floatValue() < positiveQuantity) {
                logger.info(String.format("Insuficient client balance for enterpriseId: %d", headbalanceallocation.getId()));
                throw new InsuficientBalanceException("Las cantidad de monedas que intenta transferir son mayores al saldo que usted dispone. Por favor comuníquese con el administrador de sistemas.");
            }
        }
        BigDecimal newBalance = new BigDecimal(actualBalance.floatValue() + quantity);
        if (reset) {
            BigDecimal newQuantity = newBalance;
            headbalanceallocation.setQuantity(newQuantity);
        }
        headbalanceallocation.setBalance(newBalance);
        headbalanceallocation.setLastUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    private Headbalanceallocation createNew(Headbalanceallocation old, Headbalanceallocation additional) {
        BigDecimal oldBalance = old.getBalance();
        BigDecimal additionalQuantity = additional.getQuantity();
        Headbalanceallocation headbalanceallocation = HeadbalanceallocationAllocator.build(old.getEnterpriseId());
        final float result = oldBalance.floatValue() + additionalQuantity.floatValue();
        if (additionalQuantity.floatValue() < 0) {
            if (result < 0) {
                logger.info(String.format("Insuficient client balance for enterpriseId: %d, balance: %.2f, require: %.2f", headbalanceallocation.getEnterpriseId(), oldBalance.floatValue(), additionalQuantity.floatValue()));
                throw new InsuficientBalanceException("Las cantidad de monedas que intenta transferir son mayores al saldo que usted dispone. Por favor comuníquese con el administrador de sistemas.");
            }
        }
        BigDecimal newQuantity = new BigDecimal(result);
        headbalanceallocation.setQuantity(newQuantity);
        headbalanceallocation.setBalance(newQuantity);
        return headbalanceallocation;
    }

    private void updatePartnerBalance(int enterpriseId, Headbalanceallocation headbalanceallocation, Person person) {
        Optional<Headbalanceallocation> optionalHeadbalanceallocationPartner = this.findFirstAvailableBy(enterpriseId);
        if (optionalHeadbalanceallocationPartner.isPresent()) {
            Headbalanceallocation headbalanceallocationPartner = optionalHeadbalanceallocationPartner.get();
            final float discount = headbalanceallocation.getQuantity().floatValue();
            if (discount < 0) {
                Optional<Headbalanceallocation> optionalHeadbalanceallocationClient = headbalanceallocationService.findFirstAvailableBy(headbalanceallocation.getEnterpriseId());
                if (!optionalHeadbalanceallocationClient.isPresent()) {
                    logger.info(String.format("Partner hasn't assigned balance for enterpriseId: %d", headbalanceallocation.getEnterpriseId()));
                    throw new InsuficientBalanceException("Las cantidad de monedas que intenta transferir son mayores al saldo que usted dispone. Por favor comuníquese con el administrador de sistemas.");
                }
                Headbalanceallocation headbalanceallocationClient = optionalHeadbalanceallocationClient.get();
                float positiveDiscount = -discount;
                if (headbalanceallocationClient.getBalance().floatValue() - positiveDiscount < 0) {
                    logger.info(String.format("Insuficient partner balance for enterpriseId: %d, balance: %.2f, require: %.2f", headbalanceallocation.getEnterpriseId(), headbalanceallocationPartner.getQuantity().floatValue(), discount));
                    throw new InsuficientBalanceException("Las cantidad de monedas que intenta transferir son mayores al saldo que usted dispone. Por favor comuníquese con el administrador de sistemas.");
                }
                this.assignBalance(headbalanceallocationPartner, -discount, false);
                historicalbalanceallocationService.register(headbalanceallocationPartner.getId(), person, headbalanceallocation.getEnterpriseId(), headbalanceallocation.getQuantity().negate(), headbalanceallocationPartner.getBalance(), Constant.IS_RETURNED_BALANCE);
                return;
            }
            this.assignBalance(headbalanceallocationPartner, -discount, false);
            historicalbalanceallocationService.register(headbalanceallocationPartner.getId(), person, headbalanceallocation.getEnterpriseId(), headbalanceallocation.getQuantity().negate(), headbalanceallocationPartner.getBalance());
        } else {
            logger.info(String.format("Partner hasn't assigned balance: %d", enterpriseId));
            throw new InsuficientBalanceException("Las cantidad de monedas que intenta transferir son mayores al saldo que usted dispone. Por favor comuníquese con el administrador de sistemas.");
        }
    }
}
