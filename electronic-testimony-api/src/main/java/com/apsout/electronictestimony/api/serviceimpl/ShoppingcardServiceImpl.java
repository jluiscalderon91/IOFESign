package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.exception.EnterpriseNotFoundException;
import com.apsout.electronictestimony.api.exception.ShoppingcardNotFoundException;
import com.apsout.electronictestimony.api.repository.ShoppingcardRepository;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.util.allocator.BalancepurchaseAllocator;
import com.apsout.electronictestimony.api.util.allocator.HeadbalanceallocationAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.apsout.electronictestimony.api.util.statics.Roles.PARTNER;

@Service
public class ShoppingcardServiceImpl implements ShoppingcardService {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingcardServiceImpl.class);

    @Autowired
    private ShoppingcardRepository repository;
    @Autowired
    private PersonService personService;
    @Autowired
    private BalancepurchaseService balancepurchaseService;
    @Autowired
    private HeadbalanceallocationService headbalanceallocationService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private UserService userService;

    public Optional<Shoppingcard> findBy(int shoppingcardId) {
        return repository.findBy(shoppingcardId);
    }

    public Shoppingcard getBy(int shoppingcardId) {
        Optional<Shoppingcard> optional = this.findBy(shoppingcardId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Shoppingcard not found with shoppingcardId: %d", shoppingcardId));
    }

    public List<Shoppingcard> findByPartnerId(int partnerId) {
        return repository.findAllByPartnerId(partnerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(Shoppingcard shoppingcard, HttpServletRequest request) {
        Person person = personService.getBy(request);
        Enterprise enterpriseTarget = person.getEnterpriseByEnterpriseIdView();
        shoppingcard.setPartnerId(enterpriseTarget.getPartnerId());
        Optional<Shoppingcard> optional = this.findBy(shoppingcard.getId());
        if (optional.isPresent()) {
            Shoppingcard shoppingcarddb = optional.get();
            Balancepurchase balancepurchase = BalancepurchaseAllocator.build(shoppingcard, enterpriseTarget, person);
            balancepurchaseService.save(balancepurchase);
            final Integer partnerId = shoppingcard.getPartnerId();
            Optional<Enterprise> optionalEnterprisePartner = enterpriseService.findPartnerEnterpriseBy(partnerId);
            if (optionalEnterprisePartner.isPresent()) {
                Enterprise enterpriseSource = optionalEnterprisePartner.get();
                boolean isPartner = userService.requestUserHasRole(request, PARTNER);
                Headbalanceallocation headbalanceallocation = HeadbalanceallocationAllocator.build(enterpriseTarget, balancepurchase.getQuantity());
                if (isPartner) {
                    headbalanceallocationService.transferBalance(person, headbalanceallocation, enterpriseSource, enterpriseTarget, true, false);
                } else {
                    headbalanceallocationService.transferBalance(person, headbalanceallocation, enterpriseSource, enterpriseTarget, false, true);
                }
                logger.info(String.format("Coins purchased successfully by enterpriseId: %d, quantity: %.2f ", enterpriseTarget.getId(), shoppingcarddb.getQuantity()));
            } else {
                throw new EnterpriseNotFoundException("Partner enterprise not found for partnerId: %d");
            }
        } else {
            throw new ShoppingcardNotFoundException(String.format("Shopping card not found for shoppingcardId: %d", shoppingcard.getId()));
        }
    }
}
