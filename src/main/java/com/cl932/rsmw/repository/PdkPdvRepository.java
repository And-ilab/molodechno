package com.cl932.rsmw.repository;

import com.cl932.rsmw.entity.PdkPdv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PdkPdvRepository extends CrudRepository<PdkPdv, Long> {

}
