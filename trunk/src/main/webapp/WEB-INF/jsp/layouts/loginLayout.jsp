<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title><tiles:getAsString name="title"/> - (${pageContext.response.locale})</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <spring:theme code="login.css" var="loginCss"/>
        <spring:theme code="style.css" var="styleCss"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="${styleCss}"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="${loginCss}"/>" />
    </head>
    <body>

        <div id="loginpage">
            <div class="infobox1">
                <div class="infobox2">
                    <h2><fmt:message key="prompt.title"/></h2>
                </div>
            </div>
            <div class="infobox1">
                <div class="infobox2">
                    <form name="loginForm" action="<c:url value="/login.html"/>" method="post">
                        <table cellspacing="0">
                            <tr>
                                <td class="labels"><b><fmt:message key="prompt.login"/></b></td>
                                <td class="inputs">
                                    <spring:bind path="user.login">
                                        <input type="text" name="<c:out value="${status.expression}"/>" value="" size="23"/>
                                    </spring:bind>
                                </td>
                                <td class="images" rowspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="left"><b><fmt:message key="prompt.passw"/></b></td>
                                <td>
                                    <spring:bind path="user.password">
                                        <input type="password" name="<c:out value="${status.expression}"/>" size="23"/>
                                    </spring:bind>
                                </td>
                            </tr>
                            <tr>
                                <td class="left">&nbsp;</td>
                                <td><input type="submit" value="<fmt:message key="prompt.logon"/>"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
