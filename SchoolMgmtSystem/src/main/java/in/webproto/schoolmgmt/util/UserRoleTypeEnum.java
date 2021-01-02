package in.webproto.schoolmgmt.util;

public enum UserRoleTypeEnum
{
	SUPERADMIN(1), ADMIN(2), ACCOUNTANT(4), TEACHER(3), STUDENT(4);

	private Integer roleTypeId;

	/**
	 * @param role
	 */
	private UserRoleTypeEnum(Integer role)
	{
		this.roleTypeId = role;
	}

	/**
	 * @return
	 */
	public Integer getRoleTypeId()
	{
		return roleTypeId;
	}
}
