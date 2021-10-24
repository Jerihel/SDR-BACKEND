package com.enactusumg.sdr.dto;

import com.enactusumg.sdr.models.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserLoggedDto {
    private String username;
    private List<UserRole> roles;
    private String token;
}
