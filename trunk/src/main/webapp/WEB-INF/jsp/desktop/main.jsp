<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="desktop">
    <div id="pagecontentheader">
        <table cellspacing="0">
            <tr>
                <td id="pagecontentheaderleft"><h2>Moje biurko</h2></td>
                <td id="pagecontentheaderright">
                    <a href="<c:url value="/newBug.html"/>"><img src="<c:url value="/i/icons/48/evolution.png"/>" alt="s1" width="48" height="48"/></a>
                    <a href="<c:url value="/showNewBugs.html"/>"><img src="<c:url value="/i/icons/48/gnome-xterm.png"/>" alt="s2" width="48" height="48"/></a>
                    <a href="<c:url value="/showNonresolvedBugsAssignedToMe.html"/>"><img src="<c:url value="/i/icons/48/file-manager.png"/>" alt="s3" width="48" height="48"/></a>
                    <a href="<c:url value="/stats/my.html"/>"><img src="<c:url value="/i/icons/48/gnome-applications.png"/>" alt="s4" width="48" height="48"/></a>
                    <a href="<c:url value="/help/base.html"/>"><img src="<c:url value="/i/icons/48/gnome-help.png"/>" alt="s5" width="48" height="48"/></a>
                </td>
            </tr>
        </table>
    </div>
    <div id="desktoppanels">
        <table id="desktoppanelstable" cellspacing="0">
            <tr class="desktoppanelstableheader">
                <td class="leftcells lastEvents"><div id="pagecontentsubheader"><h3>Ostatnie zdarzenia</h3></div></td>
                <td class="rightcells lastBugs"><div id="pagecontentsubheader"><h3>Ostatnie nieprzypisane zgłoszenia</h3></div></td>
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
                                    <th>Data</th>
                                    <th class="lastcol">&nbsp;</th>
                                </tr>
                                <c:forEach var="event" items="${lastEvents}">
                                    <tr>
                                        <td><c:out value="${event.evtSubject}"/></td>
                                        <td><c:out value="${event.eventType}"/></td>
                                        <td><fmt:formatDate value="${event.evtDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td class="lastcol"><a href="<c:url value="/bugDetails.html?bugId=${event.bugId}"/>">wiecej</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
                <td class="rightcells lastBugs">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastBugs}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Temat</th>
                                    <th>Zgłaszający</th>
                                    <th>Kategoria</th>
                                    <th>Priorytet</th>
                                    <th>Data</th>
                                    <th class="lastcol">&nbsp;</th>
                                </tr>
                                <c:forEach var="bug" items="${lastBugs}">
                                    <tr>
                                        <td><c:out value="${bug.subject}"/></td>
                                        <td><c:out value="${bug.notifier}"/></td>
                                        <td>
                                            <c:choose>
										        <c:when test="${bug.bugCategory.bugCategoryId == 0}">
										            Brak
										        </c:when>
										        <c:otherwise>
										            <c:out value="${bug.bugCategory}"/>
										        </c:otherwise>
										    </c:choose>
                                        </td>
                                        <td><c:out value="${bug.bugPriority}"/></td>
                                        <td><fmt:formatDate value="${bug.createDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td class="lastcol"><a href="<c:url value="/bugDetails.html?bugId=${bug.bugId}"/>">wiecej</a></td>
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
                                        <td><c:out value="${article.title}"/></td>
                                        <td class="lastcol"><c:out value="${article.createDate}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </td>
                <td class="rightcells lastInfo">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:if test="${not empty lastInformations}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Data</th>
                                    <th class="lastcol">Tytuł</th>
                                </tr>
                                <c:forEach var="information" items="${lastInformations}">
                                    <tr>
                                        <td><c:out value="${information.createDate}"/></td>
                                        <td class="lastcol"><c:out value="${information.title}"/></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="lastcol"><c:out value="${information.lead}"/></td>
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