<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showinformations" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="rightcells">
                <div id="pagecontentsubheader"><h3>Wiadomości</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0">
                            <tr>
                                <td>
                                    <a class="btn" href="<c:url value="/manage/information/edit.html"/>">Dodaj wiadomość</a>
                                </td>
                            </tr>
                        </table>
                        <br />
                        <c:if test="${not empty informations}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Tytuł</th>
                                    <th>Data utworzenia</th>
                                    <th colspan="2" class="lastcol">&nbsp;</th>
                                </tr>
                                <c:forEach var="information" items="${informations}">
                                    <tr>
                                        <td><c:out value="${information.title}"/></td>
                                        <td><c:out value="${information.createDate}"/></td>
                                        <td class="ticketEdit"><a href="<c:url value="/manage/information/edit.html?infoId=${information.informationId}"/>">Edit</a></td>
                                        <td class="lastcol ticketDrop"><a href="<c:url value="/manage/information/remove.html?infoId=${information.informationId}"/>">Del</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
</div>
