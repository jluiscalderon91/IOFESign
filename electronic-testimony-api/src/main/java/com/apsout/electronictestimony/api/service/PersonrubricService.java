package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Personrubric;

import java.util.Optional;

public interface PersonrubricService {

    Personrubric save(Personrubric personrubric);

    Optional<Personrubric> findBy(Person person);
}
