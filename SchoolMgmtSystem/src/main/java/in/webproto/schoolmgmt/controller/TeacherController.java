package in.webproto.schoolmgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.webproto.schoolmgmt.service.TeacherService;
import in.webproto.schoolmgmt.util.ActionStatus;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.URLConstants;
import in.webproto.schoolmgmt.vo.TeacherDetailVO;

@Controller
@RequestMapping(value = URLConstants.TEACHER)
public class TeacherController
{
	private static final Logger	OUT	= LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private TeacherService		service;

	@PostMapping(value = URLConstants.SUMMARY)
	public String getAll(HttpServletRequest request, Model model)
	{
		try
		{
			List<TeacherDetailVO> teacherList = service.getAll();

			request.setAttribute(ActionStatus.STATUS.getDisplayLabel(), request.getAttribute(ActionStatus.STATUS.getDisplayLabel()));
			model.addAttribute("TeacherFormBean", new TeacherDetailVO());
			request.setAttribute("teacherList", teacherList);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "TeacherScreen";
	}

	@PostMapping(value = URLConstants.SAVE)
	public String addNewTeacher(@ModelAttribute("TeacherFormBean") TeacherDetailVO formBean, HttpServletRequest request)
	{
		try
		{
			Integer teacherId = service.addNewTeacher(formBean);
			request.setAttribute("teacherId", teacherId);
			if (teacherId > 0)
			{
				request.setAttribute(ActionStatus.STATUS.getDisplayLabel(), ActionStatus.SUCCESSFUL);
			}
			else
			{
				request.setAttribute(ActionStatus.STATUS.getDisplayLabel(), ActionStatus.FAILURE);
			}
		}
		catch (Exception e)
		{
			request.setAttribute(ActionStatus.STATUS.getDisplayLabel(), ActionStatus.EXCEPTION);
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "forward:" + URLConstants.TEACHER + URLConstants.SUMMARY;
	}
}
