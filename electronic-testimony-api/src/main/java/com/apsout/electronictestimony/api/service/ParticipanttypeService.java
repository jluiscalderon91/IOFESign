package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Participanttype;

import java.util.List;

public interface ParticipanttypeService {

    List<Participanttype> findInitialAll();

    List<Participanttype> findAll();

    Participanttype getBy(int participanttypeId);
}
