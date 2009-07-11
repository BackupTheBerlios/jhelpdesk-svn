<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="allbugs" class="bugslist">
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
				<div class="active"><button><h3><span>Krok po kroku...</span></h3></button></div>
				<div><button><h3><span>Dodatkowe pliki</span></h3></button></div>
				<div><button><h3 class="lastStep"><span>Podsumowanie</span></h3></button></div>
			</div>
		</td>
		<td id="middlecenter">
			<form action="<c:url value="/newBug.html"/>" method="post" enctype="multipart/form-data">
				<table id="table2" cellspacing="0">
					<tr>
						<td>Kroki by powtórzyć:</td>
					</tr>
					<tr>
						<td>
							<spring:bind path="hdbug.stepByStep">
								<textarea class="mceEditor" name="<c:out value="${status.expression}"/>" rows="15">
								<c:out value="${status.value}" /></textarea>
							</spring:bind>
						</td>
					</tr>
					<tr>
						<td>
							<input class="btn" type="submit" name="_target1" value="&laquo; Cofnij"/> <input class="btn" type="submit" name="_target3" value="Dalej &raquo;"/>
						</td>
					</tr>
				</table>
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
