package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Shoppingcard;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ShoppingcardService {

    Optional<Shoppingcard> findBy(int shoppingcardId);

    Shoppingcard getBy(int shoppingcardId);

    List<Shoppingcard> findByPartnerId(int partnerId);

    void register(Shoppingcard shoppingcard, HttpServletRequest request);
}
