package com.enactusumg.sdr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedDto {
    private String username;
    private String email;
    private String password;
}
