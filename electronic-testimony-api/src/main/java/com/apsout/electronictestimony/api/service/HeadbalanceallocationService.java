package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Headbalanceallocation;
import com.apsout.electronictestimony.api.entity.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface HeadbalanceallocationService {

    Headbalanceallocation register(Headbalanceallocation headbalanceallocation, HttpServletRequest request);

    Headbalanceallocation save(Headbalanceallocation headbalanceallocation);

    Optional<Headbalanceallocation> findFirstAvailableBy(int enterpriseId);

    Optional<Headbalanceallocation> findFirstAvailableBy(Enterprise enterprise);

    void consumeBalance(Enterprise enterprise, Document document, Person person, com.apsout.electronictestimony.api.util.enums.Service service);

    Headbalanceallocation transferBalance(Person person, Headbalanceallocation headbalanceallocation, Enterprise enterpriseSource, Enterprise enterpriseTarget, boolean isSuper, boolean isPartner);
}
