<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showannouncements" class="management">
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
                                    <a class="btn" href="<c:url value="/announcements/new.html"/>">Dodaj wiadomość</a>
                                </td>
                            </tr>
                        </table>
                        <br />
                        <c:if test="${not empty announcements}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Tytuł</th>
                                    <th>Data utworzenia</th>
                                    <th colspan="2" class="lastcol">&nbsp;</th>
                                </tr>
                                <c:forEach var="announcement" items="${announcements}">
                                    <tr>
                                        <td><a href="<c:url value="/announcements/${announcement.announcementId}/show.html"/>"><c:out value="${announcement.title}"/></a></td>
                                        <td><c:out value="${announcement.createDate}"/></td>
                                        <td class="ticketEdit"><a href="<c:url value="/announcements/${announcement.announcementId}/edit.html"/>">Edit</a></td>
                                        <td class="lastcol ticketDrop"><a href="<c:url value="/announcements/${announcement.announcementId}/remove.html"/>">Del</a></td>
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
