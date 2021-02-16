package com.apsout.electronictestimony.api.repository.security;

import com.apsout.electronictestimony.api.entity.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface UsertokenRepository extends CrudRepository<Role, Integer> {
}
