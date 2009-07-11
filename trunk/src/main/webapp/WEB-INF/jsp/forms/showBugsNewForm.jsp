<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<util:rewrite paramGetName="priorities" paramReturnName="_priorities"/>
<util:rewrite paramGetName="statuses"   paramReturnName="_statuses"/>
<util:rewrite paramGetName="categories" paramReturnName="_categories"/>
<util:rewrite paramGetName="notifyiers" paramReturnName="_notifyiers"/>

<script language="javascript">
function clearForm() {
	for( var i = 0; i < document.forms[0].notifyiers.length; i++ ) {
		document.forms[0].notifyiers.options[i].selected = false;
	}

	for( var i = 0; i < document.forms[0].priorities.length; i++ ) {
		document.forms[0].priorities.options[i].selected = false;
	}
	for( var i = 0; i < document.forms[0].categories.length; i++ ) {
		document.forms[0].categories.options[i].selected = false;
	}
	document.forms[0].startDate.value = "";
	document.forms[0].endDate.value = "";
}
</script>



<form action="" method="post">
	<div id="boxShadow" class="shadow">
		<div id="box">
			<div class="links">
				<a href="javascript:sweeptoggle('contract')">Ukryj wszystko</a> | <a href="javascript:sweeptoggle('expand')">Pokaż wszystko</a><a id="x" href="javascript:blank()" onclick="showForm();" style="float: right;"><img src="<c:url value="/themes/hd/i/hd-x.png"/>" /></a>
			</div>
			<div class="panelsBg">
			<div id="panels">
			<div id="boxleft">
				<!--Optional Expand/ Contact All links. Remove if desired-->
				<div class="headers">
					<img src="minus.gif" class="showstate" onClick="expandcontent(this, 'sc1')" />Data wprowadzenia
				</div>
				<div id="sc1" class="switchcontent">
					<table>
					<tr>
					<td class="t1"><label for="f_date_start">Od:</label></td>
					<td class="t2">
						<spring:bind path="filterForm.startDate">
							<input id="f_date_start" readonly="1" type="text" name="startDate" value="<c:out value="${status.value}"/>" />
						</spring:bind>
					</td>
					<td>
						<img 
							src="<c:url value="/i/cal.gif"/>" id="f_trigger_c"
							style="cursor: pointer; border: 0px;"
							title="Date selector"/>
						<script type="text/javascript">
							Calendar.setup({ 
								inputField : "f_date_start", ifFormat : "%Y-%m-%d",	button : "f_trigger_c", align : "BR", singleClick : true
							});
						</script>
					</td>
					</tr>
					</table>
					<table>
					<tr>
					<td class="t1"><label for="f_date_end">Do:</label></td>
					<td class="t2">
						<spring:bind path="filterForm.endDate">
							<input id="f_date_end" readonly="1" type="text" name="endDate" value="<c:out value="${status.value}"/>" />
						</spring:bind>
					</td>
					<td>
						<img 
							src="<c:url value="/i/cal.gif"/>" id="f_trigger_d"
							style="cursor: pointer; border: 0px;"
							title="Date selector"/>
						<script type="text/javascript">
							Calendar.setup({
								inputField : "f_date_end", ifFormat : "%Y-%m-%d", button : "f_trigger_d", align : "BR", singleClick : true
							});
						</script>
					</td>
					</tr>
					</table>
				</div>
				<div class="headers">
					<img src="minus.gif" class="showstate" onClick="expandcontent(this, 'sc2')" />Ważność
				</div>
				<div id="sc2" class="switchcontent">
					<spring:bind path="filterForm.priorities">
					<select name="<c:out value="${status.expression}"/>" size="4" multiple>
					<c:forEach var="priority" items="${priorities}">
						<option 
							value="<c:out value="${priority.priorityId}"/>"
							<c:forEach items="${_priorities}" var="itemPriority">
								<c:if test="${priority.priorityId == itemPriority}">selected</c:if>
							</c:forEach>
						><c:out value="${priority.priorityName}"/>
						</option>
					</c:forEach>
					</select>
					</spring:bind>
				</div>
			</div>
			<div id="boxright">
				<div class="headers">
					<img src="minus.gif" class="showstate" onClick="expandcontent(this, 'sc3')" />Kategoria
				</div>
				<div id="sc3" class="switchcontent">
				<spring:bind path="filterForm.categories">
				<select name="<c:out value="${status.expression}"/>" size="4" multiple>
					<c:forEach var="category" items="${categories}">
						<option 
							value="<c:out value="${category.bugCategoryId}"/>"
							<c:forEach items="${_categories}" var="itemCategory">
								<c:if test="${category.bugCategoryId == itemCategory}">selected</c:if>
							</c:forEach>
						><c:out value="${category.categoryName}"/>
						</option>
					</c:forEach>
				</select>
				</spring:bind>
				</div>
				<div class="headers">
					<img src="minus.gif" class="showstate" onClick="expandcontent(this, 'sc4')" />Zgłaszający
				</div>
				<div id="sc4" class="switchcontent">
				<spring:bind path="filterForm.notifyiers">
				<select name="notifyiers" size="4" multiple>
					<c:forEach var="user" items="${users}">
						<option 
							value="<c:out value="${user.userId}"/>"
							<c:forEach items="${_notifyiers}" var="itemNotifyier">
								<c:if test="${user.userId == itemNotifyier}">selected</c:if>
							</c:forEach>
						><c:out value="${user.fullName}"/>
						</option>
					</c:forEach>
				</select>
				</spring:bind>
				</div>
			</div>
			</div>
			</div>
			<div class="buttons">
				<input type="button" onClick="clearForm()" value="Wyczyść wszystko" />
				<input type="submit" value="Zastosuj filtr"/>
			</div>
		</div>
	</div>

<%--
	<table border="0" cellspacing="2" cellpadding="2" height="40px">
		<tr height="50">
			<td valign="top">
				<fieldset>
				<legend>Data wprowadzenia</legend>
				<table border="0" cellpadding="2" cellspacing="2" width="100%">
					<tr>
						<td width="20">Od:</td>
						<td align="left">
						<spring:bind path="filterForm.startDate">
							<input id="f_date_start" readonly="1" type="text" name="startDate" 
								value="<c:out value="${status.value}"/>"
								style="width: 70px; height: 15px; font-size: 11px; text-align: center; padding-top: 4px; background: url(/helpdesk/i/input_white.gif); border: 1px solid black;"  />
						</spring:bind>
						</td>
						<td>
						<img 
							src="<c:url value="/i/cal.gif"/>" id="f_trigger_c"
							style="cursor: pointer; border: 0px;"
							title="Date selector"/>
						<script type="text/javascript">
							Calendar.setup({ 
								inputField : "f_date_start", ifFormat : "%Y-%m-%d",	button : "f_trigger_c", align : "BR", singleClick : true
							});
						</script>
						</td>
					</tr>
					<tr>
						<td>Do:</td>
						<td align="left">
						<spring:bind path="filterForm.endDate">
							<input id="f_date_end" readonly="1" type="text" name="endDate" 
								value="<c:out value="${status.value}"/>"
								style="width: 70px; height: 15px; font-size: 11px; text-align: center;  padding-top: 4px; background: url(/helpdesk/i/input_white.gif); border: 1px solid black;"  />
						</spring:bind>
						</td>
						<td>
							<img 
							src="<c:url value="/i/cal.gif"/>" id="f_trigger_d"
							style="cursor: pointer; border: 0px;"
							title="Date selector"/>
						<script type="text/javascript">
							Calendar.setup({
								inputField : "f_date_end", ifFormat : "%Y-%m-%d", button : "f_trigger_d", align : "BR", singleClick : true
							});
						</script>
					</tr>
				</table>
				</fieldset>
			</td>

			<td valign="top">
				<fieldset>
				<legend>Ważność</legend>
				<spring:bind path="filterForm.priorities">
				<select name="<c:out value="${status.expression}"/>" size="4" multiple style="width: 110px; font-size: 11px;">
					<c:forEach var="priority" items="${priorities}">
						<option 
							value="<c:out value="${priority.priorityId}"/>"
							<c:forEach items="${_priorities}" var="itemPriority">
								<c:if test="${priority.priorityId == itemPriority}">selected</c:if>
							</c:forEach>
						><c:out value="${priority.priorityName}"/>
						</option>
					</c:forEach>
				</select>
				</spring:bind>
				</fieldset>
			</td>
			<td valign="top">
				<fieldset>
				<legend>Kategoria</legend>
				<spring:bind path="filterForm.categories">
				<select name="<c:out value="${status.expression}"/>" size="4" multiple style="width: 150px; font-size: 11px;">
					<c:forEach var="category" items="${categories}">
						<option 
							value="<c:out value="${category.bugCategoryId}"/>"
							<c:forEach items="${_categories}" var="itemCategory">
								<c:if test="${category.bugCategoryId == itemCategory}">selected</c:if>
							</c:forEach>
						><c:out value="${category.categoryName}"/>
						</option>
					</c:forEach>
				</select>
				</spring:bind>
				</fieldset>
			</td>
			<td valign="top">
				<fieldset>
				<legend>Zgłaszający</legend>
				<spring:bind path="filterForm.notifyiers">
				<select name="notifyiers" size="4" multiple style="width: 160px; font-size: 11px;">
					<c:forEach var="user" items="${users}">
						<option 
							value="<c:out value="${user.userId}"/>"
							<c:forEach items="${_notifyiers}" var="itemNotifyier">
								<c:if test="${user.userId == itemNotifyier}">selected</c:if>
							</c:forEach>
						><c:out value="${user.fullName}"/>
						</option>
					</c:forEach>
				</select>
				</spring:bind>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				<input type="button" onClick="clearForm()" value="Wyczyść wszystko"/>
				<input type="submit" value="Zastosuj filtr"/>
			</td>
		</tr>
	</table>

--%>
</form>
