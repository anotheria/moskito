<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
    // This scriptlet declares and initializes "date"
    System.out.println( "Evaluating date now" );
    java.util.Date date = new java.util.Date();
    String link = request.getServletPath();
    out.println(link);
    out.println("<BR>");
    if (link.contains("est")){
    	out.println("YES");    	
    } else {
    	out.println("NO");
    }
%>

<select>
	<option value="<a href="google.com"/>">1</option>
	<option>2</option>
	<option>3</option>
</select>

</body>
</html>