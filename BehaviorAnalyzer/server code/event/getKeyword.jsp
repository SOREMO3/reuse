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

JSONArray arr = new JSONArray();

Connection conn = getDB();

String sql = "SELECT rk_keyword, rk_click_count, rk_weight FROM rd_keyword where rk_keyword like '%" +term+ "%' order by rk_weight desc limit 30";
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
	pstmt = conn.prepareStatement(sql);
	rs = pstmt.executeQuery();
	int count = 1;
	while(rs.next()) {
		JSONObject key = new JSONObject();
		String keyword = rs.getString("rk_keyword");
		int clickCount = rs.getInt("rk_click_count");
		key.put("label", keyword);
		key.put("value", keyword);
		key.put("click", clickCount);
		key.put("index", count);
		if(key != null) {
			arr.add(key);
			count++;
		}
	}	

	String result = arr.toJSONString();
	out.println(callBack + "(" + result + ")");
} catch (Exception e) {
	System.out.println("Exception: " + e.getMessage());
}
%>
