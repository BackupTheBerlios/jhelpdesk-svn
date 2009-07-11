<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="lookandfeel" class="preferences">
<div id="pagecontentheader"><h2>Preferencje</h2></div>

<table cellspacing="0">
<tr>
<td class="rightcells">

<div id="pagecontentsubheader"><h3>Wygląd, język, strona startowa</h3></div>
<div id="content">
<div class="contenttop"></div>
<div class="contentmiddle">
<table cellspacing="0" class="standardtable">
<tr>
	<th colspan="3" class="lastcol">Temat</th>
</tr>
<tr class="options">
	<td style="width: 186px;">
		<input type="radio" name="x"/><span>Temat pierwszy</span><br />
		<div><img src="http://www.gram.pl/www_image.asp?ip=upl/galerie/425/1.jpg&fn=g" width="150" height="100" alt="d"/></div>
	</td>
	<td style="width: 186px;">
		<input type="radio" name="x"/><span>Temat drugi</span><br />
		<div><img src="http://www.gram.pl/www_image.asp?ip=upl/galerie/425/6.jpg&fn=g" width="150" height="100" alt="d"/></div>
	</td>
	<td class="lastcol" style="width: 188px;">
		<input type="radio" name="x"/><span>Temat trzeci</span><br />
		<div><img src="http://www.gram.pl/www_image.asp?ip=upl/galerie/237/1.jpg&fn=g" width="150" height="100" alt="d"/></div>
	</td>
</tr>
</table>	
<br />
<table cellspacing="0" class="standardtable">
<tr>
	<th colspan="5" class="lastcol">Strona startowa</th>
</tr>
<tr class="options">
	<td style="width: 111px;"><input type="radio" name="x"/><span>biurko</span></td>
	<td style="width: 111px;"><input type="radio" name="x"/><span>wszystkie zgłoszenia</span></td>
	<td style="width: 111px;"><input type="radio" name="x"/><span>zgłoszenia nieprzypisane</span></td>
	<td style="width: 111px;"><input type="radio" name="x"/><span>moje zgłoszenia</span></td>
	<td class="lastcol" style="width: 112px;"><input type="radio" name="x"/><span>baza wiedzy</span></td>
</tr>
</table>	
<br />
<table cellspacing="0" class="standardtable">
<tr>
	<th colspan="4" class="lastcol">Wybór języka interfejsu</th>
</tr>
<tr class="options">
	<td style="width: 140px;"><input type="radio" name="x"/><span>polski</span></td>
	<td style="width: 140px;"><input type="radio" name="x"/><span>angielski</span></td>
	<td style="width: 140px;"><input type="radio" name="x"/><span>hiszpański</span></td>
	<td class="lastcol" style="width: 140px;"><input type="radio" name="x"/><span>portugalski</span></td>
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
</div>
<div class="contentbottom"></div>
</div>

</td>
</tr>
</table>

</div>