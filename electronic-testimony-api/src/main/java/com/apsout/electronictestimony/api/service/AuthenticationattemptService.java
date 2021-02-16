package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Authenticationattempt;
import com.apsout.electronictestimony.api.entity.security.User;

import java.util.List;

public interface AuthenticationattemptService {

    Authenticationattempt save(Authenticationattempt authenticationattempt);

    List<Authenticationattempt> findLastBy(User user, int numRows);
}
