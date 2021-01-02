package in.webproto.schoolmgmt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.vo.AuthenticationVO;
import in.webproto.schoolmgmt.vo.UserPrincipal;

@Service
public class UserPrincipalDetailService implements UserDetailsService
{
	private static final Logger OUT = LoggerFactory.getLogger(UserPrincipalDetailService.class);
	
	@Autowired
	private CommonService commonService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		AuthenticationVO authenticationVO = getUserByUserName(username);
		authenticationVO.setRolePermission(commonService.getRolePermissionList());
		UserPrincipal principal = new UserPrincipal(authenticationVO);
		return principal;
	}

	/**
	 * @param username
	 * @return
	 */
	private AuthenticationVO getUserByUserName(String username)
	{
		AuthenticationVO authVO = null;
		try
		{
			authVO = (AuthenticationVO) DBManager.getInstance().getResultAsObject("UserDetailMaster.getAuthenticationDetails", username);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return authVO;
	}

}
