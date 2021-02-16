package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Profileupdateattempt;

import java.util.List;

public interface ProfileupdateattemptService {

    Profileupdateattempt save(Profileupdateattempt profileupdateattempt);

    List<Profileupdateattempt> findLastTwoBy(int personId);
}
