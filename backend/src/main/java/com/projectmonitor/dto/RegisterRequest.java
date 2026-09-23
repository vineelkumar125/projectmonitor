package com.projectmonitor.dto; import jakarta.validation.constraints.*; public record RegisterRequest(@NotBlank String name,@NotBlank @Email String email,@NotBlank @Size(min=6) String password,String role){}

//package com.projectmonitor.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//
//public record LoginRequest(
//        @NotBlank
//        @Email
//        String email,
//
//        @NotBlank
//        String password
//) {
//}