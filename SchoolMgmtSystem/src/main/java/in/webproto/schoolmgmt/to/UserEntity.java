package in.webproto.schoolmgmt.to;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserEntity
{
	private int		userId;

	private String	firstName;

	private String	lastName;

	private String	userName;

	private String	password;

	private Date	lastLogin;

	private Date	lastLogout;

	private String	role;

	private Date	joiningDate;

	private String	email;

	private boolean	enabled;

	private boolean	isDeleted;
}
