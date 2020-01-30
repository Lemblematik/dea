package com.cmr.dea.adminapi.demo.model;

import javax.validation.constraints.NotNull;

public class AdminRequestModel {
    @NotNull(message = "should not be null")
    private String name;
    @NotNull(message = "password should not be null")
    private String passwort;

}
