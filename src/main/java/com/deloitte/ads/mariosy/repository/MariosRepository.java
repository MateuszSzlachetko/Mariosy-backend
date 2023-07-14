package com.deloitte.ads.mariosy.repository;

import com.deloitte.ads.mariosy.entity.Marios;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MariosRepository extends CrudRepository<Marios, Long> {
}
