package com.schoolmgmt.to;

import com.schoolmgmt.dao.IModel;

public class StudentDetailMasterTO implements IModel
{
	private int		id;

	private String	name;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(" StudentDetailMasterTO = ")
		.append("[").append(" name = ").append(name).append("]");
		return builder.toString();
	}
}
