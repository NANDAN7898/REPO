package in.webproto.schoolmgmt.to;

import java.sql.Date;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherMasterTO implements IModel
{
	private Integer	id;

	private String	name;

	private String	fatherName;

	private Long	mobile;

	private Date	dob;

	private String	subject;

	private String	address;

	private String	district;

	private String	state;

	private String	email;

	private Date	joiningDate;

	private String	highQualification;

	private Double	salary;

	private String	specialization;

	private Integer	experience;

	private Integer	pin;
}
