package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Numbersignature;

import java.util.Optional;

public interface NumbersignatureService {

    Numbersignature save(Numbersignature numbersignature);

    Optional<Numbersignature> findBy(Document document, String hashResource);
}
