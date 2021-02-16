package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Passwordretriever;

import java.util.Optional;

public interface PasswordretrieverService {

    Optional<Passwordretriever> findBy(String uuid);

    Passwordretriever save(Passwordretriever passwordretriever);

    Passwordretriever getUUID();

    Passwordretriever sent(String uuid, String username);

    Passwordretriever verifyCode(String uuid, String hash, String verificationCode);

    Optional<Passwordretriever> findBy(byte sent);

    Optional<Passwordretriever> find4Send();

    void notificate();

    Optional<Passwordretriever> findBy(String uuid, String hashFirstStep, String verificationCode);

    Optional<Passwordretriever> findBy(String hash1, String hash2);

    Passwordretriever getBy(String hash1, String hash2);

    void validate(String hash1);

    void validate(String hash1, String hash2);

    void update(String hash1, String hash2, String newPassword);
}
