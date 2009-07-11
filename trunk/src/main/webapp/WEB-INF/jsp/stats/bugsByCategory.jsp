<%@page 
	contentType="text/html;charset=UTF-8"
	import="com.jjhop.helpdesk.web.charts.BugStatsByCategoryDatasetProducer,
	com.jjhop.helpdesk.web.charts.category.Vertical3DChartCategoryDatasetProducer,
	com.jjhop.helpdesk.web.charts.BugsStatsByCategoryFullDatasetProducer"%>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<jsp:useBean id="ppChart" class="com.jjhop.helpdesk.web.charts.HDChartPostProcessor"/>

<%
	BugStatsByCategoryDatasetProducer currentWeek = ( BugStatsByCategoryDatasetProducer ) request.getAttribute( "currentWeek" );
	BugStatsByCategoryDatasetProducer currentMonth = ( BugStatsByCategoryDatasetProducer ) request.getAttribute( "currentMonth" );
	BugStatsByCategoryDatasetProducer threeMonth = ( BugStatsByCategoryDatasetProducer ) request.getAttribute( "threeMonths" );
	BugStatsByCategoryDatasetProducer allTime = ( BugStatsByCategoryDatasetProducer ) request.getAttribute( "allTime" );
	
	Vertical3DChartCategoryDatasetProducer currentWeekDayByDay = 
		( Vertical3DChartCategoryDatasetProducer ) request.getAttribute( "currentWeekDayByDayData" );
	Vertical3DChartCategoryDatasetProducer currentMonthWeekByWeekData = 
		( Vertical3DChartCategoryDatasetProducer ) request.getAttribute( "currentMonthWeekByWeekData" );
	Vertical3DChartCategoryDatasetProducer threeMonthsMonthByMonth = 
		( Vertical3DChartCategoryDatasetProducer ) request.getAttribute( "threeMonthsMonthByMonth" );
	BugsStatsByCategoryFullDatasetProducer allTimeFull = 
		( BugsStatsByCategoryFullDatasetProducer ) request.getAttribute( "allTimeFull" );

%>
<table width="100%">
	<tr class="sectionheader">
		<td colspan="2">Zgłoszenie w/g kategorii - bieżący tydzień (zbiorcze)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart 
				id="line" 
				type="pie3d"
				showlegend="false">
				<cewolf:data>
					<cewolf:producer id="currentWeek"/>
				</cewolf:data>
				<cewolf:chartpostprocessor id="ppChart" />
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line" renderer="cewolf" width="350" height="200"/>' alt=" "
				width="350" height="200" style="background-image: url(/helpdesk/i/statbg.gif);background-repeat:no-repeat;" >
		</td>
		<td>sdfsdfsdf</td>
	</tr>
	<tr>
		<td colspan="2">Zgłoszenie w/g kategorii - bieżący tydzień (tendencja)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart id="verticalBar3D" type="line" xaxislabel="Dni tygodnia">
				<cewolf:data>
					<cewolf:producer id="currentWeekDayByDayData" />
				</cewolf:data>
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="verticalBar3D" renderer="cewolf" width="320" height="200"/>' width="320" height="200">
		</td>
		<td class="up">
			<table class="statsdata" cellspacing="0">
				<tr class="header">
					<td rowspan="2">Lp</td>
					<td rowspan="2">Kategoria</td>	
					<td colspan="7">Kolejne dni</td>
					<td rowspan="2">Razem</td>
				</tr>
				<tr class="header">
					<td>Pon.</td>
					<td>Wto.</td>
					<td>Śro.</td>
					<td>Czw.</td>
					<td>Pią.</td>
					<td>Sob.</td>
					<td>Ndz.</td>
				</tr>
				<c:forEach var="entry" items="${currentWeekData}" varStatus="s">
					<tr class="data">
						<td><c:out value="${s.count}"/></td>
						<td><c:out value="${entry.key}"/></td>
						<td>1</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td><c:out value="${entry.value}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2"><hr noshade="noshade" size="3"/></td>
	</tr>
	<tr class="sectionheader">
		<td colspan="2">Zgłoszenie w/g kategorii - bieżący miesiąc (zbiorcza)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart 
				id="line2" 
				type="pie3d"
				showlegend="false">
				<cewolf:data>
					<cewolf:producer id="currentMonth"/>
				</cewolf:data>
				<cewolf:chartpostprocessor id="ppChart" />
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line2" renderer="cewolf" width="350" height="200"/>' width="350" height="200">
		</td>
		<td>sdfsdfsdf</td>
	</tr>
	<tr class="sectionheader">
		<td colspan="2">Zgłoszenie w/g kategorii - bieżący miesiąc (tendencja)</td>
	</tr>
		<td class="up">
			<cewolf:chart id="verticalBar3D" type="line" xaxislabel="Dni tygodnia">
				<cewolf:data>
					<cewolf:producer id="currentMonthWeekByWeekData" />
				</cewolf:data>
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="verticalBar3D" renderer="cewolf" width="320" height="200"/>' width="320" height="200">
		</td>
		<td class="up">
			<table class="statsdata" cellspacing="0">
				<tr class="header">
					<td rowspan="2">Lp</td>
					<td rowspan="2">Kategoria</td>	
					<td colspan="3">Okresy</td>
					<td rowspan="2">Razem</td>
				</tr>
				<tr class="header">
					<td>1 - 10</td>
					<td>11 - 20</td>
					<td>21 - 31</td>
				</tr>
				<c:forEach var="entry" items="${currentMonthData}" varStatus="s">
					<tr class="data">
						<td><c:out value="${s.count}"/></td>
						<td><c:out value="${entry.key}"/></td>
						<td>0</td>
						<td>0</td>
						<td>0</td>
						<td><c:out value="${entry.value}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	<tr>
		<td colspan="2"><hr noshade="noshade" size="3"/></td>
	</tr>
	<tr class="sectionheader">
		<td colspan="2">Zgłoszenie w/g kategorii - poprzednie 3 m-ce (podsumowanie)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart 
				id="line3" 
				type="pie3d"
				showlegend="false">
				<cewolf:data>
					<cewolf:producer id="threeMonths"/>
				</cewolf:data>
				<cewolf:chartpostprocessor id="ppChart" />
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line3" renderer="cewolf" width="350" height="250"/>' width="350" height="250">
		</td>
		<td>tsdsdgfsdf</td>
	</tr>
	<tr class="sectionheader">
		<td colspan="3">Zgłoszenie w/g kategorii - poprzednie 3 m-ce (tendencja)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart id="verticalBar3D" type="line" xaxislabel="Kolejne miesiące">
				<cewolf:data>
					<cewolf:producer id="threeMonthsMonthByMonth" />
				</cewolf:data>
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="verticalBar3D" renderer="cewolf" width="320" height="200"/>' width="320" height="200">
		</td>
		<td class="up">
			<table class="statsdata" cellspacing="0">
				<tr class="header">
					<td rowspan="2">Lp</td>
					<td rowspan="2">Kategoria</td>	
					<td colspan="3">Miesiące</td>
					<td rowspan="2">Razem</td>
				</tr>
				<tr class="header">
					<td>II 2006</td>
					<td>III 2006</td>
					<td>IV 2006</td>
				</tr>
				<c:forEach var="entry" items="${threeMonthsData}" varStatus="s">
					<tr class="data">
						<td><c:out value="${s.count}"/></td>
						<td><c:out value="${entry.key}"/></td>
						<td>1</td>
						<td>0</td>
						<td>0</td>
						<td><c:out value="${entry.value}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2"><hr noshade="noshade" size="3"/></td>
	</tr>
	<tr class="sectionheader">
		<td colspan="2">Zgłoszenie w/g kategorii - wszystkie (podsumowanie)</td>
	</tr>
	<tr>
		<td class="up">
			<cewolf:chart 
				id="line4" 
				type="horizontalbar3d"
				showlegend="false">
				<cewolf:data>
					<cewolf:producer id="allTimeFull"/>
				</cewolf:data>
			</cewolf:chart>
			<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line4" renderer="cewolf" width="500" height="250"/>' alt=""
			width="500" height="250" style="background-image: url(/helpdesk/i/statbg.gif);background-repeat:no-repeat;">
		</td>
		<td class="up">
			<table class="statsdata" cellspacing="0">
				<tr class="header">
					<td>Lp</td>
					<td>Kategoria</td>	
					<td>Ilość</td>
				</tr>
				<c:forEach var="entry" items="${allData}" varStatus="s">
					<tr class="data">
						<td><c:out value="${s.count}"/></td>
						<td><c:out value="${entry.key}"/></td>
						<td><c:out value="${entry.value}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>