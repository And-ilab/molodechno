package com.cl932.rsmw.repository;

import com.cl932.rsmw.entity.CurrentValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentRepository extends CrudRepository<CurrentValue, Long> {
}
