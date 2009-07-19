<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<form name="ticketform" action="<c:url value="/editTicket.html"/>" method="POST" class="login">
<table width="100% border="1">
<tr>
	<td width="50%">
	<div id="aboutnotyfier">
		<div id="forminfoaboutnotyfierlink">
			<a href="javascript:blank()" onclick="showDiv('forminfoaboutnotyfierlink');" >Zgłaszający</a>
		</div>
		<div id="forminfoaboutnotyfier">
			<c:if test="${hdticket.ticketId != null}">
			<spring:bind path="hdticket.ticketId">
				<input type="hidden" name="ticketId" value="<c:out value="${hdticket.ticketId}"/>"/>
			</spring:bind>
			</c:if>
			<table border="0" width="100%" align="center">
			<tr>
				<td class="tabtitle">Wyszukaj:</td>
				<td>
					<spring:bind path="hdticket.notifier">
					<input type="text" name="notifier" value="<c:out value="${hdticket.notifier.login}"/>" style="width:150px"<c:if test="${param.ticketId}">readonly="true"</c:if>/>
					</spring:bind>
				</td>
				<td colspan="2" class="tabtitle">
					<input type="image" name="checkLogin" value="ok" style="background: #ffffff;" src="<c:url value="/i/find.gif"/>" border="0"/>
				</td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdticket.notifier.firstName}"/>"/></td>
				<td class="tabtitle">Nazwisko: </td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdticket.notifier.lastName}"/>"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td><input type="text" value="<c:out value="${hdticket.notifier.phone}"/>" style="width:150px" readonly="true"/></td>
				<td class="tabtitle">Kom:</td>
				<td>
					<spring:bind path="hdticket.addPhone">
					<input type="text" name="addPhone" value="<c:out value="${status.value}"/>"  style="width:150px" <c:if test="${param.ticketId}">readonly="true"</c:if>/>
					<c:if test="${not empty status.errorMessage}">
					<span style="color: red"><c:out value="${status.errorMessage}"/></span>
					</c:if>
					</spring:bind>
				</td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3"><input type="text" value="<c:out value="${hdticket.notifier.email}"/>" style="width:190px" readonly="true"/></td>
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
					<spring:bind path="hdticket.inputer">
					<input type="text" readonly="true" name="inputer" style="width: 150px" value="<c:out value="${hdticket.inputer.login}"/>"/>
					</spring:bind>
				</td>
				<td class="tabtitle"></td>
				<td></td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdticket.inputer.firstName}"/>"/></td>
				<td class="tabtitle">Nazwisko: </td>
				<td style="width:170px"><input type="text" style="width:150px" readonly="true" value="<c:out value="${hdticket.inputer.lastName}"/>"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td><input type="text" value="<c:out value="${hdticket.inputer.phone}"/>" style="width:150px" readonly="true"/></td>
				<td class="tabtitle">Kom:</td>
				<td>..</td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3"><input type="text" value="<c:out value="${hdticket.inputer.email}"/>" style="width:190px" readonly="true"/></td>
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
						<spring:bind path="hdticket.subject">
							<textarea name="subject" rows="3" <c:if test="${not empty param.ticketId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
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
						<spring:bind path="hdticket.description">
							<textarea name="description" rows="8" <c:if test="${empty param.ticketId}">class="mceEditor"</c:if> <c:if test="${not empty param.ticketId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
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
						<spring:bind path="hdticket.stepByStep">
							<textarea name="stepByStep" rows="13" <c:if test="${empty param.ticketId}">class="mceEditor"</c:if> <c:if test="${not empty param.ticketId}">readonly="true"</c:if>/><c:out value="${status.value}"/></textarea>
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
							<spring:bind path="hdticket.ticketCategory">
							<select name="ticketCategoryId" style="width: 200px">
							<c:if test="${hdticket.ticketId == null}">
							<option value=""> -- wybierz kategorię -- </option>
							</c:if>
							<c:forEach var="ticketCategory" items="${categories}">
								<c:if test="${hdticket.ticketCategory.ticketCategoryId == ticketCategory.ticketCategoryId}">
									<c:if test="${empty ticketCategory.parentCategory}">
					          			<option selected="<c:out value="${hdticket.ticketCategory.ticketCategoryId}"/>" value="<c:out value="${ticketCategory.ticketCategoryId}"/>" disabled="true"><c:out value="${ticketCategory.categoryName}"/></option>
									</c:if>
									<c:if test="${not empty ticketCategory.parentCategory}">
										<option selected="<c:out value="${hdticket.ticketCategory.ticketCategoryId}"/>" value="<c:out value="${ticketCategory.ticketCategoryId}"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${ticketCategory.categoryName}"/></option>
									</c:if>
					        	</c:if>
					        	<c:if test="${hdticket.ticketCategory.ticketCategoryId != ticketCategory.ticketCategoryId}">
									<c:if test="${empty ticketCategory.parentCategory}">
						          		<option value="<c:out value="${ticketCategory.ticketCategoryId}"/>" disabled="true"><c:out value="${ticketCategory.categoryName}"/></option>
									</c:if>
									<c:if test="${not empty ticketCategory.parentCategory}">
						          		<option value="<c:out value="${ticketCategory.ticketCategoryId}"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${ticketCategory.categoryName}"/></option>
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
							<spring:bind path="hdticket.ticketPriority">
							<select id="ticketPriorityIdSelect" name="ticketPriorityId" style="width: 200px">
							<c:forEach var="ticketPriority" items="${priorities}">
								<c:if test="${hdticket.ticketPriority.priorityId == ticketPriority.priorityId}">
					          		<option selected="<c:out value="${hdticket.ticketPriority.priorityId}"/>" value="<c:out value="${ticketPriority.priorityId}"/>"><c:out value="${ticketPriority.priorityName}"/></option>
					        	</c:if>
					        	<c:if test="${hdticket.ticketPriority.priorityId != ticketPriority.priorityId}">
					          		<option value="<c:out value="${ticketPriority.priorityId}"/>"><c:out value="${ticketPriority.priorityName}"/></option>
					        	</c:if>
							</c:forEach> 
							</select>
							<c:if test="${not empty status.errorMessage}">
								<span style="color: red"><c:out value="${status.errorMessage}"/></span>
							</c:if>
							</spring:bind>
						</td>
					</tr>
					<c:if test="${hdticket.ticketId != null}">
					<tr>
						<td class="tabtitle">Status:</td>
						<td>
							<spring:bind path="hdticket.ticketStatus">
							<select name="ticketStatusId" style="width: 200px">
							<c:forEach var="ticketStatus" items="${statuses}">
								<c:if test="${hdticket.ticketStatus.statusId == ticketStatus.statusId}">
									<option selected="<c:out value="${hdticket.ticketStatus.statusId}"/>" value="<c:out value="${ticketStatus.statusId}"/>"><c:out value="${ticketStatus.statusName}"/></option>
						       	</c:if>
								<c:if test="${hdticket.ticketStatus.statusId != ticketStatus.statusId}">
									<option value="<c:out value="${ticketStatus.statusId}"/>"><c:out value="${ticketStatus.statusName}"/></option>
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
					<c:if test="${hdticket.ticketId != null}">
					<tr>
						<td class="tabtitle">Rozwiązujący:</td>
						<td align="right">
							<spring:bind path="hdticket.saviour">
							<select name="saviourId" style="width: 200px">
							<c:forEach var="user" items="${saviours}">
								<c:if test="${hdticket.saviour.userId == user.userId}">
	          						<option selected="<c:out value="${hdticket.saviour.userId}"/>" value="<c:out value="${user.userId}"/>"><c:out value="${user.fullName}"/></option>
								</c:if>
								<c:if test="${hdticket.saviour.userId != user.userId}">
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
		    <c:if test="${not empty param.ticketId}">
    			<input type="submit" name="delete" value="Usuń"/>
		    </c:if>
			<input type="submit" name="save" value="Zapisz"/>
		</div>
	</td>
</tr>
</table>
</form>