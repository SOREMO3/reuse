<%@ page contentType="application/jsonp; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
    
<%!
// ----------------------------------
// Method list
// ----------------------------------

public Connection getDB() {
    Connection conn = null;
    
    try {
        String url = "server db url/db name";
        String id = "user name";
        String pw = "password";

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url,id,pw);
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return conn;
}

// ----------------------------------
// Method list //
// ----------------------------------
%>
