package com.apsout.electronictestimony.api.util.converter;

import com.apsout.electronictestimony.api.entity.Deliverymail;
import com.apsout.electronictestimony.api.entity.model.DeliverymailModel;

public class DeliverymailConverter {

    public static Deliverymail doIt(DeliverymailModel model) {
        Deliverymail deliverymail = new Deliverymail();
        deliverymail.setDocumentId(deliverymail.getDocumentId());
        deliverymail.setContentdeliverymail(model.getContentdeliverymail());
        return deliverymail;
    }
}
