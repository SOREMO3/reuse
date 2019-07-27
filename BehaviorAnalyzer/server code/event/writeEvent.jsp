<%@ page contentType="application/jsonp; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%@include file="/event/eventDBfunctions.jsp"%>

<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");

String callback = request.getParameter("callback");

String eventType = request.getParameter("type");
String eventUser = request.getParameter("user");
String eventPage = request.getParameter("page");
String eventValue = request.getParameter("value");
String eventDate = request.getParameter("date");

Connection conn = getDB();

String sql = "INSERT INTO user_event (ue_type, ue_user, ue_page, ue_value, ue_date) VALUES (" 
		+ "'" + eventType + "', " + "'" + eventUser + "', " + "'" + eventPage + "', " 
		+ "'" + eventValue + "', " + "'" + eventDate + "'" + ")";										
		
PreparedStatement pstmt = null;

try {
	pstmt = conn.prepareStatement(sql);
	pstmt.executeUpdate();
} catch (Exception e) {
	System.out.println("Exception: " + e.getMessage());
}

out.println(callback+"(");
out.println("{\"flag\": \"success\", \"message\": \"write event success\"}");
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
