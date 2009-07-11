<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="knowledgebase" class="management">
<div id="pagecontentheader"><h2>Pomoc</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Baza wiedzy</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<form action="" method="post">
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="2" class="lastcol">Kryteria wyszukiwania</th>
	</tr>
	<tr>
		<td>Od</td>
		<td class="lastcol"><input type="text" size="10" /></td>
	</tr>
	<tr>
		<td>Do</td>
		<td class="lastcol"><input type="text" size="10" /></td>
	</tr>
	<tr>
		<td>Szukana fraza</td>
		<td class="lastcol"><input type="text" size="40" /></td>
	</tr>
</table>
<br />
<table cellspacing="0">
	<tr>
		<td colspan="2">
			<input type="submit" name="search" src="<c:url value="szukaj"/>" class="btn" />
		</td>
	</tr>
</table>
</form>
<br /><br />
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="2" class="lastcol">Lista wpisów</th>
	</tr>
	<tr>
		<td class="lastcol">
			<ol>
			<c:forEach items="${sections}" var="itemSection">
				<li><c:out value="${itemSection.sectionName}"/>
					<ul>
						<c:forEach items="${itemSection.articles}" var="art">
							<li>
								<a href="<c:url value="/help/base.html?key=details&id=${art.knowledgeId}"/>">
									<c:out value="${art.title}"/>
								</a>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
			</ol>
		</td>
	</tr>
</table>

</div>
<div class="contentbottom"></div>
</div>

</td>
</tr>
</table>

</div>
