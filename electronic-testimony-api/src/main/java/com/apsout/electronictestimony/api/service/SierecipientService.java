package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Sieemail;
import com.apsout.electronictestimony.api.entity.Sierecipient;

import java.util.List;
import java.util.Optional;

public interface SierecipientService {
    Sierecipient save(Sierecipient sierecipient);

    List<Sierecipient> save(List<Sierecipient> sierecipients);

    Optional<Sierecipient> findBy(int sieemailId, String address);

    List<Sierecipient> update(List<Sierecipient> sierecipients);

    List<Sierecipient> save(Sieemail sieemail);

    List<Sierecipient> getAllBy(Sieemail sieemail);
}
