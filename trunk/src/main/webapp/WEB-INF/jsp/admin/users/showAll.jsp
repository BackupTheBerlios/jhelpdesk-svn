<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showallusers" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Użytkownicy</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<display:table name="users" id="user" class="standardtable" cellspacing="0" export="false" pagesize="25" requestURI="">

<display:column title="Lp." class="rowNumber" headerClass="rowNumber">
	<c:out value="${user_rowNum}" />
</display:column>
<display:column title="Nazwisko" property="lastName" style="width: 200px;" />
<display:column title="Imię" property="firstName" />
<display:column title="" media="html" class="bugView" headerClass="bugView">
	<a href="<c:url value="show.html?userId=${user.userId}"/>">View</a>
</display:column>
<display:column title="" media="html" class="bugEdit" headerClass="bugEdit">
	<a href="<c:url value="edit.html?userId=${user.userId}"/>">Edit</a>
</display:column>
<display:column title="" media="html" class="lastcol bugDrop" headerClass="lastcol bugDrop">
	<a href="<c:url value="remove.html?userId=${user.userId}"/>">Drop</a>
</display:column>

<display:setProperty name="paging.banner.no_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">No {0} found.</td>" />
<display:setProperty name="paging.banner.one_item_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">One {0} found.</td>" />
<display:setProperty name="paging.banner.all_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">{0} {1} found, displaying all {2}.</td>" />
<display:setProperty name="paging.banner.some_items_found" value="<table id=\"pagination\"><tr><td id=\"paginationinfo\">Rekordy od {2} do {3} z {0}.</td>" />

<display:setProperty name="paging.banner.full" value="<td id=\"paginationlinks\"><a href=\"{1}\">&laquo;</a> <a href=\"{2}\">&lsaquo;</a> {0} <a href=\"{3}\">&rsaquo;</a> <a href=\"{4}\">&raquo;</a></td></tr></table>" />
<display:setProperty name="paging.banner.first" value="<td id=\"paginationlinks\"><span>&laquo;</span> <span>&lsaquo;</span> {0} <a href=\"{3}\">&rsaquo;</a> <a href=\"{4}\">&raquo;</a></td></tr></table>" />
<display:setProperty name="paging.banner.last" value=" <td id=\"paginationlinks\"><a href=\"{1}\">&laquo;</a> <a href=\"{2}\">&lsaquo;</a> {0} <span>&rsaquo;</span> <span>&raquo;</span></a></td></tr></table>" />
<display:setProperty name="paging.banner.onepage" value="<td id=\"paginationlinks\">{0}</td></tr></table>" />

<display:setProperty name="export.banner" value="<div class=\"exportlinks\"><br>Eksportuj jako: {0}</div>"/>
<display:setProperty name="export.pdf" value="false" />
<display:setProperty name="paging.banner.placement" value="top"/>
<display:setProperty name="basic.msg.empty_list" value="Nie znaleziono żadnego użytkownika."/>

</display:table>

</div>
<div class="contentbottom"></div>
</div>

</td>
</tr>
</table>

</div>
