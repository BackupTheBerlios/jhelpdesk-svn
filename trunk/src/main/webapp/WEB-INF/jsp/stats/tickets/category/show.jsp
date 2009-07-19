<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div id="stats">
	<div id="pagecontentheader"><h2>Statystyki</h2></div>
	<div class="chartcontainer">
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g kategorii - bieżący tydzień</h3></div>
            <tiles:insertTemplate template="/WEB-INF/jsp/stats/tickets/category/currentWeek/show.jsp" flush="true" />
		</div>
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g kategorii - bieżący miesiąc</h3></div>
			<tiles:insertTemplate template="/WEB-INF/jsp/stats/tickets/category/currentMonth/show.jsp" flush="true" />
		</div>
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g kategorii - poprzednie 3 m-ce</h3></div>
			<tiles:insertTemplate template="/WEB-INF/jsp/stats/tickets/category/prevThreeMonths/show.jsp" flush="true" />
		</div>
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g kategorii - wszystkie</h3></div>
			<tiles:insertTemplate template="/WEB-INF/jsp/stats/tickets/category/all/show.jsp" flush="true" />
		</div>
	</div>
</div>
