package org.sid.springmvc.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.bcrypt.*;

@Controller
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private javax.sql.DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//MD5 est ancien, il est depasse, il existe des algo rapides qui detecte les mdp
		PasswordEncoder passwordEncoder=passwordEncoder();
		System.out.println("**************************");
		System.out.println(passwordEncoder.encode("123"));
		System.out.println("**************************");
		System.out.println(passwordEncoder.encode("1234"));
		System.out.println("**************************");
		
		  auth.jdbcAuthentication() .dataSource(dataSource)
		  .usersByUsernameQuery("SELECT username as principal, password as credentials, active FROM user WHERE username=?"
		  )
		  .authoritiesByUsernameQuery("SELECT username as principal, role as role from user_roles WHERE username=?"
		  ) .passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
		 
	
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//http.formLogin();
		//http.formLogin().failureUrl("/login?error=true");
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/resources/static/*").permitAll();
		http.formLogin().loginPage("/login");
		http.httpBasic();
	//	http.authorizeRequests().antMatchers("/listeCinema**/**","/listeVille**/**","/listFilms**/**","/listProjections**/**","/listCategory**/**","/listUsers**/**","/admin/**","/edit**/**","/save**/**","/delete**/**","/form**/**").hasRole("MANAGER");
	    
		http.authorizeRequests().antMatchers("/listeCinema**/**,/listFilms**/**,/listProjections**/**,/listeVille**/**,/listCategory**/**").hasRole("CONSULTED");
	//	http.authorizeRequests().antMatchers("/listeCinema**/**","/listUsers**/**","/listeVille**/**","/listFilms**/**","/listProjections**/**","/listCategory**/**","/formUser","/editUser**/**","/deleteUser","/saveUser**/**").hasRole("ADMIN");

		http.authorizeRequests().antMatchers("/user/**","/login","/webjars/**").permitAll();
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
		//http.authorizeRequests().anyRequest().authenticated();
	}
	
	@Bean
	public  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
