<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showuser" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="leftcells">
                <div id="pagecontentsubheader"><h3>Podgąd danych użytkownika</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr>
                                <td>Login</td>
                                <td class="lastcol"><c:out value="${user.login}"/></td>
                            </tr>
                            <tr>
                                <td>Imię</td>
                                <td class="lastcol"><c:out value="${user.firstName}"/></td>
                            </tr>
                            <tr>
                                <td>Nazwisko</td>
                                <td class="lastcol"><c:out value="${user.lastName}"/></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td class="lastcol"><c:out value="${user.email}"/></td>
                            </tr>
                            <tr>
                                <td>Telefon</td>
                                <td class="lastcol"><c:out value="${user.phone}"/></td>
                            </tr>
                            <tr>
                                <td>Mobile</td>
                                <td class="lastcol"><c:out value="${user.mobile}"/></td>
                            </tr>
                        </table>
                        <br/>
                        <table cellspacing="0">
                            <tr>
                                <td colspan="2">
                                    <a class="btn" href="<c:url value="/manage/users/list.html"/>">Powrót do listy użytkowników</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                </div>

            </td>
        </tr>
    </table>
</div>