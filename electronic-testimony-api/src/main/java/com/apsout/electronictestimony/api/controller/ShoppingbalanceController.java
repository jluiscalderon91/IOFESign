package com.apsout.electronictestimony.api.controller;

import com.apsout.electronictestimony.api.entity.Shoppingcard;
import com.apsout.electronictestimony.api.entity.model.ShoppingcardModel;
import com.apsout.electronictestimony.api.modelassembler.ShoppingcardModelAssembler;
import com.apsout.electronictestimony.api.service.AccessResourceService;
import com.apsout.electronictestimony.api.service.HistoricalbalanceallocationService;
import com.apsout.electronictestimony.api.service.ShoppingcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class ShoppingbalanceController {

    @Autowired
    private ShoppingcardService shoppingcardService;
    @Autowired
    private AccessResourceService accessResourceService;
    @Autowired
    private HistoricalbalanceallocationService historicalbalanceallocationService;

    @PreAuthorize("true")
    @PostMapping(value = "/shoppingbalances")
    public ResponseEntity save(@RequestBody Shoppingcard shoppingcard, HttpServletRequest request) {
        accessResourceService.validateIfBelong2EnterpriseOfShoppingcardId(request, shoppingcard.getId());
        shoppingcardService.register(shoppingcard, request);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("true")
    @GetMapping(value = "/partners/{partnerId}/shoppingbalances")
    public ResponseEntity<CollectionModel<ShoppingcardModel>> buy(@PathVariable int partnerId,
                                                                  HttpServletRequest request) {
        accessResourceService.validateIfBelong2Partner(request, partnerId);
        List<Shoppingcard> shoppingcards = shoppingcardService.findByPartnerId(partnerId);
        ShoppingcardModelAssembler assembler = new ShoppingcardModelAssembler();
        final CollectionModel<ShoppingcardModel> model = assembler.toCollectionModel(shoppingcards);
        return ResponseEntity.ok(model);
    }
}
