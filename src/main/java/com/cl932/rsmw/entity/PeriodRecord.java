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
public class PeriodRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String timestamp;
    String concentration_CO;
    String concentration_CO2;
    String concentration_NO;
    String concentration_NO2;
    String concentration_NOx;
    String concentration_SO2;
    String concentration_CH4;
    String concentration_O2;
    String wasteGasTemperature;
    String concentration_dust;
    String emission_CO;
    String emission_CO2;
    String emission_NO;
    String emission_NO2;
    String emission_NOx;
    String emission_SO2;
    String emission_CH4;
    String emission_dust;
    String PDK_CO;
    String PDK_CO2;
    String PDK_NO;
    String PDK_NO2;
    String PDK_NOx;
    String PDK_SO2;
    String PDK_CH4;
    String PDK_dust;
    String PDV_CO;
    String PDV_CO2;
    String PDV_NO;
    String PDV_NO2;
    String PDV_NOx;
    String PDV_SO2;
    String PDV_CH4;
    String PDV_dust;
    String flueGasTemperature;
    String flueGasPressure;
    String flueGasSpeed;
    String flueGasConsumptionNU;
    String flueGasConsumptionRU;
    String fuelConsumption;
}
