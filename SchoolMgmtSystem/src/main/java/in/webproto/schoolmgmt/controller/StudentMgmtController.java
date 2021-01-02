package in.webproto.schoolmgmt.controller;

import java.io.PrintWriter;
import java.util.Calendar;
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
import org.springframework.web.multipart.MultipartFile;

import in.webproto.schoolmgmt.service.ClassService;
import in.webproto.schoolmgmt.service.CommonService;
import in.webproto.schoolmgmt.service.StudentService;
import in.webproto.schoolmgmt.util.ActionStatus;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.URLConstants;
import in.webproto.schoolmgmt.vo.StudentDetailVO;

@Controller
@RequestMapping(value = URLConstants.STUDENT_CONTROLLER)
public class StudentMgmtController
{
	private static final Logger	OUT	= LoggerFactory.getLogger(StudentMgmtController.class);

	@Autowired
	private CommonService		commonService;

	@Autowired
	private StudentService		studentService;
	
	@Autowired
	private ClassService		classService;

	@PostMapping(value = URLConstants.SUMMARY)
	public String getSummary(HttpServletRequest request)
	{
		try
		{
			request.setAttribute("classList", classService.getSummary());
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "StudentHome";
	}
	
	@PostMapping(value = URLConstants.SEARCH)
	public String getFilter(@RequestParam("clazz") String clazz, @RequestParam("section") String section, HttpServletRequest request)
	{
		List<StudentDetailVO> detailVO = null;
		Integer clazzId = 0;

		try
		{
			clazzId = clazz != null && !clazz.isEmpty() ? Integer.parseInt(clazz) : null;
			detailVO = studentService.getCardsDetails(clazzId, section);
			request.setAttribute("cardList", detailVO);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "StudentSummaryScreen";
	}

	@PostMapping(value = "/admform")
	public String showForm(Model model, HttpServletRequest request)
	{
		StudentDetailVO studentVO = null;
		Calendar cal = Calendar.getInstance();
		try
		{
			Integer currentYear = cal.get(Calendar.YEAR);
			String currentSession = String.valueOf(currentYear + " - " + (currentYear + 1));
			Integer studentId = request.getParameter("studentId") != null ? Integer.parseInt(request.getParameter("studentId")) : null;

			if (studentId != null)
			{
				studentVO = studentService.getStudentObject(studentId);
				studentVO.setStudentId(studentId);
			}
			else
			{
				studentVO = new StudentDetailVO();
			}

			studentVO.setGenderMap(commonService.getGenderMap());
			studentVO.setSessionList(commonService.getSessionList());
			studentVO.setSession(currentSession);
			studentVO.setBranchList(commonService.getBranchDetails());

			model.addAttribute("StudentFormBean", studentVO);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "StudentFormScreen";
	}

	@PostMapping(value = URLConstants.SAVE)
	public String save(@ModelAttribute("StudentFormBean") StudentDetailVO formBean, @RequestParam("image") MultipartFile file)
	{
		Integer pkId = 0;
		try
		{
			pkId = studentService.admission(formBean, file);
			if (pkId != null && pkId > 0)
			{
				formBean.setStudentId(pkId);
				formBean.setGenderMap(commonService.getGenderMap());
				formBean.setSessionList(commonService.getSessionList());
				formBean.setBranchList(commonService.getBranchDetails());
			}
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "StudentFormScreen";
	}

	@PostMapping(value = "/regform")
	public String showQuickForm(Model model)
	{
		StudentDetailVO studentVO = new StudentDetailVO();
		Calendar cal = Calendar.getInstance();
		Integer currentYear = cal.get(Calendar.YEAR);
		String currentSession = String.valueOf(currentYear + " - " + (currentYear + 1));

		studentVO.setGenderMap(commonService.getGenderMap());
		studentVO.setSessionList(commonService.getSessionList());
		studentVO.setSession(currentSession);
		studentVO.setBranchList(commonService.getBranchDetails());

		model.addAttribute("classList", classService.getSummary());
		model.addAttribute("StudentFormBean", studentVO);
		return "QuickRegistration";
	}

	@PostMapping(value = URLConstants.REGISTER)
	public String register(@ModelAttribute("StudentFormBean") StudentDetailVO formBean, HttpServletRequest request)
	{
		Integer pkId = 0;
		try
		{
			Boolean goforAdmission = request.getParameter("goforAdmission") != null ? Boolean.parseBoolean(request.getParameter("goforAdmission")): Boolean.FALSE;
			pkId = studentService.register(formBean);

			if (pkId != null && pkId > 0)
			{
				formBean.setStudentId(pkId);
				formBean.setGenderMap(commonService.getGenderMap());
				formBean.setSessionList(commonService.getSessionList());
				formBean.setBranchList(commonService.getBranchDetails());

				if(goforAdmission)
				{
					request.setAttribute("StudentFormBean", formBean);
					return "StudentFormScreen";
				}
				else
				{
					request.setAttribute("status", ActionStatus.SUCCESSFUL);
				}
			}
			else
			{
				request.setAttribute("status", ActionStatus.FAILURE);
			}
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "QuickRegistration";
	}
	
	@PostMapping(value = URLConstants.GET_BY_ID)
	public String fetchByRegistrationNo(HttpServletResponse response, HttpServletRequest request)
	{
		String regNo = null;
		String studentId = null;
		try
		{
			regNo = request.getParameter("regNo");
			studentId = request.getParameter("i");
			if(regNo != null && !regNo.isEmpty())
			{
				String detailStr = studentService.getDetailsJSON(Integer.parseInt(regNo));
				
				PrintWriter writer = response.getWriter();
				writer.write(detailStr);
				writer.flush();
				writer.close();
			}
			else
			{
				return "redirect:/student/admform?studentId=" + studentId;
			}
			
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return URLConstants.EMPTY_SCREEN;
	}
	
	@PostMapping(value = URLConstants.DELETE_BY_ID)
	public String delete(@RequestParam("i") String studentId, HttpServletRequest request)
	{
		Boolean count = false;
		try
		{
			count = studentService.deleteById(Integer.parseInt(studentId));
			if (count)
			{
				request.setAttribute("status", ActionStatus.SUCCESSFUL);
			}
			else
			{
				request.setAttribute("status", ActionStatus.FAILURE);
			}
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return "redirect:" + URLConstants.STUDENT_CONTROLLER + URLConstants.SUMMARY;
	}
	
	@PostMapping(value = "/excleUploadForm")
	public String uploadExcle()
	{
		return "ExcleUpload";
	}

	@PostMapping(value = "/tcreport")
	public String showTCReport()
	{
		return "TCPrintReport";
	}	
}
