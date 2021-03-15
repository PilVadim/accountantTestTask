package com.accountManagement.testTask.configuration;

import com.accountManagement.testTask.configuration.handler.CustomAuthenticationEntryPoint;
import com.accountManagement.testTask.configuration.handler.CustomAuthenticationFailureHandler;
import com.accountManagement.testTask.configuration.handler.CustomLogoutSuccessHandler;
import com.accountManagement.testTask.configuration.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import com.accountManagement.testTask.service.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    public final static String ADMIN_ROLE = "ADMIN";
    public final static String USER_ROLE  = "USER";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin().failureHandler(authenticationFailureHandler());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.formLogin().successHandler(simpleUrlAuthenticationSuccessHandler());
        http.logout().logoutSuccessHandler(logoutSuccessHandler());

        http
                .requestCache()
                .requestCache( new NullRequestCache())
                .and()

                .authorizeRequests()
                .antMatchers("/h2-console/**","/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/v2/api-docs",
                                        "/webjars/**"
                ).permitAll()

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .antMatchers("/api/v1/**").hasAnyAuthority(USER_ROLE)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginProcessingUrl("/auth/login")
                .and()
                .logout().logoutUrl("/auth/logout")
        .and().headers().frameOptions().disable();
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetail();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
