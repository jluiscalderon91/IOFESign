package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Detailbalanceallocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DetailbalanceallocationService {

    Detailbalanceallocation save(Detailbalanceallocation detailbalanceallocation);

    Page<Detailbalanceallocation> findAllBy(int enterpriseId, Pageable page);
}
