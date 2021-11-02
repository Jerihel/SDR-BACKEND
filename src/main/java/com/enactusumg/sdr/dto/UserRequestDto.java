package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode()
public class UserRequestDto {

    private Integer campus;
    private Integer facultad;
    private Integer career;
    private Integer enacterDegree;
    private Integer interest;
    private Integer lookUp;
    private String financialSupport;
    private Integer amount;
    private String supportDetail;
    private  String adviserDegree;
    private String currenctActivity;
    private String hasExperience;
    private  String experienceDetail;
    private Integer adviseOn;
    private Integer adviseWay;
    private String supportLookUp;


}
