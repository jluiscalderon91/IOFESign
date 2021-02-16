package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Module;
import com.apsout.electronictestimony.api.repository.ModuleRepository;
import com.apsout.electronictestimony.api.service.ModuleService;
import com.apsout.electronictestimony.api.util.others.Localstorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {
    private static final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Autowired
    private ModuleRepository repository;

    @Override
    public List<Module> findInitialAll() {
        logger.info("Getting initial list of modules");
        return repository.findAll();
    }

    @Override
    public List<Module> findAll() {
        return Localstorage.modules.isEmpty() ? findInitialAll() : Localstorage.modules;
    }

    public Optional<Module> findBy(int moduleId) {
        return Localstorage.modules
                .stream()
                .filter(module -> module.getId().equals(moduleId))
                .findFirst();
    }

    public Module getBy(int moduleId) {
        Optional<Module> optional = this.findBy(moduleId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Module not found with moduleId: %d", moduleId));
    }
}
