package in.webproto.schoolmgmt.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StudentDetailVO
{
	private Integer					studentId;

	private String					firstName;

	private String					lastName;

	private Integer					branchId;

	private List<CommonVO>			branchList;

	private String					session;

	private List<String>			sessionList;

	private Long					aadhar;

	private String					dob;

	private String					email;

	private String					gender;

	private Map<String, String>		genderMap;

	private String					nationality;

	private Double					regNo;

	private String					regDate;

	private String					admDate;

	private Double					enrollNo;

	private String					bloodGroup;

	private Long					contactNo;
	
	private String					section;
	
	private String					clazz;
	
	private	String					busRoute;
	
	private Double					feeHead;

	private String					caste;

	private String					religion;

	private String					category;

	private String					guardianName;

	private String					guardianRelation;

	private Long					guardianContact;

	private String					occupation;

	private String					localAddress;

	private String					localCity;

	private String					localDistrict;

	private String					localState;

	private Integer					localPin;

	private String					permanentAddress;

	private String					permanentCity;

	private String					permanentDistrict;

	private String					permanentState;

	private Integer					permanentPin;

	private String					fatherName;

	private Long					fatherContact;

	private String					fatherOccupation;

	private String					fatherEducation;

	private String					motherName;

	private Long					motherContact;

	private String					motherOccupation;

	private String					motherEducation;

	private String					preSchool;

	private String					preSchAddress;

	private Long					preSchoolContact;

	private String					lastExam;

	private Double					percentage;

	private String					board;

	private String					preRemarks;

	private String					examYear;

	private String					imagePath;
	
	private Boolean					isEnablePreSchInfo;
	
}
