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
String eventType = request.getParameter("type");

double weight = 0;

if(eventType == "submit") {
	weight = 4;
} else if (eventType == "keyword click") {
	weight = 2;
} else if (eventType == "keyword hover") {
	weight = 0.01;
} else {
	weight = 0.1;
}

Connection conn = getDB();

String sql = "UPDATE rd_keyword SET rk_weight = rk_weight+"+weight+" WHERE rk_keyword = '"+term+"'";

PreparedStatement pstmt = null;
int updateCount = 0;


try {
	pstmt = conn.prepareStatement(sql);
	updateCount = pstmt.executeUpdate();
} catch (Exception e) {
	System.out.println("Exception: " + e.getMessage());
}

out.println(callBack+"(");
out.println("{\"flag\": \"success\", \"message\": \""+updateCount+" rows updated\", \"weight\": \""+weight+"\", \"type\": \""+eventType+"\", \"sql\": \""+sql+"\"}");
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
