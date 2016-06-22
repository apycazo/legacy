<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iterated model view</title>
    </head>
    <body>
        <h1>Showing all elements</h1>
        <table>
            <s:iterator value="users" status="users_status">
                <tr>
                    <s:if test="#users_status.even == true">
                        <td style="background: #CCCCCC"><s:property/></td>
                    </s:if>
                    <s:elseif test="#users_status.first == true">
                        <td><s:property/> (#!)</td>
                    </s:elseif>
                    <s:else>
                        <td><s:property/></td>
                    </s:else>
                </tr>
            </s:iterator>
        </table>
        <h1>Showing all domain elements</h1>
        <table>
            <th style="background: #CCCCCC">User name</th>
            <th style="background: #CCCCCC">User id</th>
            <s:iterator value="userdomains" status="userdomains_status">
                <tr>
                    <s:if test="#userdomains_status.even == true">
                        <td style="background: #CCCCCC"><s:property value="username"/></td>
                        <td style="background: #CCCCCC"><s:property value="userid"/></td>
                    </s:if>
                    <s:else>
                        <td><s:property value="username"/></td>
                        <td><s:property value="userid"/></td>
                    </s:else>
                </tr>
            </s:iterator>
        </table>
    </body>
</html>
