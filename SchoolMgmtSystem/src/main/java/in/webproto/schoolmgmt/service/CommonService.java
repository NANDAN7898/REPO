package in.webproto.schoolmgmt.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.UserRoleTypeEnum;
import in.webproto.schoolmgmt.vo.CommonVO;

@Service
public class CommonService
{
	private static final Logger	OUT	= LoggerFactory.getLogger(CommonService.class);

	@Autowired
	private Environment			environment;

	/**
	 * @return
	 */
	public Map<String, String> getGenderMap()
	{
		Map<String, String> genderMap = new HashMap<>();
		genderMap.put("M", environment.getProperty("webproto.schoolmgmt.label.male"));
		genderMap.put("F", environment.getProperty("webproto.schoolmgmt.label.female"));
		return genderMap;
	}

	/**
	 * @return
	 */
	public List<String> getSessionList()
	{
		List<String> sessionList = new ArrayList<>();

		Calendar cal = Calendar.getInstance();
		Integer currentYear = cal.get(Calendar.YEAR);

		for (int i = 1; i < 2; i++)
		{
			sessionList.add(String.valueOf(currentYear + i) + " - " + String.valueOf((currentYear + 1) + 1));
		}

		sessionList.add(String.valueOf(currentYear) + " - " + String.valueOf(currentYear + 1));

		for (int i = 1; i < 3; i++)
		{
			sessionList.add(String.valueOf(currentYear - i) + " - " + String.valueOf((currentYear - i) + 1));
		}

		return sessionList;
	}

	/**
	 * @return
	 */
	public List<CommonVO> getBranchDetails()
	{
		List<CommonVO> branchList = null;
		try
		{
			branchList = DBManager.getInstance().getResultList("StudentDetailMaster.getBranchDetails", null);
		}
		catch (Exception e)
		{
			OUT.debug(ApplicationConstants.EXCEPTION, e);
		}
		return branchList;
	}
	
	/**
	 * @return
	 */
	public List<String> getRolePermissionList()
	{
		List<String> rolePermissioList = new ArrayList<>();
		rolePermissioList.add("ROLE_" + UserRoleTypeEnum.SUPERADMIN.getRoleTypeId());
		//rolePermissioList.add(UserRoleTypeEnum.ADMIN.toString());
		//rolePermissioList.add(UserRoleTypeEnum.TEACHER.toString());
		//rolePermissioList.add(UserRoleTypeEnum.ACCOUNTANT.toString());
		//rolePermissioList.add(UserRoleTypeEnum.STUDENT.toString());
		
		return rolePermissioList;
	}
}
