<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showallcategories" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Kategorie zgłoszeń</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">


<table cellspacing="0">
	<tr>
		<td>
			<a class="btn" href="<c:url value="/manage/category/new.html"/>">Dodaj kategorię główną</a>
		</td>
	</tr>
</table>
<c:if test="${not empty categories}">
<br />
<table cellspacing="0" class="standardtable">
	<tr>
		<th>Nazwa kategorii</th>
		<th>Aktywna</th>
		<th colspan="3" class="lastcol">&nbsp;</th>
	</tr>
	<c:forEach var="category" items="${categories}">
	<tr>
		<td style="padding-left: <c:out value="${category.depth}"/>0px"><span style="padding-left: 10px"><c:out value="${category.categoryName}"/></span></td>
		<td><c:out value="${category.active}"/></td>
		<td class="ticketEdit"><a href="<c:url value="/manage/category/edit.html?catId=${category.ticketCategoryId}"/>">Edit</a></td>
		<td class="ticketView"><a href="<c:url value="/manage/category/new.html?parentId=${category.ticketCategoryId}"/>">Add</a></td>
		<td class="lastcol ticketDrop"><a href="<c:url value="/manage/category/remove.html?catId=${category.ticketCategoryId}"/>">Drop</a></td>
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
