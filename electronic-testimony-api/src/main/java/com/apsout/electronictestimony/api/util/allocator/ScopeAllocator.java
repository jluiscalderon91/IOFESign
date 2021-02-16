package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Scope;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

public class ScopeAllocator {
    public static void ofPostMethod(Scope scope) {
        scope.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        scope.setActive(States.ACTIVE);
        scope.setDeleted(States.EXISTENT);
    }

    public static Scope build(Person person) {
        Scope scope = new Scope();
        scope.setPersonId(person.getId());
        scope.setParticipantType(person.getScope());
        ofPostMethod(scope);
        return scope;
    }

    public static void forUpdate(Scope scopeDb, Person person) {
        scopeDb.setParticipantType(person.getScope());
    }

    public static int getScopeIdOf(Person person) {
        if (person.getScope() == null)
            return person.getScopesById().iterator().next().getId();
        else
            return person.getScope();
    }
}
