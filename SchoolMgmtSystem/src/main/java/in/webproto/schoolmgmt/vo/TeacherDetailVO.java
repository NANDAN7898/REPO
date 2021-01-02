package in.webproto.schoolmgmt.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherDetailVO
{
	private Integer	id;

	private String	name;

	private String	fatherName;

	private Long	mobile;

	private String	dob;

	private String	subject;

	private String	address;

	private String	district;

	private String	state;

	private String	email;

	private String	joiningDate;

	private String	highQualification;

	private Double	salary;

	private String	specialization;

	private Integer	experience;

	private Integer	pin;
}
