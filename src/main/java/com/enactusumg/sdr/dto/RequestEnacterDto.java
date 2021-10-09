package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEnacterDto {
    private RequestDto request;
    private UserRequestDto userRequest;


}
