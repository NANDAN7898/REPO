package in.webproto.schoolmgmt.vo;

import java.util.List;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationVO implements IModel
{
	private Integer				userId;

	private String				username;

	private String				password;

	private Boolean				enabled;

	private String				authority;

	private String				firstName;

	private String				lastName;

	private Boolean				locked;
	
	private List<String>		rolePermission;

}
