<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="desktop">
    <div id="pagecontentheader">
        <table cellspacing="0">
            <tr>
                <td id="pagecontentheaderleft"><h2><fmt:message key="desktop.title"/></h2></td>
                <td id="pagecontentheaderright">
                    <a href="<c:url value="/tickets/new.html"/>"><img src="<c:url value="/themes/blue/i/icons/48/evolution.png"/>" alt="s1" width="48" height="48"/></a>
                    <a href="<c:url value="/help/kb/index.html"/>"><img src="<c:url value="/themes/blue/i/icons/48/gnome-help.png"/>" alt="s5" width="48" height="48"/></a>
                </td>
            </tr>
        </table>
    </div>
    <div id="desktoppanels">
        <table id="desktoppanelstable" cellspacing="0">
            <tr class="desktoppanelstableheader">
                <td class="leftcells lastEvents"><div id="pagecontentsubheader"><h3>Ostatnie zdarzenia</h3></div></td>
                <td class="rightcells lastTickets"><div id="pagecontentsubheader"><h3>Ostatnie nieprzypisane zgłoszenia</h3></div></td>
            </tr>
            <tr class="desktoppanelstabledata">
                <td class="leftcells lastEvents">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastEvents}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Zdarzenie</th>
                                    <th>Rodzaj</th>
                                    <th class="lastcol">Data</th>
                                </tr>
                                <c:forEach var="event" items="${lastEvents}">
                                    <tr>
                                        <td><a href="<c:url value="/tickets/${event.ticket.ticketId}/details.html"/>"><c:out value="${event.evtSubject}"/></a></td>
                                        <td><c:out value="${event.eventType}"/></td>
                                        <td class="lastcol"><fmt:formatDate value="${event.evtDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
                <td class="rightcells lastTickets">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastTickets}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Temat</th>
                                    <th>Zgłaszający</th>
                                    <th>Kategoria</th>
                                    <th>Priorytet</th>
                                    <th class="lastcol">Data</th>
                                </tr>
                                <c:forEach var="ticket" items="${lastTickets}">
                                    <tr>
                                        <td><a href="<c:url value="/tickets/${ticket.ticketId}/details.html"/>"><c:out value="${ticket.subject}"/></a></td>
                                        <td><c:out value="${ticket.notifier}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ticket.ticketCategory.ticketCategoryId == 0}">
                                                    Brak
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${ticket.ticketCategory}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${ticket.ticketPriority}"/></td>
                                        <td class="lastcol"><fmt:formatDate value="${ticket.createDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
            </tr>
            <tr class="desktoppanelstableheader">
                <td class="leftcells lastArticles">
                    <div id="pagecontentsubheader"><h3>Ostatnie artykuły</h3></div>
                </td>
                <td class="rightcells lastInfo">
                    <div id="pagecontentsubheader"><h3>Ostatnie informacje</h3></div>
                </td>
            </tr>
            <tr class="desktoppanelstabledata">
                <td class="leftcells lastArticles">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastArticles}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Tytuł</th>
                                    <th class="lastcol">Data</th>
                                </tr>
                                <c:forEach var="article" items="${lastArticles}">
                                    <tr>
                                        <td><a href="<c:url value="/help/base/showOne.html?id=${article.articleId}"/>"><c:out value="${article.title}"/></a></td>
                                        <td class="lastcol"><fmt:formatDate value="${article.createDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
                <td class="rightcells lastInfo">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastAnnouncements}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Data</th>
                                    <th class="lastcol">Tytuł</th>
                                </tr>
                                <c:forEach var="announcement" items="${lastAnnouncements}">
                                    <tr>
                                        <td><fmt:formatDate value="${announcement.createDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td class="lastcol">
                                            <a href="<c:url value="/announcements/${announcement.announcementId}/show.html"/>"><c:out value="${announcement.title}"/></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="lastcol"><c:out value="${announcement.lead}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>