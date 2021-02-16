package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Enterprise;
import com.apsout.electronictestimony.api.entity.Mailtemplate;

import java.util.Optional;

public interface MailtemplateService {

    Optional<Mailtemplate> findBy(int enterpriseId, int type, int recipientType);

    Optional<Mailtemplate> findBy(Enterprise enterprise, int type, int recipientType);

    Mailtemplate findReplacedBy(int enterpriseId, int type, int recipientType, int documentId);
}
