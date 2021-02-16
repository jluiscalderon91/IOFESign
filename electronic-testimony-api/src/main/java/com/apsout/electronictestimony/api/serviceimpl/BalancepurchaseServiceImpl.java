package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Balancepurchase;
import com.apsout.electronictestimony.api.repository.BalancepurchaseRepository;
import com.apsout.electronictestimony.api.service.BalancepurchaseService;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class BalancepurchaseServiceImpl implements BalancepurchaseService {
    private static final Logger logger = LoggerFactory.getLogger(BalancepurchaseServiceImpl.class);

    @Autowired
    private BalancepurchaseRepository repository;

    @Override
    public Balancepurchase save(Balancepurchase balancepurchase) {
        return repository.save(balancepurchase);
    }

    @Override
    public Page<Balancepurchase> findBy(int partnerId, String fromOnMilis, String toOnMilis, Pageable pageable) {
        long f = Long.parseLong(fromOnMilis);
        long t = Long.parseLong(toOnMilis);
        if (t < f) {
            throw new RuntimeException(String.format("Inconsistent info dates from: %s, to: %s", fromOnMilis, toOnMilis));
        }
        final LocalDate fd = Instant.ofEpochMilli(f).atZone(ZoneId.systemDefault()).toLocalDate();
        final LocalDate td = Instant.ofEpochMilli(t).atZone(ZoneId.systemDefault()).toLocalDate();
        String from = DateUtil.build(fd, "yyyy-MM-dd");
        String to = DateUtil.build(td, "yyyy-MM-dd");
        return repository.findBy(partnerId, from, to, pageable);
    }
}
