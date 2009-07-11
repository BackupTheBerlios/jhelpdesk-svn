<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showallsections" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Baza wiedzy - sekcje</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<c:if test="${not empty sections}">

<table cellspacing="0">
	<tr>
		<td>
			<a class="btn" href="<c:url value="/manage/knowledge/section/new.html"/>">Dodaj nową sekcję</a>
		</td>
	</tr>
</table>
<br />
<table cellspacing="0" class="standardtable">
	<tr>
		<th class="rowNumber">Lp.</th>
		<th>Nazwa sekcji</th>
		<th class="artnumber">Ilość art.</th>
		<th colspan="5" class="lastcol">Akcje</th>
	</tr>
	<c:forEach var="section" items="${sections}">
	<tr>
		<td class="rowNumber">.</td>
		<td><c:out value="${section.sectionName}"/></td>
		<td class="artnumber"><c:out value="${section.articlesCount}"/></td>
		<td class="bugEdit">
			<a href="<c:url value="/manage/knowledge/article/showAll.html?sectionId=${section.hdKnowledgeSectionId}"/>">Art</a>
		</td>
		<td class="bugEdit">
			<a href="<c:url value="/manage/knowledge/section/edit.html?sectionId=${section.hdKnowledgeSectionId}"/>">Edit</a>
		</td>
		<td class="bugEdit">
			<a href="<c:url value="/manage/knowledge/section/remove.html?sectionId=${section.hdKnowledgeSectionId}"/>">Del</a>
		</td>
		<td class="bugEdit">
			<a href="<c:url value="/manage/knowledge/section/up.html?sectionId=${section.hdKnowledgeSectionId}"/>">Up</a>
		</td>
		<td class="lastcol bugEdit">
			<a href="<c:url value="/manage/knowledge/section/down.html?sectionId=${section.hdKnowledgeSectionId}"/>">Down</a>
		</td>
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
