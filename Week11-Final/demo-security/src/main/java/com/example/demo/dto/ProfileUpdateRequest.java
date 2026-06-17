package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfileUpdateRequest {

    @NotBlank
    private String displayName;

    private String department;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
