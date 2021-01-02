package in.webproto.schoolmgmt.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil
{
	private static final Logger	OUT	= LoggerFactory.getLogger(CommonUtil.class);
	
	public static final String				DISPLAY_DATE_FORMAT	= "dd MMM yyyy";

	public static final String				SQL_DATE_FORMAT		= "yyyy-MM-dd";
	
	public static final String				DD_MM_YYYY			= "dd/MM/yyyy";

	private static final SimpleDateFormat	sqlFormat			= new SimpleDateFormat(SQL_DATE_FORMAT);

	/**
	 * @return
	 */
	public static Date getCurrentSQLDate()
	{
		Calendar cal = Calendar.getInstance();
		String sessionDate = null;
		sessionDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
		return Date.valueOf(sessionDate);
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	public static Date convertIntoSQLDate(String date) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		java.util.Date utilDate = format.parse(date);
		return Date.valueOf(sqlFormat.format(utilDate));
	}

	/**
	 * @param list
	 * @return
	 * 
	 */
	public static String convertListToCommaSeperatedString(List<String> list)
	{
		int count = 0;
		StringBuilder builder = new StringBuilder();
		if (list != null && !list.isEmpty())
		{
			for (String str : list)
			{
				count++;
				builder.append(str);
				if (list.size() > count)
				{
					builder.append(", ");
				}
			}
		}
		return builder.toString();
	}

	/**
	 * @param string
	 * @return
	 */
	public static List<String> convertCommaSepStringToList(String string)
	{
		List<String> strList = new ArrayList<>();
		if (string != null && string.contains(","))
		{
			String[] strArray = string.split(",");
			for (String value : strArray)
			{
				strList.add(value.trim());
			}
		}
		else
		{
			strList.add(string);
		}
		return strList;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getDisplayDateFormat(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
		if (date != null)
		{
			return format.format(date);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * @param object
	 * @return
	 */
	public static String convertCharArrayToCommaString(Character[] object)
	{
		StringBuilder builder = new StringBuilder();
		if (object != null && object.length < 1)
		{
			return builder.toString();
		}

		for (char section : object)
		{
			if (builder.length() > 0)
			{
				builder.append(", ");
			}
			builder.append(section);
		}
		return builder.toString();
	}
}
