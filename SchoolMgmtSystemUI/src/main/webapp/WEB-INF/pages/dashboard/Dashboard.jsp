<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<head>
	<tiles:insertAttribute name="title" ignore="true"/>
</head>

<table border="1" style="width: 100%; height: 100%;">
	<tr bgcolor="" height="40%">
		<td colspan="2"><tiles:insertAttribute name="header"/></td>
	</tr>
	<tr style="width: 40%; height: 50%;">
		<td bgcolor="#FFFFFF"><tiles:insertAttribute name="body"/></td>
	</tr>
	<tr height="10%" bgcolor="cyan">
		<td colspan="2"><tiles:insertAttribute name="footer"/></td>
	</tr>
</table>
		