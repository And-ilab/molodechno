package com.cl932.rsmw.util;

import com.cl932.rsmw.entity.CurrentValue;
import com.cl932.rsmw.entity.MonitoringRecord;
import com.cl932.rsmw.entity.PeriodRecord;
import com.cl932.rsmw.repository.CurrentRepository;
import com.cl932.rsmw.repository.MonitoringRecordRepository;
import com.cl932.rsmw.repository.PdkPdvRepository;
import com.cl932.rsmw.repository.PeriodRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.DocAttributeSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MsParser {
    MsExtractor msExtractor;
    CurrentRepository currentRepository;
    PeriodRecordRepository periodRecordRepository;
    MonitoringRecordRepository monitoringRecordRepository;
    final
    PdkPdvRepository pdkPdvRepository;

    public MsParser(MsExtractor msExtractor, CurrentRepository currentRepository, PeriodRecordRepository periodRecordRepository, MonitoringRecordRepository monitoringRecordRepository, PdkPdvRepository pdkPdvRepository) {
        this.monitoringRecordRepository = monitoringRecordRepository;
        this.msExtractor = msExtractor;
        this.periodRecordRepository = periodRecordRepository;
        this.currentRepository = currentRepository;
        this.pdkPdvRepository = pdkPdvRepository;
    }

    public CurrentValue parseCurrent() throws SQLException {
        Map<String, String> values = msExtractor.extractFromReports();
        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        Date date = new Date();
//NOx = NO + (NO2кэф * NO)
//NO = NOx - 0.05NO NOx = 1.05NO
        Double concentration_NO = Double.parseDouble(values.get("Concentr_NOx").replace(",", ".")) / 1.05;
        Double concentration_NO2 = concentration_NO - Double.parseDouble(values.get("Concentr_NOx").replace(",", "."));
        Double emission_NOx = Double.parseDouble(values.get("Vibros_NO").replace(",", ".")) + Double.parseDouble(values.get("Vibros_NO2").replace(",", "."));
        double flueGasConsRU = Double.parseDouble(values.get("Analog_Flow_d").replace(",", ".")) / 3600.0;
        double k = 0.9;
        double P = 101.3;
        double a = 21/(21 - Double.parseDouble(values.get("Concentr_O2").replace(",", ".")));
        double tg = Double.parseDouble(values.get("Analog_T_d").replace(',', '.'));
        var pdz = pdkPdvRepository.findById(1L).get();

        Double flueGasConsNU = (flueGasConsRU * 1.4 * k * 273 * P) / (a * (273 + tg) * 101.3);
        CurrentValue currentValue = CurrentValue.builder()
                .timestamp(df.format(date))
                .concentration_CO(values.get("Concentr_CO").replace(",","."))
                .concentration_CO2(values.get("Concentr_CO2").replace(",","."))
                .concentration_NO(String.valueOf(concentration_NO))
                .concentration_NO2(String.valueOf(concentration_NO2))
                .concentration_NOx(values.get("Concentr_NOx").replace(",","."))
                .concentration_SO2(values.get("Concentr_SO2").replace(",","."))
                .concentration_CH4("0".replace(",","."))
                .concentration_O2(values.get("Concentr_O2").replace(",","."))
                .wasteGasTemperature(values.get("Analog_T_d").replace(",","."))
                .concentration_dust("0".replace(",","."))
                .emission_CO(values.get("Vibros_CO").replace(",","."))
                .emission_CO2(values.get("Vibros_CO2").replace(",","."))
                .emission_NO(values.get("Vibros_NO").replace(",","."))
                .emission_NO2(values.get("Vibros_NO2").replace(",","."))
                .emission_NOx(String.valueOf(emission_NOx))
                .emission_SO2(values.get("Vibros_SO2").replace(",","."))
                .emission_CH4("0".replace(",","."))
                .emission_dust("0".replace(",","."))
                .PDK_CO(pdz.getPDK_CO())
                .PDK_CO2(pdz.getPDK_CO2())
                .PDK_NO(pdz.getPDK_NO())
                .PDK_NO2(pdz.getPDK_NO2())
                .PDK_NOx(pdz.getPDK_NOx())
                .PDK_SO2(pdz.getPDK_SO2())
                .PDK_CH4(pdz.getPDK_CH4())
                .PDK_dust(pdz.getPDK_dust())
                .PDV_CO(pdz.getPDV_CO())
                .PDV_CO2(pdz.getPDV_CO2())
                .PDV_NO(pdz.getPDV_NO())
                .PDV_NO2(pdz.getPDV_NO2())
                .PDV_NOx(pdz.getPDV_NOx())
                .PDV_SO2(pdz.getPDV_SO2())
                .PDV_CH4(pdz.getPDV_CH4())
                .PDV_dust(pdz.getPDV_dust())
                .flueGasTemperature(values.get("Analog_T_d").replace(",","."))
                .flueGasPressure(values.get("Analog_P_d").replace(",","."))
                .flueGasSpeed(values.get("Analog_V_Flow_d").replace(",","."))
                .flueGasConsumptionNU(String.valueOf(flueGasConsNU))
                .flueGasConsumptionRU(String.valueOf(flueGasConsRU))
                .fuelConsumption("0".replace(",","."))
                .build();
        currentRepository.save(currentValue);
        return currentValue;

    }

    public PeriodRecord parsePeriod() throws SQLException {
        var currentMap = msExtractor.extractFromCurrent();
        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        Date date = new Date();
        var pdz = pdkPdvRepository.findById(1L).get();
        PeriodRecord pr = PeriodRecord.builder()
                .timestamp(df.format(date))
                .concentration_CO(currentMap.get("concentration_CO"))
                .concentration_CO2(currentMap.get("concentration_CO2"))
                .concentration_NO(currentMap.get("concentration_NO"))
                .concentration_NO2(currentMap.get("concentration_NO2"))
                .concentration_NOx(currentMap.get("concentration_NOx"))
                .concentration_SO2(currentMap.get("concentration_SO2"))
                .concentration_CH4(currentMap.get("concentration_CH4"))
                .concentration_O2(currentMap.get("concentration_O2"))
                .wasteGasTemperature(currentMap.get("waste_Gas_Temperature"))
                .concentration_dust(currentMap.get("concentration_dust"))
                .emission_CO(currentMap.get("emission_CO"))
                .emission_CO2(currentMap.get("emission_CO2"))
                .emission_NO(currentMap.get("emission_NO"))
                .emission_NO2(currentMap.get("emission_NO2"))
                .emission_NOx(currentMap.get("emission_NOx"))
                .emission_SO2(currentMap.get("emission_SO2"))
                .emission_CH4(currentMap.get("emission_CH4"))
                .emission_dust(currentMap.get("emission_dust"))
                .PDK_CO(pdz.getPDK_CO())
                .PDK_CO2(pdz.getPDK_CO2())
                .PDK_NO(pdz.getPDK_NO())
                .PDK_NO2(pdz.getPDK_NO2())
                .PDK_NOx(pdz.getPDK_NOx())
                .PDK_SO2(pdz.getPDK_SO2())
                .PDK_CH4(pdz.getPDK_CH4())
                .PDK_dust(pdz.getPDK_dust())
                .PDV_CO(pdz.getPDV_CO())
                .PDV_CO2(pdz.getPDV_CO2())
                .PDV_NO(pdz.getPDV_NO())
                .PDV_NO2(pdz.getPDV_NO2())
                .PDV_NOx(pdz.getPDV_NOx())
                .PDV_SO2(pdz.getPDV_SO2())
                .PDV_CH4(pdz.getPDV_CH4())
                .PDV_dust(pdz.getPDV_dust())
                .flueGasTemperature(currentMap.get("flue_Gas_Temperature"))
                .flueGasPressure(currentMap.get("flue_Gas_Pressure"))
                .flueGasSpeed(currentMap.get("flue_Gas_Speed"))
                .flueGasConsumptionNU(currentMap.get("flue_Gas_consumptionNU"))
                .flueGasConsumptionRU(currentMap.get("flue_Gas_consumptionRU"))
                .fuelConsumption(currentMap.get("fuel_consumption"))
                .build();
        periodRecordRepository.save(pr);
        return pr;
    }

    public MonitoringRecord parseDay() throws SQLException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        Date date = new Date();
        Map<String, String> prs = msExtractor.extractFromPeriod();
        MonitoringRecord mr = MonitoringRecord.builder()
                .timestamp(df.format(date))
                .concentration_CO(prs.get("concentration_CO"))
                .concentration_CO2(prs.get("concentration_CO2"))
                .concentration_NO(prs.get("concentration_NO"))
                .concentration_NO2(prs.get("concentration_NO2"))
                .concentration_NOx(prs.get("concentration_NOx"))
                .concentration_SO2(prs.get("concentration_SO2"))
                .concentration_CH4(prs.get("concentration_CH4"))
                .concentration_O2(prs.get("concentration_O2"))
                .wasteGasTemperature(prs.get("waste_Gas_Temperature"))
                .concentration_dust("0")
                .wasteGasFlow(prs.get("flue_Gas_consumptionRU"))
                .systemId("0052")
                .build();
        monitoringRecordRepository.save(mr);
        return mr;

    }

    public void flushCurrent() {
        currentRepository.deleteAll();
    }
    public void clearOldPeriod() throws SQLException {
        msExtractor.clearOldPeriod();
    }

    public MonitoringRecord getLast() {
        return monitoringRecordRepository.getLast();
    }

}
