<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quinielator V2</title>
        <style>
        </style>
    </head>
    <body>
        <h2>Quinielator Configuration</h2>
        <!-- commandName: model attribute element -->
        <form:form method="POST" commandName="predictionRequest">
            <table>
                <tr>
                    <td>Select year</td>
                    <td>
                        <!-- path: updates attribute from commandName model element -->
                        <form:select path="year">
                            <%--<form:option value="2010" label="--- Select ---"/>--%>
                            <form:options items="${yearList}"/>
                        </form:select>
                    </td>    
                </tr>
                <tr>
                    <td>Select day</td>
                    <td>
                        <form:select path="day">
                            <form:option value="1" label="--- Select ---"/>
                            <form:options items="${dayList}"/>
                        </form:select>
                    </td>    
                </tr>
                <tr>
                    <td>Select predictor algorithm</td>
                    <td>
                        <form:select path="predictor">
                            <form:option value="Default" label="--- Select ---"/>
                            <form:options items="${predictorList}"/>
                        </form:select>
                    </td>    
                </tr>

                <tr>
                    <td colspan="3"><input type="submit" value="Send request"/></td>
                </tr>
            </table>

        </form:form>
        <br/>
        <a href="config/config.htm">Configure</a>
        <br/>
        <h2>Request: ${predictionRequest.stringValue}</h2>
    </body>
</html>
