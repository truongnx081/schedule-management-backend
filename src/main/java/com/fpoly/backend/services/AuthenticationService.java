package com.fpoly.backend.services;

import com.fpoly.backend.dto.*;
import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.Student;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticationAndGenerateToken (AuthenticationRequest request);

    public void logout(Logout request) throws ParseException, JOSEException;

    public IntrospectResponse introspect(IntrospectRequest request);

    public AuthenticationResponse refreshToken(Refresh request) throws ParseException, JOSEException;
}
