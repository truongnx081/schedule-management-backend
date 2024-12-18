package com.fpoly.backend.controller;

import com.fpoly.backend.dto.*;
import com.fpoly.backend.exception.AppUnCheckedException;
import com.fpoly.backend.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    //API login, lấy token
    @PostMapping("/token")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            var result = authenticationService.authenticationAndGenerateToken(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), result, "Token generated successfully", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    //API Xác minh token
    @PostMapping("/introspect")
    public ResponseEntity<Response> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        try {
            var result = authenticationService.introspect(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), result, "Token introspection successful", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    //API logout, xóa token
    @PostMapping("/logout")
    public ResponseEntity<Response> logout(@RequestBody Logout request) throws ParseException, JOSEException {
        try {
            authenticationService.logout(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), null, "Logged out successfully", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }

    //API refresh token
    @PostMapping("/refresh")
    public ResponseEntity<Response> refreshToken(@RequestBody Refresh request) throws ParseException, JOSEException {
        try {
            var result = authenticationService.refreshToken(request);
            return ResponseEntity.ok(new Response(LocalDateTime.now(), result, "Token refreshed successfully", HttpStatus.OK.value()));
        } catch (AppUnCheckedException e){
            return ResponseEntity.status(e.getStatus()).body(new Response(LocalDateTime.now(),null,e.getMessage(),e.getStatus().value()));
        }
    }
}
