package com.enactusumg.sdr.dto;

import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.models.UserRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEnacterQuery {
 private   Request request;
 private UserRequest userRequest;
}
