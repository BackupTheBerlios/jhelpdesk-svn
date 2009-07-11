<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<form name="bugform" action="<c:url value="/editBug.html"/>" method="POST" class="login">
<table width="100% border="1">
<tr>
	<td width="50%">
	<div id="aboutnotyfier">
		<div id="forminfoaboutnotyfierlink">
			<a href="javascript:blank()" onclick="showDiv('forminfoaboutnotyfierlink');" >Zgłaszający</a>
		</div>
		<div id="forminfoaboutnotyfier">
			<c:if test="${hdbug.bugId != null}">
			<spring:bind path="hdbug.bugId">
				<input type="hidden" name="bugId" value="<c:out value="${hdbug.bugId}"/>"/>
			</spring:bind>
			</c:if>
			<table border="0" width="100%" align="center">
			<tr>
				<td class="tabtitle">Wyszukaj:</td>
				<td>
					<spring:bind path="hdbug.notifier">
					<input type="text" name="notifier" value="<c:out value="${hdbug.notifier.login}"/>" style="width:150px"<c:if test="${param.bugId}">readonly="true"</c:if>/>
					</spring:bind>
				</td>
				<td colspan="2" class="tabtitle">
					<input type="image" name="checkLogin" value="ok" style="background: #ffffff;" src="<c:url value="/i/find.gif"/>" border="0"/>
				</td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdbug.notifier.firstName}"/>"/></td>
				<td class="tabtitle">Nazwisko: </td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdbug.notifier.lastName}"/>"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td><input type="text" value="<c:out value="${hdbug.notifier.phone}"/>" style="width:150px" readonly="true"/></td>
				<td class="tabtitle">Kom:</td>
				<td>
					<spring:bind path="hdbug.addPhone">
					<input type="text" name="addPhone" value="<c:out value="${status.value}"/>"  style="width:150px" <c:if test="${param.bugId}">readonly="true"</c:if>/>
					<c:if test="${not empty status.errorMessage}">
					<span style="color: red"><c:out value="${status.errorMessage}"/></span>
					</c:if>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3"><input type="text" value="<c:out value="${hdbug.notifier.email}"/>" style="width:190px" readonly="true"/></td>
			</tr>
			</table>
		</div>
	</div><!-- aboutnotyfier -->
	</td>
	<td>
	<div id="aboutinputer">
		<div id="forminfoaboutinputerlink">
			<a href="javascript:blank()" onclick="showDiv('forminfoaboutinputerlink');" >Wprowadzający</a>
		</div>
		<div id="forminfoaboutinputer">
			<table width="100%" align="center">
			<tr>
				<td class="tabtitle">Wprowadził:</td>
				<td>
					<spring:bind path="hdbug.inputer">
					<input type="text" readonly="true" name="inputer" style="width: 150px" value="<c:out value="${hdbug.inputer.login}"/>"/>
					</spring:bind>
				</td>
				<td class="tabtitle"></td>
				<td></td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdbug.inputer.firstName}"/>"/></td>
				<td class="tabtitle">Nazwisko: </td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdbug.inputer.lastName}"/>"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td><input type="text" value="<c:out value="${hdbug.inputer.phone}"/>" style="width:150px" readonly="true"/></td>
				<td class="tabtitle">Kom:</td>
				<td>..</td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3"><input type="text" value="<c:out value="${hdbug.inputer.email}"/>" style="width:190px" readonly="true"/></td>
			</tr>
			</table>
		</div>
	</div><!-- aboutinputer -->
	</td>
</tr>
</table>

<table width="100%">
<tr>
	<td>
		<div id="aboutproblem">
			<div id="forminfoaboutproblemlink">
				<a href="javascript:blank()" onclick="showDiv('forminfoaboutproblemlink');" >Opis problemu</a>
			</div>
			<div id="forminfoaboutproblem">
				<table width="100%" align="center">
				<tr>
					<td colspan="4" class="tabtitle">Przyczyna zgłoszenia(*) - max. 255 znaków:</td>
				</tr>
				<tr>
					<td colspan="4">
						<spring:bind path="hdbug.subject">
							<textarea name="subject" rows="3" <c:if test="${not empty param.bugId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
							<c:if test="${not empty status.errorMessage}">
							<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="tabtitle">Opis zgłoszenia:(*)</td>
				</tr>
				<tr>
					<td colspan="4">
						<spring:bind path="hdbug.description">
							<textarea name="description" rows="8" <c:if test="${empty param.bugId}">class="mceEditor"</c:if> <c:if test="${not empty param.bugId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="tabtitle">Kroki by powtórzyć:</td>
				</tr>
				<tr>
					<td colspan="4">
						<spring:bind path="hdbug.stepByStep">
							<textarea name="stepByStep" rows="13" <c:if test="${empty param.bugId}">class="mceEditor"</c:if> <c:if test="${not empty param.bugId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
						</spring:bind>
					</td>
				</tr>
				</table>
			</div><!-- forminfoaboutproblem -->
		</div><!-- aboutproblem -->
	</td>
	<td valign="top" align="left">
		<div id="suwaki">
			<div id="formsuwakilink">
				<a href="javascript:blank()" onclick="showDiv('formsuwakilink');" >Wiecej...</a>
			</div>
			<div id="formsuwaki">
				<table>	
					<tr>
						<td class="tabtitle">Kategoria:</td>
						<td>
							<spring:bind path="hdbug.bugCategory">
							<select name="bugCategoryId" style="width: 200px">
							<c:if test="${hdbug.bugId == null}">
							<option value=""> -- wybierz kategorię -- </option>
							</c:if>
							<c:forEach var="bugCategory" items="${categories}">
								<c:if test="${hdbug.bugCategory.bugCategoryId == bugCategory.bugCategoryId}">
									<c:if test="${empty bugCategory.parentCategory}">
					          			<option selected="<c:out value="${hdbug.bugCategory.bugCategoryId}"/>" value="<c:out value="${bugCategory.bugCategoryId}"/>" disabled="true"><c:out value="${bugCategory.categoryName}"/></option>
									</c:if>
									<c:if test="${not empty bugCategory.parentCategory}">
										<option selected="<c:out value="${hdbug.bugCategory.bugCategoryId}"/>" value="<c:out value="${bugCategory.bugCategoryId}"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${bugCategory.categoryName}"/></option>
									</c:if>
					        	</c:if>
					        	<c:if test="${hdbug.bugCategory.bugCategoryId != bugCategory.bugCategoryId}">
									<c:if test="${empty bugCategory.parentCategory}">
						          		<option value="<c:out value="${bugCategory.bugCategoryId}"/>" disabled="true"><c:out value="${bugCategory.categoryName}"/></option>
									</c:if>
									<c:if test="${not empty bugCategory.parentCategory}">
						          		<option value="<c:out value="${bugCategory.bugCategoryId}"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${bugCategory.categoryName}"/></option>
									</c:if>
					        	</c:if>
							</c:forEach> 
							</select>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td class="tabtitle">Ważność:</td>
						<td align="right">
							<spring:bind path="hdbug.bugPriority">
							<select id="bugPriorityIdSelect" name="bugPriorityId" style="width: 200px">
							<c:forEach var="bugPriority" items="${priorities}">
								<c:if test="${hdbug.bugPriority.priorityId == bugPriority.priorityId}">
					          		<option selected="<c:out value="${hdbug.bugPriority.priorityId}"/>" value="<c:out value="${bugPriority.priorityId}"/>"><c:out value="${bugPriority.priorityName}"/></option>
					        	</c:if>
					        	<c:if test="${hdbug.bugPriority.priorityId != bugPriority.priorityId}">
					          		<option value="<c:out value="${bugPriority.priorityId}"/>"><c:out value="${bugPriority.priorityName}"/></option>
					        	</c:if>
							</c:forEach> 
							</select>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
							</spring:bind>
						</td>
					</tr>
					<c:if test="${hdbug.bugId != null}">
					<tr>
						<td class="tabtitle">Status:</td>
						<td>
							<spring:bind path="hdbug.bugStatus">
							<select name="bugStatusId" style="width: 200px">
							<c:forEach var="bugStatus" items="${statuses}">
								<c:if test="${hdbug.bugStatus.statusId == bugStatus.statusId}">
									<option selected="<c:out value="${hdbug.bugStatus.statusId}"/>" value="<c:out value="${bugStatus.statusId}"/>"><c:out value="${bugStatus.statusName}"/></option>
						       	</c:if>
								<c:if test="${hdbug.bugStatus.statusId != bugStatus.statusId}">
									<option value="<c:out value="${bugStatus.statusId}"/>"><c:out value="${bugStatus.statusName}"/></option>
								</c:if>
							</c:forEach> 
							</select>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
							</spring:bind>
						</td>
					</tr>
					</c:if>
					<c:if test="${hdbug.bugId != null}">
					<tr>
						<td class="tabtitle">Rozwiązujący:</td>
						<td align="right">
							<spring:bind path="hdbug.saviour">
							<select name="saviourId" style="width: 200px">
							<c:forEach var="user" items="${saviours}">
								<c:if test="${hdbug.saviour.userId == user.userId}">
	          						<option selected="<c:out value="${hdbug.saviour.userId}"/>" value="<c:out value="${user.userId}"/>"><c:out value="${user.fullName}"/></option>
								</c:if>
								<c:if test="${hdbug.saviour.userId != user.userId}">
									<option value="<c:out value="${user.userId}"/>"><c:out value="${user.fullName}"/></option>
								</c:if>
							</c:forEach> 
							</select>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
							</spring:bind>
						</td>
					</tr>
					</c:if>
				</table>
			</div><!-- formsuwaki -->			
		</div><!-- suwaki -->
		<br>
		
	</td>
</tr>
<tr>
	<td colspan="2">
		<div id="formbtns">
		    <c:if test="${not empty param.bugId}">
    			<input type="submit" name="delete" value="Usuń"/>
		    </c:if>
			<input type="submit" name="save" value="Zapisz"/>
		</div>
	</td>
</tr>
</table>
</form>