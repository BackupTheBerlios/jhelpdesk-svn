<%@page contentType="text/html;charset=UTF-8" import="de.berlios.jhelpdesk.web.charts.TicketStatsByPriorityDatasetProducer"%>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<jsp:useBean id="ppChart" class="de.berlios.jhelpdesk.web.charts.HDChartPostProcessor"/>

<%
TicketStatsByPriorityDatasetProducer currentWeek = ( TicketStatsByPriorityDatasetProducer ) request.getAttribute( "currentWeek" );
TicketStatsByPriorityDatasetProducer currentMonth = ( TicketStatsByPriorityDatasetProducer ) request.getAttribute( "currentMonth" );
TicketStatsByPriorityDatasetProducer threeMonth = ( TicketStatsByPriorityDatasetProducer ) request.getAttribute( "threeMonth" );
TicketStatsByPriorityDatasetProducer allTime = ( TicketStatsByPriorityDatasetProducer ) request.getAttribute( "allTime" );
%>

<div id="stats">
	
	<div id="pagecontentheader"><h2>Statystyki</h2></div>
	
	<div class="chartcontainer bypriority">
	
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g ważności - bieżący tydzień</h3></div>
			<div id="currentWeekChart">
				<div class="TabView" id="currentWeekTabView">
					<%-- div class="Tabs"><a><span>Wskaźniki tendencji</span></a></div --%>
					<div class="contenttop"></div>		
					<div class="Pages">
						<div class="Page">
							<table width="100%" cellspacing="12" cellpadding="4">
								<tr>
									<td class="chart">
										<cewolf:chart id="line" type="pie3d" showlegend="false">
											<cewolf:data><cewolf:producer id="currentWeek"/></cewolf:data>
											<cewolf:chartpostprocessor id="ppChart" />
										</cewolf:chart>
										<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line" renderer="cewolf" width="350" height="200"/>' width="350" height="200">
									</td>
									<td class="data">
										<table class="statsdata standardtable" cellspacing="0" cellpadding="4">
											<tr class="header">
												<th>Lp</th>
												<th>Kategoria</th>	
												<th class="lastcol">Ilość</th>
											</tr>
											<c:forEach var="entry" items="${currentWeekData}" varStatus="s">
											<tr class="data">
												<td class="scount"><c:out value="${s.count}"/></td>
												<td><c:out value="${entry.key}"/></td>
												<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
											</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="contentbottom"></div>
				</div>
			</div>
		</div>
		
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g ważności - bieżący miesiąc</h3></div>
			<div id="currentMonthChart">
				<div class="TabView" id="currentWeekTabView">
					<%-- div class="Tabs"><a><span>Wskaźniki tendencji</span></a></div --%>
					<div class="contenttop"></div>		
					<div class="Pages">
						<div class="Page">
							<table width="100%" cellspacing="12" cellpadding="4">
								<tr>
									<td class="chart">
										<cewolf:chart id="line2" type="pie3d" showlegend="false">
											<cewolf:data><cewolf:producer id="currentMonth"/></cewolf:data>
											<cewolf:chartpostprocessor id="ppChart" />
										</cewolf:chart>
										<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line2" renderer="cewolf" width="350" height="200"/>' width="350" height="200">
									</td>
									<td class="data">
										<table class="statsdata standardtable" cellspacing="0" cellpadding="4">
											<tr class="header">
												<th>Lp</th>
												<th>Kategoria</th>	
												<th class="lastcol">Ilość</th>
											</tr>
											<c:forEach var="entry" items="${currentMonthData}" varStatus="s">
											<tr class="data">
												<td class="scount"><c:out value="${s.count}"/></td>
												<td><c:out value="${entry.key}"/></td>
												<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
											</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="contentbottom"></div>
				</div>
			</div>
		</div>

		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g ważności - poprzednie 3 miesiące</h3></div>
			<div id="currentMonthChart">
				<div class="TabView" id="currentWeekTabView">
					<%-- div class="Tabs"><a><span>Wskaźniki tendencji</span></a></div --%>
					<div class="contenttop"></div>		
					<div class="Pages">
						<div class="Page">
							<table width="100%" cellspacing="12" cellpadding="4">
								<tr>
									<td class="chart">
										<cewolf:chart id="line3" type="pie3d" showlegend="false">
											<cewolf:data><cewolf:producer id="threeMonth"/></cewolf:data>
											<cewolf:chartpostprocessor id="ppChart" />
										</cewolf:chart>
										<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line3" renderer="cewolf" width="350" height="200"/>' width="350" height="200">
									</td>
									<td class="data">
										<table class="statsdata standardtable" cellspacing="0" cellpadding="4">
											<tr class="header">
												<th>Lp</th>
												<th>Kategoria</th>	
												<th class="lastcol">Ilość</th>
											</tr>
											<c:forEach var="entry" items="${threeMonthsData}" varStatus="s">
											<tr class="data">
												<td class="scount"><c:out value="${s.count}"/></td>
												<td><c:out value="${entry.key}"/></td>
												<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
											</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="contentbottom"></div>
				</div>
			</div>
		</div>
		
		<div class="chartbox">
			<div id="pagecontentsubheader"><h3>Zgłoszenia w/g ważności - wszystkie</h3></div>
			<div id="currentMonthChart">
				<div class="TabView" id="currentWeekTabView">
					<%-- div class="Tabs"><a><span>Wskaźniki tendencji</span></a></div --%>
					<div class="contenttop"></div>		
					<div class="Pages">
						<div class="Page">
							<table width="100%" cellspacing="12" cellpadding="4">
								<tr>
									<td class="chart">
										<cewolf:chart id="line4" type="pie3d" showlegend="false">
											<cewolf:data><cewolf:producer id="allTime"/></cewolf:data>
											<cewolf:chartpostprocessor id="ppChart" />
										</cewolf:chart>
										<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line4" renderer="cewolf" width="350" height="200"/>' width="350" height="200">
									</td>
									<td class="data">
										<table class="statsdata standardtable" cellspacing="0" cellpadding="4">
											<tr class="header">
												<th>Lp</th>
												<th>Kategoria</th>	
												<th class="lastcol">Ilość</th>
											</tr>
											<c:forEach var="entry" items="${allData}" varStatus="s">
											<tr class="data">
												<td class="scount"><c:out value="${s.count}"/></td>
												<td><c:out value="${entry.key}"/></td>
												<td class="entryvalue lastcol"><c:out value="${entry.value}"/></td>
											</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="contentbottom"></div>
				</div>
			</div>
		</div>
		
	</div>

</div>