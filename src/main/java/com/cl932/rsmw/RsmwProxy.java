package com.cl932.rsmw;

import com.cl932.rsmw.entity.CurrentValue;
import com.cl932.rsmw.util.MsParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class RsmwProxy {
    final
    MsParser msParser;

    public RsmwProxy(MsParser msParser) {
        this.msParser = msParser;
    }
    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void writeCurrent() throws SQLException {
        try {
            msParser.parseCurrent();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 */20 * * * *")
    public void writePeriod() {
        try {
            msParser.parsePeriod();
            msParser.flushCurrent();
            msParser.parseCurrent();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "30 57 23 * * *")
    public void writeDay() {
        try {
            msParser.parseDay();
            msParser.clearOldPeriod();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 0 */1 * * *")
//    public void check() {
//        var mr = msParser.getLast();
//        if (mr == null) {
//            writeDay();
//            return;
//        }
//        DateFormat df = new SimpleDateFormat("yyyyMMdd");
//        Date date = new Date();
//        if (mr.getTimestamp().split(" ")[0].equals(df.format(date))){
//
//            return;
//        }
//
//        writeDay();
//    }



}
