package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Personworkflow;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.util.statics.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PersonworkflowAllocator {
    private static final Logger logger = LoggerFactory.getLogger(PersonworkflowAllocator.class);

    public static Personworkflow build(Person person, Workflow workflow) {
        Personworkflow personworkflow = new Personworkflow();
        personworkflow.setPersonId(person.getId());
        personworkflow.setPersonByPersonId(person);
        personworkflow.setWorkflowId(workflow.getId());
        personworkflow.setWorkflowByWorkflowId(workflow);
        ofPostMethod(personworkflow);
        return personworkflow;
    }

    public static void ofPostMethod(Personworkflow personworkflow) {
        personworkflow.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        personworkflow.setActive(States.ACTIVE);
        personworkflow.setDeleted(States.EXISTENT);
    }

}
