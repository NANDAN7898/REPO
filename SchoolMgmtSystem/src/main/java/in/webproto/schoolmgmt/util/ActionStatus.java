package in.webproto.schoolmgmt.util;

public enum ActionStatus
{
	PROCESSING("PROCESSING"), SUCCESSFUL("SUCCESSFUL"), FAILURE("FAILURE"), PENDING("PENDING"), EXCEPTION("EXCEPTION"), STATUS("STATUS");;

	private String type;

	/**
	 * @param status
	 */
	private ActionStatus(String type)
	{
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getDisplayLabel()
	{
		return this.type;
	}
}
