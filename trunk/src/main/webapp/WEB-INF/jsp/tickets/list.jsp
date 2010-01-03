<%@page pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<div id="alltickets" class="ticketslist">
    <div id="pagecontentheader"><h2>Zgłoszenia</h2></div>
    <div id="pagecontentsubheader"><h3>Lista wszystkich zgłoszeń</h3></div>
    <div id="content">
        <div class="contenttop"></div>
        <div class="contentmiddle">
            <display:table
                name="tickets" id="ticketsIterator" export="false" pagesize="25" requestURI="" cellspacing="0"
                sort="external" partialList="true" size="ticketsListSize">
                <display:column title="Lp." class="rowNumber" headerClass="rowNumber">
                    <c:out value="${ticketsIterator_rowNum}"/>
                </display:column>
                <display:column title="Przyczyna zgłoszenia" class="ticketsDetail" headerClass="ticketsDetail">
                    <a href="<c:url value="ticketDetails.html"/>?ticketId=<c:out value="${ticketsIterator.ticketId}"/>" onmouseover="jGetXY( event )" onmouseout="jHideDesc( event, 'row_<c:out value="${ticketsIterator.ticketId}"/>' );" title="">
                        <c:out value="${ticketsIterator.subject}"/>
                    </a>
                    <div id="row_<c:out value="${ticketsIterator.ticketId}"/>" class="linker">
                        <c:out value="${ticketsIterator.description}"/>
                    </div>
                </display:column>
                <display:column property="ticketCategory" title="Kategoria" class="category" headerClass="category" />
                <display:column title="Data" class="createDate" headerClass="createDate">
                    <fmt:formatDate value="${ticketsIterator.createDate}" pattern="yy/MM/dd HH:mm" />
                </display:column>
                <display:column title="Status" class="status" headerClass="status" sortName="b_status" sortable="true">
                    <span style="font-weight: bold; color: #<c:out value="${ticketsIterator.ticketStatus.bgColor}"/>">
                        <c:out value="${ticketsIterator.ticketStatus}" />
                    </span>
                </display:column>
                <display:column property="ticketPriority" title="Ważność" class="priority" headerClass="priority" />
                <display:column property="notifier" title="Zgłaszający" class="notifier" headerClass="notifier" />
                <display:column title="" media="html" class="ticketView" headerClass="ticketView">
                    <a href="<c:url value="ticketDetails.html?ticketId=${ticketsIterator.ticketId}"/>">View</a>
                </display:column>

                <display:setProperty name="paging.banner.no_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">No {0} found.</td>"/>
                <display:setProperty name="paging.banner.one_item_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">One {0} found.</td>"/>
                <display:setProperty name="paging.banner.all_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">{0} {1} found, displaying all {2}.</td>"/>
                <display:setProperty name="paging.banner.some_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">Rekordy od {2} do {3} z {0}.</td>"/>
                <display:setProperty name="paging.banner.full" value="<td id=\"paginationlinks\"><a href=\"{1}\">&laquo;</a> <a href=\"{2}\">&lsaquo;</a> {0} <a href=\"{3}\">&rsaquo;</a> <a href=\"{4}\">&raquo;</a></td></tr></table>" />
                <display:setProperty name="paging.banner.first" value="<td id=\"paginationlinks\"><span>&laquo;</span> <span>&lsaquo;</span> {0} <a href=\"{3}\">&rsaquo;</a> <a href=\"{4}\">&raquo;</a></td></tr></table>" />
                <display:setProperty name="paging.banner.last" value=" <td id=\"paginationlinks\"><a href=\"{1}\">&laquo;</a> <a href=\"{2}\">&lsaquo;</a> {0} <span>&rsaquo;</span> <span>&raquo;</span></a></td></tr></table>" />
                <display:setProperty name="paging.banner.onepage" value="<td id=\"paginationlinks\">{0}</td></tr></table>" />

                <display:setProperty name="paging.banner.page.separator" value=" &nbsp;" />
                <display:setProperty name="export.banner" value="<span class=\"exportlinks\"><br>Eksportuj jako: {0}</span>" />
                <display:setProperty name="export.pdf" value="false" />
                <display:setProperty name="paging.banner.placement" value="top" />
                <display:setProperty name="basic.msg.empty_list" value="Nie znaleziono zgłoszeń do wyświetlenia." />
            </display:table>
        </div>
        <div class="contentbottom"></div>
    </div>
    <a id="filterbutton" class="btn" href="javascript:blank()" onclick="showForm();">Filtr</a>
</div>

<div id="hintlt"></div>
<div id="hintlb"></div>
<div id="hint">
    <div id="hinttop"></div>
    <div id="hintmiddle">
        
    </div>
    <div id="hintbottom"></div>
</div>