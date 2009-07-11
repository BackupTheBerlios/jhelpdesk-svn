<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="editinformation" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="leftcells">

<div id="pagecontentsubheader"><h3>Edycja wiadomości</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<form action="" method="post">
	<c:if test="${information.informationId != null}">
		<spring:bind path="information.informationId">
		<input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
		</spring:bind>
	</c:if>
<table cellspacing="0" class="standardtable">
	<tr>
		<td>Tytul</td>
		<td class="lastcol">
			<spring:bind path="information.title">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" 
				<c:if test="${not empty status.errorMessage}">class="hintanchor" 
				onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
				</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Skrót</td>
		<td class="lastcol">
			<spring:bind path="information.lead">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" 
				<c:if test="${not empty status.errorMessage}">class="hintanchor" 
				onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
				</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Treść główna</td>
		<td class="lastcol">
			<spring:bind path="information.body">
			<textarea 
				name="<c:out value="${status.expression}"/>" rows="4" cols="30"
				<c:if test="${not empty status.errorMessage}">class="hintanchor" 
				onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
				</c:if>><c:out value="${status.value}"/></textarea>
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
