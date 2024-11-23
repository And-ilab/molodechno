package com.cl932.rsmw.repository;

import com.cl932.rsmw.entity.CurrentValue;
import com.cl932.rsmw.entity.PeriodRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRecordRepository extends CrudRepository<PeriodRecord, Long> {
    @Query("from PeriodRecord pr where pr.timestamp like :ts")
    public List<PeriodRecord> getYesterday(@Param("ts") String name);
}
