package com.deloitte.ads.mariosy.repository;

import com.deloitte.ads.mariosy.entity.Marios;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MariosRepository extends CrudRepository<Marios, Long> {
    Optional<Marios> findByExternalId(UUID externalId);
}
