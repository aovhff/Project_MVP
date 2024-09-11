package com.boot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .antMatchers("/login", "/signup", "/signup/**", "/css/**", "/js/**", "/images/**","/main","/movie/**", 
	                    "/checkUserId", "/verify-code", 
	                    "/findIdPage", "/userid", // 여기서 /userid 추가
	                    "/findPwPage", "/findPassword", "/resetPwPage", "/resetPassword", "/email/**")
	            .permitAll()  // 인증 없이 접근 가능하게 설정
	        .anyRequest().authenticated()
	        .and()
	        .csrf()
	            .ignoringAntMatchers("/email/**") // 필요에 따라 CSRF 무시할 경로 설정  // 이메일인증은 로그인이 되어있지않은 상태이기때문에 예외처리함
	        .and()
	        .formLogin()
	            .loginPage("/login")
	            .loginProcessingUrl("/auth")
	            .usernameParameter("userid")
	            .passwordParameter("ppass")
	            .defaultSuccessUrl("/main")
	        .and()
	        .logout()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/main")
	        .and()
	        .sessionManagement()
	            .sessionFixation().none();

	    return http.build();
	}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 빈 등록
    }
}
