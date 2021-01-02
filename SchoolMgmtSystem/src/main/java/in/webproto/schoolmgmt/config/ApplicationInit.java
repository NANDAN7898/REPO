package in.webproto.schoolmgmt.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.context.ContextLoaderListener;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.to.UserDetailMasterTO;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.UserRoleTypeEnum;

public class ApplicationInit extends ContextLoaderListener implements ServletContextInitializer
{
	private static final Logger OUT = LoggerFactory.getLogger(ApplicationInit.class);

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		OUT.info("**************************************************************************************************");
		OUT.info("										Starting Application										");
		OUT.info("**************************************************************************************************");

		UserDetailMasterTO userTO = new UserDetailMasterTO();
		userTO.setUsername(UserRoleTypeEnum.SUPERADMIN.name().toLowerCase());
		try
		{
			userTO = (UserDetailMasterTO) DBManager.getInstance().getResultAsObject("", userTO);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		OUT.info("**************************************************************************************************");
		OUT.info("										Started Application											");
		OUT.info("**************************************************************************************************");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		OUT.info("**************************************************************************************************");
		OUT.info("										Stopped Application											");
		OUT.info("**************************************************************************************************");
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		OUT.info("**************************************************************************************************");
		OUT.info("										Starting Application			onStartup							");
		OUT.info("**************************************************************************************************");
		
	}

}
