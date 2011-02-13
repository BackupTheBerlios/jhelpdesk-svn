<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="showuser" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="leftcells">
                <div id="pagecontentsubheader"><h3>Edycja danych użytkownika</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:url value="/manage/users/save.html" var="formUrl"/>
                        <form:form action="${formUrl}" commandName="user" >
                            <c:if test="${user.userId != null}">
                                <form:hidden path="userId"/>
                            </c:if>
                            <table cellspacing="0" class="standardtable">
                                <spring:hasBindErrors name="user">
                                <tr>
                                    <td colspan="2" style="color: red" class="lastcol">
                                        <form:errors path="*" />
                                    </td>
                                </tr>
                                </spring:hasBindErrors>
                                <tr>
                                    <td>Imię</td>
                                    <td class="lastcol"><form:input path="firstName"/></td>
                                </tr>
                                <tr>
                                    <td>Nazwisko</td>
                                    <td class="lastcol"><form:input path="lastName"/></td>
                                </tr>
                                <tr>
                                    <td>Login</td>
                                    <td class="lastcol"><form:input path="login"/></td>
                                </tr>
                                <tr>
                                    <td>Hasło</td>
                                    <td class="lastcol"><form:password path="password"/></td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td class="lastcol"><form:input path="email"/></td>
                                </tr>
                                <tr>
                                    <td>Telefon</td>
                                    <td class="lastcol"><form:input path="phone"/></td>
                                </tr>
                                <tr>
                                    <td>Mobile</td>
                                    <td class="lastcol"><form:input path="mobile"/></td>
                                </tr>
                                <tr>
                                    <td>Rola</td>
                                    <td class="lastcol">
                                        <form:select id="userRole" path="userRole" items="${roles}" itemValue="roleCode" itemLabel="roleName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label for="active1">Aktywny</label></td>
                                    <td class="lastcol"><form:checkbox id="active1" path="active"/></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="2"><input type="submit" value="zapisz" class="btn" /></td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
</div>