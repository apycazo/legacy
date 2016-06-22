<%-- 
    Author     : Andres Picazo
    Anything under /WEB-INF will not be directly accesible to the client.
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
        <script src="<c:url value="/resources/js/jquery-2.1.0.min.js" />"></script>
        <script src="<c:url value="/resources/js/homepage.js" />"></script>
        <title>${Title}</title>
    </head>
    <body class="dark-theme">
        <h2 id="jqueryMessage"></h2>
        <hr>
        <h1>User message (Form version)</h1>
        <form:form method="POST" commandName="HomePagePostRequest">
            <!-- Will fill items using the controller @ModelAttribute method and store 
            the value on the HomePagePostRequest object, attribute "username"-->
            <span>User select: </span>
            <form:select id="usercombo" path="username">
                <form:options items="${userlist}"/>
            </form:select>
            <input type="submit" value="Get message" id="updateValue"/>
        </form:form>
        <span>Message: ${HomePageDataModel.selectedUserText}</span>
        <hr>
        <h1>User message (AJAX version)</h1>
            <button type="button" onclick="getUserMessage()" id="ajaxUpdateValue">Ajax Query</button>
            <span id="ajaxMessage"></span>
        <hr>
        <h1>All users data</h1>
        <table>
            <th>User</th>
            <th>Message</th>
            <c:forEach items="${HomePageDataModel.messages}" var="msg">
                <tr>
                    <td>${msg.username} </td>
                    <td>${msg.text}</td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <span>Go to another page, using location: </span>
        <input type="button"  onclick="location.href='/spring_mvc/about'" value="About"/>
        <hr>
        <span>Throw forced exception to capture</span>
        <input type="button"  onclick="location.href='/spring_mvc/home/fail'" value="Fail test"/>
    </body>
</html>
