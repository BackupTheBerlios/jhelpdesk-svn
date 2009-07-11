<%@page 
contentType="text/html;charset=UTF-8"
import="de.berlios.jhelpdesk.web.charts.BugStatsByCategoryDatasetProducer,
de.berlios.jhelpdesk.web.charts.category.Vertical3DChartCategoryDatasetProducer,
de.berlios.jhelpdesk.web.charts.BugsStatsByCategoryFullDatasetProducer"%>

<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<jsp:useBean id="ppChart" class="de.berlios.jhelpdesk.web.charts.HDChartPostProcessor"/>

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

<div class="TabView" id="currentMonthTabView">

<div class="Tabs">
<a><span>Podsumowanie</span></a>
<a><span>Wskaźniki tendencji</span></a>
<a><span>Szczegółowa statystyka</span></a>
</div>

<div class="contenttop"></div>

<div class="Pages">

<div class="Page">
<table width="100%" cellspacing="12" cellpadding="4">
<tr>
<td class="chart">
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
<td class="data">
miejsce na tabele z danymi
</td>
</tr>
</table>
</div>

<div class="Page">
<table width="100%" cellspacing="12" cellpadding="4">
<td class="chart">
<cewolf:chart id="verticalBar3D" type="line" xaxislabel="Dni tygodnia">
<cewolf:data>
<cewolf:producer id="currentMonthWeekByWeekData" />
</cewolf:data>
</cewolf:chart>
<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="verticalBar3D" renderer="cewolf" width="320" height="200"/>' width="320" height="200">
</td>
<td class="data">
<table class="statsdata standardtable" cellspacing="0">
<tr class="header">
<th rowspan="2">Lp</th>
<th rowspan="2">Kategoria</th>	
<th class="periodtop" colspan="3">Okresy</th>
<th class="lastcol" rowspan="2">Razem</th>
</tr>
<tr class="header period">
<th>1 - 10</th>
<th>11 - 20</th>
<th>21 - 31</th>
</tr>
<c:forEach var="entry" items="${currentMonthData}" varStatus="s">
<tr class="data">
<td class="scount"><c:out value="${s.count}"/></td>
<td class="entrykey"><c:out value="${entry.key}"/></td>
<td>0</td>
<td>0</td>
<td>0</td>
<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
</tr>
</c:forEach>
</table>
</td>
</tr>
</table>
</div>

<div class="Page">
tu na razie nic nie ma
</div>

</div>

<div class="contentbottom"></div>

</div>

<script type="text/javascript"> tabview_initialize('currentMonthTabView'); </script>
