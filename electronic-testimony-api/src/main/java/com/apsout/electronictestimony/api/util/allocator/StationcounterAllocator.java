package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Stationcounter;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StationcounterAllocator {

    public static Stationcounter build(Stationcounter stationcounter, Person person, Workflow workflow) {
        Stationcounter stationcounter1 = new Stationcounter();
        stationcounter1.setType(stationcounter.getType());
        stationcounter1.setInitialAmount(stationcounter.getInitialAmount());
        stationcounter1.setPersonId(person.getId());
        stationcounter1.setPersonByPersonId(person);
        stationcounter1.setWorkflowId(workflow.getId());
        stationcounter1.setWorkflowByWorkflowId(workflow);
        stationcounter1.setCompleted(States.INCOMPLETE);
        ofPostMethod(stationcounter1);
        return stationcounter1;
    }

    public static void forUpdate(Stationcounter oldStationcounter, Stationcounter newStationcounter) {
        newStationcounter.setFinalAmount(oldStationcounter.getFinalAmount());
        newStationcounter.setCompleted(States.COMPLETED);
        newStationcounter.setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static void ofPostMethod(Stationcounter stationcounter) {
        stationcounter.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stationcounter.setActive(States.ACTIVE);
        stationcounter.setDeleted(States.EXISTENT);
    }
}
