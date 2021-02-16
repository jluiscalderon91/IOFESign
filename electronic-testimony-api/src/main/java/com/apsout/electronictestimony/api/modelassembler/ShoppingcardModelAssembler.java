package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.HeadbalanceallocationController;
import com.apsout.electronictestimony.api.entity.Shoppingcard;
import com.apsout.electronictestimony.api.entity.model.ShoppingcardModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class ShoppingcardModelAssembler extends RepresentationModelAssemblerSupport<Shoppingcard, ShoppingcardModel> {

    public ShoppingcardModelAssembler() {
        super(HeadbalanceallocationController.class, ShoppingcardModel.class);
    }

    @Override
    public ShoppingcardModel toModel(Shoppingcard shoppingcard) {
        ShoppingcardModel model = instantiateModel(shoppingcard);
        model.setId(shoppingcard.getId());
        model.setPartnerId(shoppingcard.getPartnerId());
        model.setDescription(shoppingcard.getDescription());
        model.setQuantity(shoppingcard.getQuantity());
        model.setPrice(shoppingcard.getPrice());
        model.setOrderCard(shoppingcard.getOrderCard());
        model.setCreateAt(shoppingcard.getCreateAt());
        model.setActive(shoppingcard.getActive());
        return model;
    }
}
