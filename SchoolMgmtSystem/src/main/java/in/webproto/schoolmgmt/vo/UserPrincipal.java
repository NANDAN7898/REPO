package in.webproto.schoolmgmt.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5513524013943960138L;

	private AuthenticationVO	authenticationVO;

	public UserPrincipal(AuthenticationVO authenticationVO)
	{
		this.authenticationVO = authenticationVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> autohrityList = new ArrayList<>();
		
		this.authenticationVO.getRolePermission().forEach(role -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(role);
			autohrityList.add(authority);
		});
		
		return autohrityList;
	}

	@Override
	public String getPassword()
	{
		return this.authenticationVO.getPassword();
	}

	@Override
	public String getUsername()
	{
		return this.authenticationVO.getUsername();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return this.authenticationVO.getEnabled();
	}

}
