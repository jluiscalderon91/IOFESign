package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.model.pojo._Embedded;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface ApplicationService {
    String add(int personId, String identifiers);

    HashMap<String, String> remove(String uuid);

    HashMap<String, String> get(String uuid);

    List<Integer> getIdentifiers(String uuid);

    Integer getPersonIdBy(String uuid);

    _Embedded getStaticData(HttpServletRequest request);

    _Embedded getStaticData4Outside(HttpServletRequest request);

    String getUUID();

    void reloadStaticData();

    _Embedded getActualBalance(HttpServletRequest request);
}
