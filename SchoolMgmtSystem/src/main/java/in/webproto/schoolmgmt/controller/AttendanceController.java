package in.webproto.schoolmgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendanceController 
{
	@PostMapping(value = "/viewAttendance")
	public String viewAttendance()
	{
		return "ViewAttendance";
	}
	
	@PostMapping(value = "/markAttendance")
	public String markAttendance()
	{
		return "MarkAttendance";
	}
}
