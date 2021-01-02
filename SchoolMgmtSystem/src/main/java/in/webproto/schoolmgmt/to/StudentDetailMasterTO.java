package in.webproto.schoolmgmt.to;

import java.sql.Date;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StudentDetailMasterTO implements IModel
{
	private Integer	id;

	private String	firstName;

	private String	lastName;

	private Integer	branchId;

	private String	session;

	private Date	dob;

	private Long	aadhar;

	private String	email;

	private String	gender;

	private String	nationality;

	private Double	registrationNo;

	private String	clazz;

	private String	section;

	private String	busRoute;
	
	private Double	feeHead;

	private Date	regDate;

	private Date	admissioDate;

	private Double	enrollmentNo;

	private String	bloodGroup;

	private Long	contactNo;

	private String	caste;

	private String	religion;

	private String	category;

	private String	guardianName;

	private String	guardianRelation;

	private Long	guardianContact;

	private String	occupation;

	private String	localAddress;

	private String	localCity;

	private String	localDistrict;

	private String	localState;

	private Integer	localPin;

	private String	permanentAddress;

	private String	permanentCity;

	private String	permanentDistrict;

	private String	permanentState;

	private Integer	permanentPin;

	private String	fatherName;

	private Long	fatherContact;

	private String	fatherOccupation;

	private String	fatherEducation;

	private String	motherName;

	private Long	motherContact;

	private String	motherOccupation;

	private String	motherEducation;

	private String	preSchool;

	private String	preSchAddress;

	private Long	preSchoolContact;

	private String	lastExam;

	private Double	percentage;

	private String	board;

	private String	preRemarks;

	private String	examYear;

	private byte[]	byteArray;
	
	private String	imagePath;
	
	private Boolean	isEnablePreSchInfo;
}
