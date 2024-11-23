package com.cl932.rsmw.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pdk_pdv")
public class PdkPdv {
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
