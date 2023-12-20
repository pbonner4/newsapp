package newsApp.controller;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import javax.validation.Valid;

import newsApp.model.LoginDto;
import newsApp.model.LoginResponseDto;
import newsApp.model.RegisterUserDto;
import newsApp.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;




import newsApp.dao.UserDao;
import newsApp.security.jwt.JWTFilter;
import newsApp.security.jwt.TokenProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames.CLIENT_ID;

@RestController
@CrossOrigin
public class ThirdPartyAuthenticationController {


    private UserDao userDao;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    public ThirdPartyAuthenticationController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/validateGoogleTokenSignUp", method = RequestMethod.POST)
    public ResponseEntity<String> validateGoogleSignUp(
            @RequestHeader("Authorization") String Bearer,
            @RequestHeader("idToken") String idToken,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) throws GeneralSecurityException, IOException {

        System.out.println(Bearer);
        System.out.println(idToken);
        System.out.println(oauth2User);

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        System.out.println(googleIdToken);
        if (googleIdToken != null) {
            Payload payload = googleIdToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println(payload);
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            System.out.println("Last Name: " + familyName);

            // Use or store profile information
            // ...
            return ResponseEntity.ok(familyName);

        } else {
            System.out.println("Invalid ID token.");
        }
        return ResponseEntity.ok("validated");

    }
}

