<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="bugdetails">
<div id="pagecontentheader"><h2>Podgląd zgłoszenia</h2></div>

<div id="desktoppanels">
<table id="desktoppanelstable" cellspacing="0">

<tr class="desktoppanelstableheader">

<td class="rightcells lastBugs">
<div id="pagecontentsubheader"><h3>Opis problemu</h3></div>
		<div class="contenttop"></div>
		<div class="contentmiddle">
			<table cellspacing="0" class="standardtable"> 
				<tr>
					<th colspan="3" style="width: 258px;">Numer</th>
					<th colspan="3" class="lastcol">Data</th>
				</tr>
				<tr>
					<td colspan="3" style="width: 258px; font-weight: bold;"><c:out value="${bug.bugId}"/></td>
					<td colspan="3" class="lastcol" style="font-weight: bold;"><fmt:formatDate value="${bug.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th colspan="6" class="lastcol">Przyczyna</th>
				</tr>
				<tr>
					<td colspan="6" class="lastcol"><c:out value="${bug.subject}" /></td>
				</tr>
				<tr>
					<th colspan="2">Status</th>
					<th colspan="2">Kategoria</th>
					<th colspan="2" class="lastcol">Ważność</th>
				</tr>
				<tr>
					<td colspan="2" style="width: 170px;">
					    <select size="1">
					        <c:forEach var="status" items="${bugStatuses}">
                                <option value="${status.statusId}" <c:if test="${status == bug.bugStatus}">selected="selected"</c:if>>
                                    <c:out value="${status}" />
                                </option>
                            </c:forEach>
					    </select>
					</td>
					<td colspan="2" style="width: 170px;">
					    <select size="1">
				            <option value="0" <c:if test="${bug.bugCategory.bugCategoryId == 0}">selected="selected"</c:if>>
                                Brak
                            </option>
                            <c:forEach var="category" items="${bugCategories}">
                                <option value="${category.bugCategoryId}" <c:if test="${category.bugCategoryId == bug.bugCategory.bugCategoryId}">selected="selected"</c:if>>
                                    <c:out value="${category}" />
                                </option>
                            </c:forEach>
                        </select>
					</td>
					<td colspan="2" class="lastcol">
                        <select size="1">
                            <c:forEach var="priority" items="${bugPriorities}">
                                <option value="${priority.priorityId}" <c:if test="${priority == bug.bugPriority}">selected="selected"</c:if>>
                                    <c:out value="${priority}" />
                                </option>
                            </c:forEach>
                        </select>
					</td>
				</tr>
                <tr>
                    <th colspan="6" class="lastcol">Opis</th>
                </tr>
                <tr>
                    <td colspan="6" class="lastcol"><c:out value="${bug.description}" /></td>
                </tr>
				<c:if test="${not empty bug.stepByStep}">
					<tr>
	                    <th colspan="6" class="lastcol">Krok po kroku</th>
	                </tr>
	                <tr>
	                    <td colspan="6" class="lastcol"><c:out value="${bug.stepByStep}" /></td>
	                </tr>
                </c:if>
			</table>
		</div>
		<div class="contentbottom"></div>
		
		<c:if test="${not empty files}">
			<div id="pagecontentsubheader"><h3>Pliki</h3></div>
	        <div class="contenttop"></div>
	        <div class="contentmiddle">
		        <table cellspacing="0" class="standardtable">
		            <tr>
		                <th>Nazwa</th>
		                <th class="lastcol">Rozmiar</th>
		            </tr>
		            <c:forEach var="file" items="${files}" varStatus="status">
			            <tr>
			                <td><c:out value="${file.originalFileName}"/></td>
			                <td class="lastcol"><c:out value="${file.hashedFileName}"/></td>
			            </tr>
		            </c:forEach>
		        </table>
	        </div>
	        <div class="contentbottom"></div>
        </c:if>
		
		<div id="pagecontentsubheader"><h3>Ostatnie zdarzenia</h3></div>
		<div class="chartcontainer">
		<div class="chartbox">
			<div class="TabView" id="currentWeekTabView">
				<div class="Tabs">
				    <a><span>Lista komentarzy</span></a>
					<a><span>Historia zgloszenia</span></a>
				</div>
				<div class="contenttop"></div>
				<div class="Pages">
                    <div class="Page">
                    <table width="100%" cellspacing="12" cellpadding="4"><tr><td>
                        <c:if test="${not empty bug.comments}">
	                        <table cellspacing="0" class="standardtable" style="margin-bottom: 10px;"> 
	                            <tr>
	                                <th>Autor</th>
	                                <th>Data</th>
	                                <th class="lastcol">Treść</th>
	                            </tr>
	                            <c:forEach var="comment" items="${bug.comments}" varStatus="status">
		                            <tr>
		                                <td class="tit"><c:out value="${comment.commentAuthor}"/></td>
		                                <td><fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm"/></td>
		                                <td class="bod"><c:out value="${comment.commentText}" escapeXml="false"/></td>
		                            </tr>
	                            </c:forEach>
	                        </table>
                        </c:if>
                        <form action="<c:url value="/bugDetails.html?bugId=${param.bugId}"/>" method="POST">
                            <textarea id="addComm" name="addComm" rows="3" class="mceEditor addcomment" style="height: 120px;"></textarea>
                            <br/>
                            <input type="submit" value="dodaj komentarz" class="btn" />
                        </form>
                    </td></tr></table>
                    </div>
					<div class="Page">
					<table width="100%" cellspacing="12" cellpadding="4"><tr><td>
						<table cellspacing="0" class="standardtable"> 
							<tr>
								<th>Lp.</th>
								<th>Zdarzenie</th>
								<th>Autor</th>
								<th class="lastcol">Data</th>
							</tr>
							<c:forEach var="event" items="${bug.events}" varStatus="status">
							<tr>
								<td class="scount"><c:out value="${status.count}"/></td>	
								<td><c:out value="${event.evtSubject}"/></td>
								<td><c:out value="${event.evtAuthor}"/></td>
								<td class="lastcol"><fmt:formatDate value="${event.evtDate}" pattern="yyyy-MM-dd HH:mm"/></td>
							</tr>
							</c:forEach>
						</table>
					</td></tr></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	tabview_initialize('currentWeekTabView');
	</script>
</td>

	<td class="leftcells">
	<div id="pagecontentsubheader"><h3>Wprowadził</h3></div>
		<div class="contenttop"></div>
		<div class="contentmiddle">
			<table cellspacing="0" class="standardtable">
			<%--
			<tr>
				<th colspan="4" class="lastcol">
					<a href="javascript:blank()" onclick="showDiv('forminfoaboutinputerlink');" >Wprowadził</a>
				</th>
			</tr>
			--%>
			<tr>
				<td class="tabtitle">Login:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.inputer.login}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.inputer.firstName}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Nazwisko: </td>
				<td colspan="3" class="lastcol"><c:out value="${bug.inputer.lastName}"/></td>
			</tr>
				<td class="tabtitle">Tel:</td>
				<td style="width: 110px;"><c:out value="${bug.inputer.phone}"/></td>
				<td class="tabtitle">Kom:</td>
				<td class="lastcol" style="width: 110px;">..</td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.inputer.email}"/></td>
			</tr>
			</table>
		</div>
		<div class="contentbottom"></div>
		<div id="pagecontentsubheader"><h3>Zgłosił</h3></div>
		<div class="contenttop"></div>
		<div class="contentmiddle">
			<table cellspacing="0" class="standardtable">
			<%--
			<tr>
				<th colspan="4" class="lastcol">
					<a href="javascript:blank()" onclick="showDiv('forminfoaboutnotyfierlink');" >Zgłosił</a>
				</th>
			</tr>
			--%>
			<tr>
				<td class="tabtitle">Login:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.notifier.login}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.notifier.firstName}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Nazwisko: </td>
				<td colspan="3" class="lastcol"><c:out value="${bug.notifier.lastName}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td style="width: 110px;"><c:out value="${bug.notifier.phone}"/></td>
				<td class="tabtitle">Kom:</td>
				<td class="lastcol" style="width: 110px;"><c:out value="${bug.addPhone}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.notifier.email}"/></td>
			</tr>
			</table>
		</div>
		<div class="contentbottom"></div>
		<div id="pagecontentsubheader"><h3>Rozwiązuje</h3></div>
		<div class="contenttop"></div>
		<div class="contentmiddle">
			<table cellspacing="0" class="standardtable">
			<%--
			<th colspan="4" class="lastcol">
				<a href="javascript:blank()" onclick="showDiv('forminfoaboutnotyfierlink');">Rozwiązuje</a>
			</th>
			--%>
			<tr>
				<td class="tabtitle">Login:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.saviour.login}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Imie:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.saviour.firstName}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Nazwisko: </td>
				<td colspan="3" class="lastcol"><c:out value="${bug.saviour.lastName}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Tel:</td>
				<td style="width: 110px;"><c:out value="${bug.saviour.phone}"/></td>
				<td class="tabtitle">Kom:</td>
				<td class="lastcol" style="width: 110px;"><c:out value="${bug.addPhone}"/></td>
			</tr>
			<tr>
				<td class="tabtitle">Email:</td>
				<td colspan="3" class="lastcol"><c:out value="${bug.saviour.email}"/></td>
			</tr>
			</table>
		</div>
		</div>
	</td>
</tr>
</table>
</div>
</div>
