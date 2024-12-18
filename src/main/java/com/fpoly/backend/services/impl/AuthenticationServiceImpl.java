package com.fpoly.backend.services.impl;

import com.fpoly.backend.dto.*;
import com.fpoly.backend.entities.Admin;
import com.fpoly.backend.entities.Instructor;
import com.fpoly.backend.entities.InvalidateToken;
import com.fpoly.backend.entities.Student;
import com.fpoly.backend.repository.AdminRepository;
import com.fpoly.backend.repository.InstructorRepository;
import com.fpoly.backend.repository.InvalidateTokenRepository;
import com.fpoly.backend.repository.StudentRepository;
import com.fpoly.backend.services.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    StudentRepository studentRepository;
    AdminRepository adminRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    InstructorRepository instructorRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    public String generateToken(String email, String role) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("truongnx")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(10, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",role)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            throw new RuntimeException("Lỗi khi lấy token: ", e);
        }
    }


    public String generateRoleStudent(Student student) {
        return generateToken(student.getEmail(),"ROLE_STUDENT");
    }


    public String generateRoleInstructor(Instructor instructor) {
        return generateToken(instructor.getSchoolEmail(),"ROLE_INSTRUCTOR");
    }


    public String generateRoleAdmin(Admin admin) {

        System.out.println("Token created for admin: " + admin.getEmail());
        return generateToken(admin.getEmail(),"ROLE_ADMIN");
    }


    public AuthenticationResponse authenticationAndGenerateToken(AuthenticationRequest request) {
        String email = request.getEmail();

        if(studentRepository.existsByEmail(email)){
            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("Student not found"));
            return createAuthResponse(generateRoleStudent(student));

        } else if(instructorRepository.existsBySchoolEmail(email)){
            Instructor instructor = instructorRepository.findBySchoolEmail(email)
                    .orElseThrow(()->new RuntimeException("Instructor not found"));
            return createAuthResponse(generateRoleInstructor(instructor));

        } else if(adminRepository.existsByEmail(email)){
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("Admin not found"));
            return createAuthResponse(generateRoleAdmin(admin));
        }else {
            throw new RuntimeException("Không tìm thấy email này trong dữ liệu!");
        }
    }


    public AuthenticationResponse createAuthResponse(String token) {
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT =SignedJWT.parse(token);

        Date expiryTime=signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified=signedJWT.verify(verifier);

        if(!expiryTime.after(new Date())&&verified){
            throw new RuntimeException("Người dùng chưa được xác thực!");
        }

        if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException("Quá trình xác thực thất bại. Do token đã bị vô hiệu hóa! ");
        }
        return signedJWT;

    }

    public void logout(Logout request) throws ParseException, JOSEException {
        var signToken=verifyToken(request.getToken());
        String jit=signToken.getJWTClaimsSet().getJWTID();

        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .token(jit)
                .expiryTime(expiryTime)
                .build();
        invalidateTokenRepository.save(invalidateToken);
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token =request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token);
        }
        catch (Exception e){
            isValid=false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse refreshToken(Refresh request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken());

        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .token(jit)
                .expiryTime(expiryTime)
                .build();
        invalidateTokenRepository.save(invalidateToken);

        var email = signJWT.getJWTClaimsSet().getSubject();
        var scope = (String) signJWT.getJWTClaimsSet().getClaim("scope");

        String newToken;

        if ("ROLE_ADMIN".equals(scope)) {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("ErrorCode.UNAUTHENTICATED"));
            newToken = generateToken(admin.getEmail(), "ROLE_ADMIN");

        } else if ("ROLE_STUDENT".equals(scope)) {
            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("ErrorCode.UNAUTHENTICATED"));
            newToken = generateToken(student.getEmail(), "ROLE_STUDENT");

        } else if ("ROLE_INSTRUCTOR".equals(scope)) {
            Instructor instructor = instructorRepository.findBySchoolEmail(email)
                    .orElseThrow(() -> new RuntimeException("ErrorCode.UNAUTHENTICATED"));
            newToken = generateToken(instructor.getSchoolEmail(), "ROLE_INSTRUCTOR");

        } else {
            throw new RuntimeException("ErrorCode.UNAUTHENTICATED");
        }

        return AuthenticationResponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();
    }
}
