package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoleAllocator {

    public static int getRoleIdOf(Person person) {
        if (person.getUsersById() != null) {
            if (!person.getUsersById().isEmpty()) {
                return person.getUsersById().iterator().next().getUserrolesById().iterator().next().getRoleByRoleId().getId();
            } else {
                return -1;
            }
        }
        return -1;
    }

    public static Role build(Role role) {
        role.setEditable(States.EDITABLE);
        ofPostMethod(role);
        return role;
    }

    public static void ofPostMethod(Role role) {
        role.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        role.setActive(States.ACTIVE);
        role.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Role oldRole, Role newRole) {
        newRole.setName(oldRole.getName());
        newRole.setAbbreviation(oldRole.getAbbreviation());
        newRole.setActive(oldRole.getActive());
        newRole.setDescription(oldRole.getDescription());
    }

    public static void forDelete(Role role) {
        role.setDeleted(States.DELETED);
    }
}
