package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Siecredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SiecredentialService {

    Siecredential save(Siecredential siecredential);

    Siecredential save(int enterpriseId, String username, String password, MultipartFile certificate, String passwordCertificate);

    Integer disableAllBy(Enterprise enterprise);

    Optional<Siecredential> findLastActiveBy(Enterprise enterprise);

    Siecredential getLastActiveBy(Enterprise enterprise);

    Page<Siecredential> getBy(int enterpriseId, Pageable pageable);
}
