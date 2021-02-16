package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Stationcounter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface StationcounterService {

    Optional<Stationcounter> findBy(int stationcounterId);

    Stationcounter getBy(int stationcounterId);

    Stationcounter save(Stationcounter stationcounter, HttpServletRequest request);

    Stationcounter update(Stationcounter stationcounter);

    Stationcounter onlysave(Stationcounter stationcounter);
}
