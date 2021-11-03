package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEntrepreneurDto {
    private RequestDto request;
    private EntrepreneurRequestDto entrepreneurRequest;


}
