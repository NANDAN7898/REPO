package in.webproto.schoolmgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import in.webproto.schoolmgmt.service.UserPrincipalDetailService;
import in.webproto.schoolmgmt.util.UserRoleTypeEnum;

@EnableWebSecurity
public class WebApplicationAdapter extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserPrincipalDetailService userPrincService;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(authenticatioProvider());
	}

	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticatioProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(getPasswordEncoder());
		provider.setUserDetailsService(userPrincService);
		
		return provider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/admin").hasRole(String.valueOf(UserRoleTypeEnum.SUPERADMIN.getRoleTypeId()))
		.and()
		.formLogin().loginPage("/login")
		.and()
		.logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout")
        .permitAll()
        .and().httpBasic();
	}

}
