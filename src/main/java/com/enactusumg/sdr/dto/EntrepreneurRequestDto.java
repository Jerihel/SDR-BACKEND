package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@EqualsAndHashCode()
public class EntrepreneurRequestDto  {
    private String aboutUs;
    private String requirements;
    private String contextLocation;
    private String details;

}