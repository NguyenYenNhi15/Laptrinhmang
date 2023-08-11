<%-- 
    Document   : result_page.jsp
    Created on : Jul 29, 2023, 5:16:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RESULT</title>
    </head>
    
    <body>
        <h3>Result page</h3>
        <div id="content">
            <p>Name: <c:out value="${user.getUserName}"/></p>
            <p>Password: <c:out value="${user.getPassword()}"/></p>
        </div>
    </body>
</html>
