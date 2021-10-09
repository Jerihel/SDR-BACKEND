package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@NoArgsConstructor
@Data
public class RequestDto  {
    private String name;
    private String lastName;
    private String telephone;
    private String email;
    private Integer requestType;
    private Integer state;
    private String idReviwer;
    private String appointmentLocation;
    private Date appointment;
    private String requestComment;
}
