package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface EnterpriseService {

    Enterprise save(Enterprise enterprise);

    Enterprise register(int partnerId, Enterprise enterprise, HttpServletRequest request);

    Enterprise getBy(int enterpriseId);

    Optional<Enterprise> findBy(String documentNumber);

    Enterprise getBy(String documentNumber);

    Page<Enterprise> getAllBy(int partnerId, String onlyCustomers, String term2Search, Pageable pageable);

    Enterprise update(Enterprise enterprise, HttpServletRequest request);

    Enterprise delete(Enterprise enterprise);

    List<Enterprise> findAll();

    List<Enterprise> findAllActive();

    List<Enterprise> getAllBy(int enterpriseId, int participantType, String nameOrDocnumber, HttpServletRequest request);

    Enterprise findOf(int resourceId);

    List<Integer> getEnterpriseIdsBy(Enterprise enterprise);

    List<Integer> getEnterpriseIdsBy(int partnerId, Enterprise enterprise);

    List<Enterprise> findAllBy(int partnerId);

    Enterprise getBy(HttpServletRequest request);

    Optional<Enterprise> findPartnerEnterpriseBy(int partnerId);

    Optional<Enterprise> findBy(Partner partner, String documentNumber);
}
