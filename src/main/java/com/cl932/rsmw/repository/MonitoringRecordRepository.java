package com.cl932.rsmw.repository;

import com.cl932.rsmw.entity.MonitoringRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringRecordRepository extends CrudRepository<MonitoringRecord, Long> {
    @Query("from MonitoringRecord m " +
            "order by m.id desc" +
            "   limit 1")
    MonitoringRecord getLast();
}
