package com.cl932.rsmw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonitoringRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String systemId;
    String timestamp;
    String concentration_CO;
    String concentration_CO2;
    String concentration_NO;
    String concentration_NO2;
    String concentration_NOx;
    String concentration_SO2;
    String concentration_CH4;
    String concentration_O2;
    String concentration_dust;
    String wasteGasTemperature;
    String wasteGasFlow;
}
