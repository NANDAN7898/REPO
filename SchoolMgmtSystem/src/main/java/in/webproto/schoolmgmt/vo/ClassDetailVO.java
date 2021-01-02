package in.webproto.schoolmgmt.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClassDetailVO
{
	private Integer	id;

	private String	className;

	private Integer	noOfSection;

	private Integer	maxStudent;
}
