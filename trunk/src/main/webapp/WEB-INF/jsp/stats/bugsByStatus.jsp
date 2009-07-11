<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<jsp:useBean id="ppChart" class="com.jjhop.helpdesk.web.charts.HDChartPostProcessor"/>
<jsp:useBean id="pageViews" class="com.jjhop.helpdesk.web.charts.BugStatsByStatusDatasetProducer" scope="request"/>
<cewolf:chart 
	id="line" 
	title="Zgłoszenia w podziale na bieżący status" 
	type="pie3d"
	showlegend="false">
	<cewolf:gradientpaint>
		<cewolf:point x="0" y="0" color="#FFFFcc"/>
		<cewolf:point x="500" y="300" color="#C8C8C8"/>
	</cewolf:gradientpaint>
	<cewolf:data>
		<cewolf:producer id="pageViews"/>
	</cewolf:data>
	<cewolf:chartpostprocessor id="ppChart" />
</cewolf:chart>
<p>
	<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line" renderer="cewolf" width="500" height="300"/>' width="500" height="300">
</p>