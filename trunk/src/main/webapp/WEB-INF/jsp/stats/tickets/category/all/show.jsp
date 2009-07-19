<%@page contentType="text/html;charset=UTF-8"
        import="de.berlios.jhelpdesk.web.charts.TicketStatsByCategoryDatasetProducer,
            de.berlios.jhelpdesk.web.charts.category.Vertical3DChartCategoryDatasetProducer,
            de.berlios.jhelpdesk.web.charts.TicketsStatsByCategoryFullDatasetProducer"
%>

<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<jsp:useBean id="ppChart" class="de.berlios.jhelpdesk.web.charts.HDChartPostProcessor"/>

<%
	TicketStatsByCategoryDatasetProducer allTime = ( TicketStatsByCategoryDatasetProducer ) request.getAttribute( "allTime" );
	TicketsStatsByCategoryFullDatasetProducer allTimeFull = ( TicketsStatsByCategoryFullDatasetProducer ) request.getAttribute( "allTimeFull" );
%>

<div class="TabView" id="allTabView">

	<div class="Tabs">
		<a><span>Podsumowanie</span></a>
		<a><span>Szczegółowa statystyka</span></a>
	</div>

<div class="contenttop"></div>

	<div class="Pages">

		<div class="Page">
			<table width="100%" cellspacing="12" cellpadding="4">
				<tr>
					<td class="chart">
						<cewolf:chart 
							id="line4" 
							type="horizontalbar3d"
							showlegend="false">
							<cewolf:data>
								<cewolf:producer id="allTimeFull"/>
							</cewolf:data>
						</cewolf:chart>
						<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line4" renderer="cewolf" width="500" height="240"/>' alt=""
						width="500" height="240" style="background-image: url(/helpdesk/i/statbg.gif);background-repeat:no-repeat;">
					</td>
					<td class="data">
						<table class="statsdata standardtable" cellspacing="0">
							<tr class="header">
								<th>Lp</th>
								<th>Kategoria</th>	
								<th class="lastcol">Ilość</th>
							</tr>
							<c:forEach var="entry" items="${allData}" varStatus="s">
							<tr class="data">
								<td class="scount"><c:out value="${s.count}"/></td>
								<td class="entrykey"><c:out value="${entry.key}"/></td>
								<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</div>

		<div class="Page">
			tego ma nie byc widac :)
		</div>
	</div>
	
	<div class="contentbottom"></div>

</div>
<script type="text/javascript"> tabview_initialize('allTabView'); </script>