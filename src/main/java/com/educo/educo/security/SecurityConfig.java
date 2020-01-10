package com.educo.educo.security;

import com.educo.educo.exceptions.CustomAccessDeniedHandler;
import com.educo.educo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.educo.educo.constants.RouteConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationEntryPoint entryPoint;
    private CustomUserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtAuthenticationFilter authenticationFilter;
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(
            JwtAuthenticationEntryPoint entryPoint, CustomUserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder, JwtAuthenticationFilter authenticationFilter,
            CustomAccessDeniedHandler accessDeniedHandler) {
        this.entryPoint = entryPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFilter = authenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(
                "/**/*.jpg",
                QUESTION_ROOT.concat(QUESTION_GET_QUESTION),
                QUESTION_ROOT.concat(QUESTION_GET_BY_CATEGORY),
                COMMENT_ROOT.concat(COMMENT_GET_BY_QUESTION),
                CATEGORY_ROOT.concat(CATEGORY_GET),
                QUESTION_ROOT.concat(QUESTION_GET),
                QUESTION_ROOT.concat(QUESTION_GET_IMAGE),
                AUTH_ALL
        ).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
