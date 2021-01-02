package in.webproto.schoolmgmt.to;

import in.webproto.schoolmgmt.db.IModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClassMasterTO implements IModel
{
	private Integer	id;

	private String	className;

	private String	sections;

	private Integer	maxStudent;
}
