package in.webproto.schoolmgmt.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.webproto.schoolmgmt.service.ClassService;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.URLConstants;
import in.webproto.schoolmgmt.vo.ClassDetailVO;

@Controller
@RequestMapping(value = URLConstants.CLASS_CONTROLLER)
public class ClassController
{
	private static final Logger	OUT	= LoggerFactory.getLogger(ClassController.class);

	@Autowired
	private ClassService		service;

	@PostMapping(value = URLConstants.SAVE)
	public String addNewClass(@ModelAttribute("ClassFormBean") ClassDetailVO ClassFormBean, HttpServletRequest request)
	{
		Boolean isAdded = service.addNewClass(ClassFormBean);

		if (isAdded)
		{
			request.setAttribute("className", ClassFormBean.getClassName());
			request.setAttribute("isAdded", isAdded);
		}
		return "redirect:" + URLConstants.CLASS_CONTROLLER + URLConstants.SUMMARY + "?isAdded=" + isAdded + "&className=" + ClassFormBean.getClassName();
	}

	@PostMapping(value = URLConstants.SUMMARY)
	public String summary(HttpServletRequest request, Model model)
	{
		Boolean isAdded = request.getParameter("isAdded") != null ? Boolean.parseBoolean(request.getParameter("isAdded")) : false;
		Boolean isDelete = request.getParameter("isDelete") != null ? Boolean.parseBoolean(request.getParameter("isDelete")) : false;
		Boolean isUpdated = request.getParameter("isUpdated") != null ? Boolean.parseBoolean(request.getParameter("isUpdated")) : false;

		request.setAttribute("className", request.getParameter("className"));
		request.setAttribute("isAdded", isAdded);
		request.setAttribute("isDelete", isDelete);
		request.setAttribute("isUpdated", isUpdated);
		List<ClassDetailVO> classList = service.getSummary();

		model.addAttribute("ClassFormBean", new ClassDetailVO());
		request.setAttribute("classList", classList);
		return "ClassScreen";
	}

	@PostMapping(value = URLConstants.DELETE_BY_ID)
	public String deleteClass(@RequestParam("i") String id)
	{
		Boolean isDelete = false;
		try
		{
			isDelete = service.deleteClass(id != null ? Integer.parseInt(id) : 0);
		}
		catch (Exception e)
		{
			isDelete = false;
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "redirect:" + URLConstants.CLASS_CONTROLLER + URLConstants.SUMMARY + "?isDelete=" + isDelete;
	}

	@PostMapping(value = URLConstants.UPDATE)
	public String editClassDetails(@ModelAttribute("ClassFormBean") ClassDetailVO ClassFormBean, HttpServletRequest request)
	{
		Boolean isUpdated = false;
		try
		{
			isUpdated = service.updateClass(ClassFormBean);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "redirect:" + URLConstants.CLASS_CONTROLLER + URLConstants.SUMMARY + "?isUpdated=" + isUpdated + "&className=" + ClassFormBean.getClassName();
	}

	@PostMapping(value = URLConstants.GET_BY_ID)
	public String getById(@RequestParam("id") String id, HttpServletResponse response)
	{
		PrintWriter writer = null;
		try
		{
			writer = response.getWriter();
			writer.write(service.getById(Integer.parseInt(id)));
		}
		catch (NumberFormatException e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		finally
		{
			if (writer != null)
			{
				writer.flush();
				writer.close();
			}
		}
		return URLConstants.EMPTY_SCREEN;
	}

	@PostMapping(value = "/getSection")
	public String getSection(@RequestParam("clazz") String clazz, HttpServletResponse response)
	{
		PrintWriter writer = null;
		try
		{
			writer = response.getWriter();
			writer.write(service.getSectionByClass(Integer.parseInt(clazz)));
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		finally
		{
			if (writer != null)
			{
				writer.flush();
				writer.close();
			}
		}
		return URLConstants.EMPTY_SCREEN;
	}

	@GetMapping(value = "/subject")
	public String schedule()
	{
		return "SubjectScreen";
	}
}
