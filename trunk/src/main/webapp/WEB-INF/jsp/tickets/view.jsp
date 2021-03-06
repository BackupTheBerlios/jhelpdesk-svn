<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="ticketdetails">
    <div id="pagecontentheader"><h2>Podgląd zgłoszenia</h2></div>
    <div id="desktoppanels">
        <table id="desktoppanelstable" cellspacing="0">
            <tr class="desktoppanelstableheader">
                <td class="rightcells lastTickets">
                    <div id="pagecontentsubheader"><h3>Opis problemu</h3></div>
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr>
                                <th colspan="3" style="width: 258px;">Identyfikator</th>
                                <th colspan="3" class="lastcol">Data</th>
                            </tr>
                            <tr>
                                <td colspan="3" style="width: 258px; font-weight: bold;"><c:out value="${ticket.ticketId}"/></td>
                                <td colspan="3" class="lastcol" style="font-weight: bold;"><fmt:formatDate value="${ticket.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                            </tr>
                            <tr>
                                <th colspan="6" class="lastcol">Przyczyna</th>
                            </tr>
                            <tr>
                                <td colspan="6" class="lastcol"><c:out value="${ticket.subject}" /></td>
                            </tr>
                            <tr>
                                <th colspan="2">Status</th>
                                <th colspan="2">Kategoria</th>
                                <th colspan="2" class="lastcol">Ważność</th>
                            </tr>
                            <tr>
                                <td colspan="2" style="width: 170px;">
                                    <select size="1">
                                        <c:forEach var="status" items="${ticketStatuses}">
                                            <option value="${status.statusId}" <c:if test="${status.statusId == ticket.ticketStatus.statusId}">selected="selected"</c:if>>
                                                <c:out value="${status}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td colspan="2" style="width: 170px;">
                                    <select size="1">
                                        <c:forEach var="category" items="${ticketCategories}">
                                            <option value="${category.ticketCategoryId}" <c:if test="${category.ticketCategoryId == ticket.ticketCategory.ticketCategoryId}">selected="selected"</c:if>>
                                                <c:out value="${category}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td colspan="2" class="lastcol">
                                    <select size="1">
                                        <c:forEach var="priority" items="${ticketPriorities}">
                                            <option value="${priority.priorityId}" <c:if test="${priority == ticket.ticketPriority}">selected="selected"</c:if>>
                                                <c:out value="${priority}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th colspan="6" class="lastcol">Opis</th>
                            </tr>
                            <tr>
                                <td colspan="6" class="lastcol"><c:out value="${ticket.description}" escapeXml="false" /></td>
                            </tr>
                            <c:if test="${not empty ticket.stepByStep}">
                                <tr>
                                    <th colspan="6" class="lastcol">Krok po kroku</th>
                                </tr>
                                <tr>
                                    <td colspan="6" class="lastcol"><c:out value="${ticket.stepByStep}" escapeXml="false"/></td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                    <c:if test="${not empty ticket.addFilesList}">
                        <div id="pagecontentsubheader"><h3>Pliki</h3></div>
                        <div class="contenttop"></div>
                        <div class="contentmiddle">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Nazwa</th>
                                    <th class="lastcol">Rozmiar</th>
                                </tr>
                                <c:forEach var="file" items="${ticket.addFilesList}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${file.originalFileName}"/></td>
                                        <td class="lastcol"><c:out value="${file.humanReadableFileSize}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="contentbottom"></div>
                    </c:if>
                    <div id="pagecontentsubheader"><h3>Ostatnie zdarzenia</h3></div>
                    <div class="chartcontainer">
                        <div class="chartbox">
                            <div class="TabView" id="currentWeekTabView">
                                <div class="Tabs">
                                    <a><span>Lista komentarzy</span></a>
                                    <a><span>Historia zgłoszenia</span></a>
                                </div>
                                <div class="contenttop"></div>
                                <div class="Pages">
                                    <div class="Page">
                                        <table width="100%" cellspacing="12" cellpadding="4">
                                            <tr>
                                                <td>
                                                    <c:if test="${not empty ticket.comments}">
                                                    <table cellspacing="0" class="standardtable" style="margin-bottom: 10px;">
                                                        <tr>
                                                            <th>Autor</th>
                                                            <th>Data</th>
                                                            <th class="lastcol">Treść</th>
                                                        </tr>
                                                        <c:forEach var="comment" items="${ticket.comments}" varStatus="status">
                                                        <tr>
                                                            <td class="tit"><c:out value="${comment.commentAuthor}"/></td>
                                                            <td><fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                                                            <td class="bod"><c:out value="${comment.commentText}" escapeXml="false"/></td>
                                                        </tr>
                                                        </c:forEach>
                                                    </table>
                                                    </c:if>
                                                    <form action="<c:url value="/ticketDetails.html?ticketId=${param.ticketId}"/>" method="POST">
                                                        <textarea id="addComm" name="addComm" rows="3" cols="40" class="mceEditor addcomment" style="height: 120px;"></textarea>
                                                        <br/>
                                                        <input type="checkbox" name="notForPlainUser" value="true"/> - tylko dla pracowników helpdesku
                                                        <br/><br/>
                                                        <input type="submit" value="dodaj komentarz" class="btn" />
                                                    </form>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="Page">
                                        <table width="100%" cellspacing="12" cellpadding="4">
                                            <tr>
                                                <td>
                                                    <table cellspacing="0" class="standardtable">
                                                        <tr>
                                                            <th>Lp.</th>
                                                            <th>Zdarzenie</th>
                                                            <th>Autor</th>
                                                            <th class="lastcol">Data</th>
                                                        </tr>
                                                        <c:forEach var="event" items="${ticket.events}" varStatus="status">
                                                            <tr>
                                                                <td class="scount"><c:out value="${status.count}"/></td>
                                                                <td><c:out value="${event.evtSubject}"/></td>
                                                                <td><c:out value="${event.evtAuthor}"/></td>
                                                                <td class="lastcol"><fmt:formatDate value="${event.evtDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        tabview_initialize('currentWeekTabView');
                    </script>
                </td>
                <td class="leftcells">
                    <div id="pagecontentsubheader"><h3>Wprowadził</h3></div>
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr>
                                <td class="tabtitle">Login:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.inputer.login}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Imie:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.inputer.firstName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Nazwisko: </td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.inputer.lastName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Tel:</td>
                                <td style="width: 110px;"><c:out value="${ticket.inputer.phone}"/></td>
                                <td class="tabtitle">Kom:</td>
                                <td class="lastcol" style="width: 110px;"><c:out value="${ticket.inputer.mobile}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Email:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.inputer.email}"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                    <div id="pagecontentsubheader"><h3>Zgłosił</h3></div>
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr>
                                <td class="tabtitle">Login:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.notifier.login}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Imie:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.notifier.firstName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Nazwisko: </td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.notifier.lastName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Tel:</td>
                                <td style="width: 110px;"><c:out value="${ticket.notifier.phone}"/></td>
                                <td class="tabtitle">Kom:</td>
                                <td class="lastcol" style="width: 110px;"><c:out value="${ticket.notifier.mobile}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Email:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.notifier.email}"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                    <div id="pagecontentsubheader"><h3>Rozwiązuje</h3></div>
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr>
                                <td class="tabtitle">Login:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.saviour.login}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Imie:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.saviour.firstName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Nazwisko: </td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.saviour.lastName}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Tel:</td>
                                <td style="width: 110px;"><c:out value="${ticket.saviour.phone}"/></td>
                                <td class="tabtitle">Kom:</td>
                                <td class="lastcol" style="width: 110px;"><c:out value="${ticket.saviour.mobile}"/></td>
                            </tr>
                            <tr>
                                <td class="tabtitle">Email:</td>
                                <td colspan="3" class="lastcol"><c:out value="${ticket.saviour.email}"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                </td>
            </tr>
        </table>
    </div>
</div>
