package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.entity.model.UserModel;
import com.apsout.electronictestimony.api.entity.security.User;

public class UserModelAssembler {

    public UserModel toModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setUsername(user.getUsername());
        return userModel;
    }
}
