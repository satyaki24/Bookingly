package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Handler;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.enums.Role;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Security.JWTService;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JWTService jwtService;

    @Value("${deploy.env}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException, java.io.IOException {

        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User= (DefaultOAuth2User) token.getPrincipal();

        String email=oAuth2User.getAttribute("email");

        User user=userService.getUserByEmail(email);
        if(user==null){
            User newUser=User.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(email)
                    .password("oauth2_dummy_password")
                    .roles(Set.of(Role.GUEST))
                    .build();
            user=userService.save(newUser);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        String frontEndUrl="http://localhost:8080/api/v1/home.html?token=" + accessToken;

//        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

        response.sendRedirect(frontEndUrl);
    }
}
