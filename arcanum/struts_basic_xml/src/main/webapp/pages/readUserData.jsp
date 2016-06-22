
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Read user data</title>
    </head>
    <body>
        <!-- Action to run when submitted -->
        <s:form action="ShowReadUser">
            <s:textfield name="username" label="User Name" value=""/>
            <s:textfield name="userid" label="User ID" value=""/>
            <s:submit/>
        </s:form>
    </body>
</html>
