package com.enactusumg.sdr.dto;

import lombok.Data;

import java.util.List;

@Data
public class EditUserDto {
    private Integer state;
    private List<Integer> roles;
}
