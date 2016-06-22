
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Some exception has been captured</h1>
        <h3>Exception name   : ${name}</h3>
        <h3>Exception message: ${message}</h3>
        <a href="/spring_mvc/home">Back home</a>
    </body>
</html>
