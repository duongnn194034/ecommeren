package com.example.ecommerce.Dto.User;

public class SignInResponseDto {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SignInResponseDto(String status) {
        this.status = status;
    }
}
