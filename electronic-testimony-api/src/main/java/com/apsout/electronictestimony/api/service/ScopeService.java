package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Scope;

public interface ScopeService {
    Scope save(Scope scope);

    Scope getBy(int personId);

    Scope findBy(int personId);
}
