<%@ page contentType="application/jsonp; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%@include file="/event/eventDBfunctions.jsp"%>

<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

String callBack = request.getParameter("callback");
String term = request.getParameter("term");

Connection conn = getDB();

String sql = "UPDATE rd_keyword SET rk_click_count = rk_click_count+1 WHERE rk_keyword = '"+term+"'";

PreparedStatement pstmt = null;
int updateCount = 0;


try {
	pstmt = conn.prepareStatement(sql);
	updateCount = pstmt.executeUpdate();
} catch (Exception e) {
	System.out.println("Exception: " + e.getMessage());
}

out.println(callBack+"(");
out.println("{\"flag\": \"success\", \"message\": \""+updateCount+" rows updated\",\"object\": \""+term+"\", }");
out.println(")");
out.flush();
out.close();

if(pstmt != null)
	try {
		pstmt.close();
	} catch (SQLException sqle) {
	}   // PreparedStatement 객체 해제

if(conn != null)
	try {
		conn.close();
	} catch (SQLException sqle) {
	}   // Connection 해제



%>
