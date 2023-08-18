package com.prosigmaka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthDto {

    private String username;
    private String password;
    private String role;

}