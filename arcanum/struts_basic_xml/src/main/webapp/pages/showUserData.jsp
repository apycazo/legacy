
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User data</title>
    </head>
    <body>
        <h1>Showing user data</h1>
        User name : <s:property value="username"/><br>
        User id : <s:property value="userid"/><br>
    </body>
</html>
