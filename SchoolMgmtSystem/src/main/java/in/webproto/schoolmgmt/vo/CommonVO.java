package in.webproto.schoolmgmt.vo;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonVO implements IModel
{
	private Integer	integer1;

	private Integer	integer2;

	private String	string1;

	private String	string2;
	
	private byte[]	byteArray;

}
