<%@page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Error... 500</title>
</head>

<body bgcolor="#FFFFFF">
      Uuu.. wystapił wewnętrzny błąd serwera (lub aplikacji). <p>
	<font color="red"><%= exception.getMessage() %></font><p>
</body>

</html>
