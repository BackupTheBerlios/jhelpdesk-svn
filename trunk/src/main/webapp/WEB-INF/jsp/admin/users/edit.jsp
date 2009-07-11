<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showuser" class="management">
<div id="pagecontentheader"><h2>Zarządzanie</h2></div>

<table cellspacing="0">
<tr>
<td class="leftcells">

<div id="pagecontentsubheader"><h3>Edycja danych użytkownika</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<form action="" method="post">
<table cellspacing="0" class="standardtable">
	<spring:bind path="user.*">
	<c:if test="${not empty status.errorMessages}">
	<tr>
		<td colspan="2" style="color: red" class="lastcol">
		<c:forEach var="err" items="${status.errorMessages}">
			<c:out value="${err}" escapeXml="false"/><br/>
		</c:forEach>
		</td>
	</tr>
	</c:if>
	</spring:bind>

	<c:if test="${user.userId != null}">
		<spring:bind path="user.userId">
		<input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
		</spring:bind>
	</c:if>
	<tr>
		<td>Imię</td>
		<td class="lastcol">
			<spring:bind path="user.firstName">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" 
				<c:if test="${not empty status.errorMessage}">class="hintanchor" 
				onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
				</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Nazwisko</td>
		<td class="lastcol">
			<spring:bind path="user.lastName">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
				<c:if test="${not empty status.errorMessage}">style="background: yellow"</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Login</td>
		<td class="lastcol">
			<spring:bind path="user.login">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
				<c:if test="${not empty status.errorMessage}">style="background: yellow"</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Hasło</td>
		<td class="lastcol">
			<spring:bind path="user.password">
			<input type="password" name="<c:out value="${status.expression}"/>" value=""
				<c:if test="${not empty status.errorMessage}">style="background: yellow"</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Email</td>
		<td class="lastcol">
			<spring:bind path="user.email">
			<input type="text" name="<c:out value="${status.expression}"/>" value=""
				<c:if test="${not empty status.errorMessage}">style="background: yellow"</c:if>/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Telefon</td>
		<td class="lastcol">
			<spring:bind path="user.phone">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Mobile</td>
		<td class="lastcol">
			<spring:bind path="user.mobile">
			<input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Rola</td>
		<td class="lastcol">
			<spring:bind path="user.userRole">
			<select name="<c:out value="${status.expression}"/>">
				<c:forEach var="role" items="${roles}">
					<c:if test="${user.userRole.roleCode == role.roleCode}">
						<option value="<c:out value="${role.roleCode}"/>" selected="true"><c:out value="${role.roleName}"/></option>
		        	</c:if>
		        	<c:if test="${user.userRole.roleCode != role.roleCode}">
						<option value="<c:out value="${role.roleCode}"/>"><c:out value="${role.roleName}"/></option>
		        	</c:if>
				</c:forEach>
			</select>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td>Aktywny</td>
		<td class="lastcol">
			<spring:bind path="user.active">
			<input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value == true}">checked="true"</c:if>/>
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