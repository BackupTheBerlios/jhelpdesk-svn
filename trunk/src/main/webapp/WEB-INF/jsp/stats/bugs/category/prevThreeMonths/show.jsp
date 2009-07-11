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
<div class="TabView" id="prevThreeMonthsTabView">

<div class="Tabs">
<a><span>Podsumowanie</span></a>
<a><span>Wskazniki tendencji</span></a>
<a><span>Szczególowa statystyka</span></a>
</div>

<div class="contenttop"></div>

<div class="Pages">

<div class="Page">
<table width="100%" cellspacing="12" cellpadding="4">
<tr>
<td class="chart">
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
<td class="data">tabelka z danymi</td>
</tr>
</table>
</div>

<div class="Page">
<table width="100%" cellspacing="12" cellpadding="4">
<tr>
<td class="chart">
<cewolf:chart id="verticalBar3D" type="line" xaxislabel="Kolejne miesiące">
<cewolf:data>
<cewolf:producer id="threeMonthsMonthByMonth" />
</cewolf:data>
</cewolf:chart>
<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="verticalBar3D" renderer="cewolf" width="320" height="200"/>' width="320" height="200">
</td>
<td class="data">
<table class="statsdata standardtable" cellspacing="0">
<tr class="header">
<th rowspan="2">Lp</th>
<th rowspan="2">Kategoria</th>	
<th class="periodtop" colspan="3">Miesiące</th>
<th class="lastcol" rowspan="2">Razem</th>
</tr>
<tr class="header period">
<th>II 2006</th>
<th>III 2006</th>
<th>IV 2006</th>
</tr>
<c:forEach var="entry" items="${threeMonthsData}" varStatus="s">
<tr class="data">
<td class="scount"><c:out value="${s.count}"/></td>
<td class="entrykey"><c:out value="${entry.key}"/></td>
<td>1</td>
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
<table>
<tr>
<td>Username:</td>
<td><input style="width: 120px;" type="text" name="username" /></td>
</tr>
<tr>
<td>Password:</td>
<td><input style="width: 120px;" type="password" name="password" /></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input type="submit" value="Submit" /></td>
</tr>
</table>
</div>

</div>

<div class="contentbottom"></div>

</div>

<script type="text/javascript"> tabview_initialize('prevThreeMonthsTabView'); </script>
