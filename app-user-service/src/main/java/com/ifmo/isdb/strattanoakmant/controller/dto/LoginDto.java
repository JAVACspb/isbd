package com.ifmo.isdb.strattanoakmant.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {

    @ApiModelProperty(value = "login", example = "")
    @NotBlank(message = "Login is mandatory.")
    @Size(min = 1, max = 255, message = "Login must be from 1 to 255 characters")
    private String login;

    @ApiModelProperty(value = "password", example = "")
    @NotBlank(message = "Password is mandatory.")
    @Size(min = 1, max = 255, message = "Password must be from 1 to 255 characters")
    private String password;
}
