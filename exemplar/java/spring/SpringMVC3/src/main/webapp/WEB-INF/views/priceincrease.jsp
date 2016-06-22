<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="priceincrease.heading"/></h1>

<!--
commandName = name of a variable in the request scope or session scope that 
contains the information about this form, it should be a bean.

path = name of a bean property that should be accessed in order to pass the 
information to from and to the controller, from the controller when you want 
your inputs to have a "starting value" for example when you want to update a 
record, and to the controller when you submit your form.
-->

<!-- The commandName "priceIncreaser, is generated automatically for the form, 
and initialized with the formBackingObject (uses PriceIncreaser class)" 
-->

<form:form method="post" commandName="priceIncreaser">
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="20%">Increase (%):</td>
        <td width="20%">
          <form:input path="percentage"/>
        </td>
        <td width="60%">
          <form:errors path="percentage" cssClass="error"/>
        </td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Execute">
</form:form>
<a href="<c:url value="hello.htm"/>">Home</a>
</body>
</html>
