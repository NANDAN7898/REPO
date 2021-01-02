package in.webproto.schoolmgmt.to;

import java.sql.Date;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDetailMasterTO implements IModel
{
	private Integer	id;

	private String	firstName;

	private String	lastName;

	private Date	joiningDate;

	private String	email;

	private String	username;

	private String	password;

	private Long	lastLogin;

	private Long	lastLogout;

	private String	role;

	private Boolean	enabled;

	private Boolean	isDeleted;

	private Boolean	isLocked;
}
