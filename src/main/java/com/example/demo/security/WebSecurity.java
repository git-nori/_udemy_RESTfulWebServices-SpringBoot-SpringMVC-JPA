package com.example.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService; // 独自インターフェースを使用するため、UserDetailsServiceを拡張したインターフェースを指定
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // constructor
    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認証、認可の設定
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter()) // 認証にはカスタムしたAuthenticationFilterクラスを使用するよう設定
                .addFilter(new AuthorizationFilter(authenticationManager())) // トークンの検証にAuthorizationFilterクラスを使用するよう設定
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // HttpSessionを用いないよう設定(jwtではセッションで管理することはしないため)
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用するuserDetailsServiceとpasswordEncoderを設定
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // 認証に使用するフィルターの生成と、認証に使用するURLの設定
    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login"); // 認証に使用するURLを設定
        return filter;
    }
}
