package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Detailbalanceallocation;
import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.common.MoreAboutDetailbalance;
import com.apsout.electronictestimony.api.repository.DetailbalancedallocationRepository;
import com.apsout.electronictestimony.api.service.DetailbalanceallocationService;
import com.apsout.electronictestimony.api.service.DocumentService;
import com.apsout.electronictestimony.api.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailbalanceallocationServiceImpl implements DetailbalanceallocationService {
    private static final Logger logger = LoggerFactory.getLogger(DetailbalanceallocationServiceImpl.class);

    @Autowired
    private DetailbalancedallocationRepository repository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private DocumentService documentService;

    @Override
    public Detailbalanceallocation save(Detailbalanceallocation detailbalanceallocation) {
        return repository.save(detailbalanceallocation);
    }

    @Override
    public Page<Detailbalanceallocation> findAllBy(int enterpriseId, Pageable page) {
        final Page<Detailbalanceallocation> detailbalanceallocations = repository.findAllBy(enterpriseId, page);
        this.loadMoreInfoDetailbalance(detailbalanceallocations.getContent());
        return detailbalanceallocations;
    }

    private void loadMoreInfoDetailbalance(List<Detailbalanceallocation> detailbalanceallocations) {
        detailbalanceallocations.stream().forEach(detailbalanceallocation -> {
            MoreAboutDetailbalance moreAboutDetailbalance = new MoreAboutDetailbalance();
            Document document = detailbalanceallocation.getDocumentByDocumentId();
            moreAboutDetailbalance.setSubjectDocument(document.getSubject());
            detailbalanceallocation.setMoreAboutDetailbalance(moreAboutDetailbalance);
        });
    }
}
