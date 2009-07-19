<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="editcategory" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="leftcells">

<div id="pagecontentsubheader"><h3>Edycja/dodanie kategorii</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<form action="" method="post">
<table cellspacing="0" class="standardtable">
	<c:if test="${category.ticketCategoryId != null}">
		<spring:bind path="category.ticketCategoryId">
		<input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
		</spring:bind>
	</c:if>
	<c:if test="${not empty param.parentId}">
		<spring:bind path="category.parentCategory">
		<input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${param.parentId}"/>"/>
		</spring:bind>
	</c:if>
	<tr>
		<td>Nazwa</td>
		<td class="lastcol">
			<spring:bind path="category.categoryName">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" 
				<c:if test="${not empty status.errorMessage}">class="hintanchor" 
				onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
				</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Opis</td>
		<td class="lastcol">
			<spring:bind path="category.categoryDesc">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
				<c:if test="${not empty status.errorMessage}">style="background: yellow"</c:if>/>
			</spring:bind>
		</td>
	</tr>
</table>
<br />
<table cellspacing="0">
	<tr>
		<td colspan="2">
			<input type="submit" value="zapisz" class="btn" />
		</td>
	</tr>
</table>
</form>

</div>
<div class="contentbottom"></div>
</div>

</td>
</tr>
</table>

</div>
