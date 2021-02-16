package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.common.MoreAboutHistoricalbalanceallocation;
import com.apsout.electronictestimony.api.repository.HistoricalbalancedallocationRepository;
import com.apsout.electronictestimony.api.service.EnterpriseService;
import com.apsout.electronictestimony.api.service.HistoricalbalanceallocationService;
import com.apsout.electronictestimony.api.util.allocator.HistoricalbalanceallocationAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HistoricalbalanceallocationServiceImpl implements HistoricalbalanceallocationService {
    private static final Logger logger = LoggerFactory.getLogger(HistoricalbalanceallocationServiceImpl.class);

    @Autowired
    private HistoricalbalancedallocationRepository repository;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public Historicalbalanceallocation register(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance) {
        Historicalbalanceallocation historicalbalanceallocation = HistoricalbalanceallocationAllocator.build(headbalanceallocationId, person, enterpriseIdAction, quantity, balance);
        return this.save(historicalbalanceallocation);
    }

    @Override
    public Historicalbalanceallocation register(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance, byte isReturn) {
        Historicalbalanceallocation historicalbalanceallocation = HistoricalbalanceallocationAllocator.build(headbalanceallocationId, person, enterpriseIdAction, quantity, balance, isReturn);
        return this.save(historicalbalanceallocation);
    }

    @Override
    public Historicalbalanceallocation save(Historicalbalanceallocation historicalbalanceallocation) {
        return repository.save(historicalbalanceallocation);
    }

    @Override
    public List<Historicalbalanceallocation> findAllBy(int enterpriseId) {
        final List<Historicalbalanceallocation> historicals = repository.findAllBy(enterpriseId);
        historicals.stream().forEach(historicalbalanceallocation -> loadMoreInfo(historicalbalanceallocation));
        return historicals;
    }

    private void loadMoreInfo(Historicalbalanceallocation historicalbalanceallocation) {
        MoreAboutHistoricalbalanceallocation more = new MoreAboutHistoricalbalanceallocation();
        int enterpriseIdAaction = historicalbalanceallocation.getEnterpriseIdAction();
        Enterprise enterprise = enterpriseService.getBy(enterpriseIdAaction);
        more.setEnterpriseName(enterprise.getName());
        historicalbalanceallocation.setMoreAboutHistoricalbalanceallocation(more);
    }

    @Override
    public Page<Historicalbalanceallocation> findAllBy(int enterpriseId, Pageable pageable) {
        final Page<Historicalbalanceallocation> historicals = repository.findAllBy(enterpriseId, pageable);
        historicals.stream().forEach(historicalbalanceallocation -> loadMoreInfo(historicalbalanceallocation));
        return historicals;
    }
}
