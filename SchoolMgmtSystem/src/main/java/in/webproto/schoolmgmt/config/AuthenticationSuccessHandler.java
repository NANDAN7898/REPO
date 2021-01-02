package in.webproto.schoolmgmt.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{
	private static final Logger OUT = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		response.setStatus(HttpServletResponse.SC_OK);
		boolean admin = false;
		OUT.info("Authentication Successful!!");

		for (GrantedAuthority auth : authentication.getAuthorities())
		{
			OUT.info("Authority: {}", auth.getAuthority());
			if ("ROLE_ADMIN".equals(auth.getAuthority()) || "ADMIN".equals(auth.getAuthority()))
			{
				admin = true;
			}
		}

		if (admin)
		{
			response.sendRedirect("/admin");
		}
		else
		{
			response.sendRedirect("/user");
		}
	}
}
