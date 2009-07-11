<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="evennotify" class="preferences">
<div id="pagecontentheader"><h2>Preferencje</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Ustawienia powiadamiania</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="6" class="lastcol">Powiadomienia o zgłoszeniach</th>
	</tr>
	<tr class="numberofitems">
		<td class="blank">&nbsp;</td>
		<td>Po zdarzeniu</td>
		<td>1 raz / dzień</td>
		<td>1 raz / tydzień</td>
		<td>1 raz / miesiąć</td>
		<td class="lastcol">nigdy</td>
	</tr>
	<tr>
		<td class="blank">przypisanie zgloszenia  (zawsze na biurku)</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">przypisanie zgloszenia komus innemu (zawsze na biurku)</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">zmiana rozwiazujacego</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">odrzucenie zgloszenia</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">rozwiazanie zgloszenia</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">zamkniecie zgloszenia</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">dodanie plikow do zgloszenia</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">usuniecie plikow</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">dodanie komentarza</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">zmiana kategorii</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">zmiana waznosci</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">zmiana opisu problemu</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">dodanie/usuniecie kategorii zglaszanych problemow</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
</table>
<br />
<table cellspacing="0" class="standardtable">
	<tr>
		<th colspan="6" class="lastcol">Powiadomienia o zmianach w bazie wiedzy</th>
	</tr>
	<tr class="numberofitems">
		<td class="blank">&nbsp;</td>
		<td>Po zdarzeniu</td>
		<td>1 raz / dzień</td>
		<td>1 raz / tydzień</td>
		<td>1 raz / miesiąć</td>
		<td class="lastcol">nigdy</td>
	</tr>
	<tr>
		<td class="blank">dodanie/usuniecie sekcji w bazie wiedzy</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">dodanie/usuniecie artykulu w bazie wiedzy</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
	<tr>
		<td class="blank">dodanie/usuniecie artykulu w bazie wiedzy</td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td><input type="checkbox"/></td>
		<td class="lastcol"><input type="checkbox"/></td>
	</tr>
</table>
<br />
<table cellspacing="0">
	<tr>
		<td colspan="4">
			<input name="_cancel" type="submit" value="anuluj" class="btn" />
			<input name="_save" type="submit" value="zapisz" class="btn" />
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