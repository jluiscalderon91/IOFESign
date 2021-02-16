package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Historicalbalanceallocation;
import com.apsout.electronictestimony.api.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface HistoricalbalanceallocationService {

    Historicalbalanceallocation register(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance);

    Historicalbalanceallocation register(int headbalanceallocationId, Person person, int enterpriseIdAction, BigDecimal quantity, BigDecimal balance, byte isReturn);

    Historicalbalanceallocation save(Historicalbalanceallocation historicalbalanceallocation);

    List<Historicalbalanceallocation> findAllBy(int enterpriseId);

    Page<Historicalbalanceallocation> findAllBy(int enterpriseId, Pageable page);
}
