<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="personaldata" class="preferences">
<div id="pagecontentheader"><h2>Preferencje</h2></div>

<table cellspacing="0">
<tr>
<td class="leftcells">

<div id="pagecontentsubheader"><h3>Ustawienia danych osobowych</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">

<form action="" method="post">
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="2" class="lastcol">Dane osobowe</th>
	</tr>
	<tr>
		<td class="blank">Imie</td>
		<td class="lastcol"><input type="text" /></td>
	</tr>
	<tr>
		<td class="blank">Nazwisko</td>
		<td class="lastcol"><input type="text" /></td>
	</tr>
	<tr>
		<td class="blank">Email</td>
		<td class="lastcol"><input type="text" /></td>
	</tr>
	<tr>
		<td class="blank">Telefon</td>
		<td class="lastcol"><input type="text" /></td>
	</tr>
	<tr>
		<td class="blank">Mobile</td>
		<td class="lastcol"><input type="text" /></td>
	</tr>
</table>
<br />
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="2" class="lastcol">Zmiana hasła</th>
	</tr>
	<tr>
		<td class="blank">Login</td>
		<td class="lastcol">niezmienny</td>
	</tr>
	<tr>
		<td class="blank">Haslo</td>
		<td class="lastcol"><input type="password" /></td>
	</tr>
	<tr>
		<td class="blank">Haslo 2</td>
		<td class="lastcol"><input type="password" /></td>
	</tr>
</table>
<br />
<table cellspacing="0">
	<tr>
		<td colspan="2">
			<input name="_cancel" type="submit" value="anuluj" class="btn" />
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