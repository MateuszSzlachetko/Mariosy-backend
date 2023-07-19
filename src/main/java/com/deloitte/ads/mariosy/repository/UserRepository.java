package com.deloitte.ads.mariosy.repository;

import com.deloitte.ads.mariosy.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByExternalId(UUID externalId);
}
