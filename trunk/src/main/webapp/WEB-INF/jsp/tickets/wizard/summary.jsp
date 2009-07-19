<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="alltickets" class="ticketslist">
<div id="pagecontentheader"><h2>Zgłoszenia</h2></div>
<div id="pagecontentsubheader"><h3>Zgłaszanie problemu</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<table id="table1" cellspacing="0">
	<tr class="top">
		<td id="topleft">&nbsp;</td>
		<td id="topcenter">&nbsp;</td>
		<td id="topright"><div>&nbsp;</div></td>
	</tr>
	<tr class="middle">
		<td id="middleleft">
			<div id="steps">
				<div><button><h3 class="firstStep"><span>Osoba zgłaszająca</span></h3></button></div>
				<div><button><h3><span>Opis zgłoszenia</span></h3></button></div>
				<div><button><h3><span>Krok po kroku...</span></h3></button></div>
				<div><button><h3><span>Dodatkowe pliki</span></h3></button></div>
				<div class="active"><button><h3 class="lastStep"><span>Podsumowanie</span></h3></button></div>
			</div>
		</td>
		<td id="middlecenter">
			<form action="<c:url value="/newTicket.html"/>" method="post" enctype="multipart/form-data">
				<table id="table2" class="summary standardtable" cellspacing="0">
					<tr>
						<th class="lastcol" colspan="2">Powiadamiający</th>
					</tr>
					<tr>
						<td class="desc">Imie</td>
						<td class="lastcol"><c:out value="${hdticket.notifier.firstName}"/></td>
					</tr>
					<tr>
						<td class="desc">Nazwisko</td>
						<td class="lastcol"><c:out value="${hdticket.notifier.lastName}"/></td>
					</tr>
					<tr>
						<td class="desc">Telefon</td>
						<td class="lastcol"><c:out value="${hdticket.notifier.phone}"/></td>
					</tr>
					<tr>
						<td class="desc">E-mail</td>
						<td class="lastcol"><c:out value="${hdticket.notifier.email}"/></td>
					</tr>
					<tr>
						<th class="lastcol" colspan="2">Opis problemu</th>
					</tr>
					<tr>
						<td class="desc">Przyczyna</td>
						<td class="lastcol"><c:out value="${hdticket.subject}"/></td>
					</tr>
					<tr>
						<td class="desc">Opis</td>
						<td class="lastcol"><c:out value="${hdticket.description}"/></td>
					</tr>
					<c:if test="${not empty hdticket.stepByStep}">
					<tr>
						<td class="desc">Opis krok po kroku</td>
						<td class="lastcol"><c:out value="${hdticket.stepByStep}"/></td>
					</tr>
					</c:if>
					<c:if test="${not empty hdticket.addFilesList}">
					<tr>
						<td class="desc">Lista plikow</td>
						<td class="lastcol">
							<table class="files standardtable" cellspacing="0">
								<tr>
									<th class="yhnumber">Lp.</th>
									<th class="tdfile">Nazwa</th>
									<th class="tdsize">Rozmiar</th>
									<th class="tdlink lastcol">D</th>
								</tr>
								<c:forEach var="file" items="${hdticket.addFilesList}" varStatus="status">
								<tr>
									<td class="tdnumber"><c:out value="${status.count}"/></td>
									<td class="tdfile"><c:out value="${file.originalFileName}"/></td>
									<td class="tdsize"><c:out value="${file.fileSize}"/></td>
									<td class="tdlink lastcol"><input type="image" name="x" src="<c:url value="/i/delete.gif"/>" /></td>
								</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					</c:if>
				</table>
				<br />
				<input class="btn" type="submit" name="_target3" value="&laquo; Cofnij"/>
				<input class="btn" type="submit" name="_finish" value="Zapisz"/>
			</form>
		</td>
		<td id="middleright">&nbsp;</td>
	</tr>
	<tr class="bottom">
		<td id="bottomleft">&nbsp;</td>
		<td id="bottomcenter">&nbsp;</td>
		<td id="bottomright"><div>&nbsp;</div></td>
	</tr>
</table>

</div>
<div class="contentbottom"></div>
</div>
</div>
