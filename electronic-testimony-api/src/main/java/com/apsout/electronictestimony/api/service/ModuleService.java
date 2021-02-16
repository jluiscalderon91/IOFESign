package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleService {

    List<Module> findInitialAll();

    List<Module> findAll();

    Optional<Module> findBy(int moduleId);

    Module getBy(int moduleId);
}
