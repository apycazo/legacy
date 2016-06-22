<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quinielator config</title>
    </head>
    <body>
        <h1>Database</h1>
        <form:form name="updateDB" method="POST" commandName="config" >
            <input type="submit" name="updateDB" value="Update quinielator database" path="request"/>    
        </form:form>
        <a href="config.updatedb.htm?update=true"><input type="button" value="Update DB"></input></a>
        <a href="config.htm?update=true"><input type="button" value="Update DB (Same .htm)"></input></a>
        <a href="../main.htm">Back to main page</a>
    </body>
</html>
