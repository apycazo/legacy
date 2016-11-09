package com.github.apycazo.pylon.core.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSession
{
    private String username;
    private List<String> roles = new LinkedList<>();
    private long timestamp;
    private String token;
}