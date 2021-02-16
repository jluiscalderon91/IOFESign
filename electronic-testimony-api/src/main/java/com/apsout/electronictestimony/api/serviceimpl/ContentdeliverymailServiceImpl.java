package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Contentdeliverymail;
import com.apsout.electronictestimony.api.entity.Deliverymail;
import com.apsout.electronictestimony.api.repository.ContentdeliverymailRepository;
import com.apsout.electronictestimony.api.service.ContentdeliverymailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentdeliverymailServiceImpl implements ContentdeliverymailService {
    private static final Logger logger = LoggerFactory.getLogger(ContentdeliverymailServiceImpl.class);

    @Autowired
    private ContentdeliverymailRepository repository;

    @Override
    public Contentdeliverymail save(Contentdeliverymail contentdeliverymail) {
        return repository.save(contentdeliverymail);
    }

    public Optional<Contentdeliverymail> findBy(int deliverymailId){
        return repository.findBy(deliverymailId);
    }

    @Override
    public Contentdeliverymail getBy(Deliverymail deliverymail) {
        final Optional<Contentdeliverymail> optional = this.findBy(deliverymail.getId());
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Contentdeliverymail not found by deliverymailId: %d", deliverymail.getId()));
    }
}
