package in.webproto.schoolmgmt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.to.StudentDetailMasterTO;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.CommonUtil;
import in.webproto.schoolmgmt.vo.StudentDetailVO;

@Service
public class StudentService
{
	private static final Logger	OUT						= LoggerFactory.getLogger(StudentService.class);

	private static final String	STUDENT_REGISTRATION	= "StudentDetailMaster.regStudent";

	private static final String	STUDENT_ADMISSION		= "StudentDetailMaster.admissionStudent";

	private static final String	STUDENT_CARD_DETAILS	= "StudentDetailMaster.getStudentsCardDetails";
	
	private static final String	GET_BY_ID				= "StudentDetailMaster.getById";
	
	private static final String	DEFAULT_UPLOAD_PATH		= "./src/main/webapp/photos/";

	/**
	 * @param studentVO
	 * @return
	 * @throws Exception
	 */
	public Integer register(StudentDetailVO studentVO) throws Exception
	{
		Integer pkId = 0;

		StudentDetailMasterTO studentTO = new StudentDetailMasterTO();
		BeanUtils.copyProperties(studentVO, studentTO);
		studentTO.setRegDate(CommonUtil.getCurrentSQLDate());
		studentTO.setDob(CommonUtil.convertIntoSQLDate(studentVO.getDob()));
		
		pkId = DBManager.getInstance().insert(STUDENT_REGISTRATION, studentTO);

		if (pkId > 0)
		{
			return studentTO.getId();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * @param studentVO
	 * @return
	 * @throws Exception
	 */
	public Integer admission(StudentDetailVO studentVO, MultipartFile multiPartFile) throws Exception
	{
		String fileName = studentVO.getFirstName() + "_" + studentVO.getStudentId() + "." + FilenameUtils.getExtension(multiPartFile.getOriginalFilename());
		Path path = Paths.get("./src/main/webapp/photos/" + fileName);
		if (!Files.exists(path))
		{
			Files.createDirectories(path);
		}
		Files.copy(multiPartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		OUT.info("Profile picture {} is uploaded successfully", multiPartFile.getOriginalFilename());

		StudentDetailMasterTO studentTO = new StudentDetailMasterTO();
		BeanUtils.copyProperties(studentVO, studentTO);
		studentTO.setAdmissioDate(CommonUtil.getCurrentSQLDate());
		studentTO.setDob(CommonUtil.convertIntoSQLDate(studentVO.getDob()));
		studentTO.setByteArray(multiPartFile.getBytes());
		studentTO.setId(studentVO.getStudentId());
		studentTO.setImagePath("../photos/" + fileName);
		
		int result = DBManager.getInstance().update(STUDENT_ADMISSION, studentTO);
		if(result > 0)
		{
			return studentTO.getId();
		}
		else 
		{
			return 0;
		}
	}
	
	/**
	 * @param clazz
	 * @param section
	 * @return
	 * @throws Exception
	 */
	public List<StudentDetailVO> getCardsDetails(Integer clazz, String section) throws Exception
	{
		List<StudentDetailVO> studentVOList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clazz", clazz);
		paramMap.put("section", section);

		List<StudentDetailMasterTO> studentTOList = DBManager.getInstance().getResultList(STUDENT_CARD_DETAILS, paramMap);

		studentTOList.forEach(studentTO -> {
			StudentDetailVO detailVO = new StudentDetailVO();
			detailVO.setStudentId(studentTO.getId());
			detailVO.setFirstName(studentTO.getFirstName() + " " + studentTO.getLastName());
			if(studentTO.getImagePath() != null)
			{
				Path path = Paths.get("./src/main/webapp/photos/" + studentTO.getImagePath().split("/")[2]);
				
				if (!Files.exists(path))
				{
					try
					{
						Files.createFile(path);
						Files.write(path, studentTO.getByteArray(), StandardOpenOption.WRITE);
					}
					catch (IOException e)
					{
						OUT.error(ApplicationConstants.EXCEPTION, e);
					}
				}
			}
			detailVO.setImagePath(studentTO.getImagePath());
			detailVO.setClazz(studentTO.getClazz());
			detailVO.setSection(studentTO.getSection());
			studentVOList.add(detailVO);
		});
		return studentVOList;
	}
	
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public String getDetailsJSON(Integer regNo) throws Exception
	{
		SimpleDateFormat formatDate = new SimpleDateFormat(CommonUtil.DD_MM_YYYY);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("studentId", regNo);
		paramMap.put("isForAdmission", true);
		
		StudentDetailMasterTO detailTO = (StudentDetailMasterTO) DBManager.getInstance().getResultAsObject(GET_BY_ID, paramMap);
		JSONObject json = new JSONObject();

		json.put("studentId", detailTO.getId());
		json.put("firstName", detailTO.getFirstName());
		json.put("lastName", detailTO.getLastName());
		json.put("fatherName", detailTO.getFatherName());
		json.put("motherName", detailTO.getMotherName());
		json.put("gender", detailTO.getGender());
		json.put("contactNo", detailTO.getContactNo());
		json.put("dob", formatDate.format(detailTO.getDob()));
		json.put("branchId", detailTO.getBranchId());
		json.put("session", detailTO.getSession());

		return json.toString();
	}
	
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public StudentDetailVO getStudentObject(Integer studentId) throws Exception
	{
		SimpleDateFormat formatDate = new SimpleDateFormat(CommonUtil.DD_MM_YYYY);
		StudentDetailVO detailVO = new StudentDetailVO();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("studentId", studentId);
		paramMap.put("isForUpdate", true);
		
		StudentDetailMasterTO detailTO = (StudentDetailMasterTO) DBManager.getInstance().getResultAsObject(GET_BY_ID, paramMap);
		BeanUtils.copyProperties(detailTO, detailVO);
		detailVO.setDob(detailTO.getDob() !=  null ? formatDate.format(detailTO.getDob()) : "");
		
		return detailVO;
	}
	
	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteById(int studentId) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("studentId", studentId);
		paramMap.put("isDeleted", 1);

		Integer count = DBManager.getInstance().update("StudentDetailMaster.deleteByStudentId", paramMap);

		if (count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
