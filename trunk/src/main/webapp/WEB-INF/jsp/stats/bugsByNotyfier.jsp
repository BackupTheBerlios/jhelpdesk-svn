<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<jsp:useBean id="ppChart" class="com.jjhop.helpdesk.web.charts.HDChartPostProcessor"/>
<jsp:useBean id="pageViews1" class="com.jjhop.helpdesk.web.charts.BugStatsByNotyfier1DatasetProducer" scope="request"/>
<jsp:useBean id="pageViews2" class="com.jjhop.helpdesk.web.charts.BugStatsByNotyfier2DatasetProducer" scope="request"/>
	
<cewolf:chart 
	id="line1" 
	title="Zgłoszenia w podziale na zgłaszającego - wszystkie" 
	type="pie3d"
	showlegend="false">
	<cewolf:gradientpaint>
		<cewolf:point x="0" y="0" color="#FFFFcc"/>
		<cewolf:point x="500" y="300" color="#C8C8C8"/>
	</cewolf:gradientpaint>
	<cewolf:data>
		<cewolf:producer id="pageViews1"/>
	</cewolf:data>
	<cewolf:chartpostprocessor id="ppChart" />
</cewolf:chart>

<cewolf:chart 
	id="line2" 
	title="Zgłoszenia w podziale na zgłaszającego - rozwiązywane" 
	type="pie3d"
	showlegend="false">
	<cewolf:gradientpaint>
		<cewolf:point x="0" y="0" color="#FFFFcc"/>
		<cewolf:point x="500" y="300" color="#C8C8C8"/>
	</cewolf:gradientpaint>
	<cewolf:data>
		<cewolf:producer id="pageViews2"/>
	</cewolf:data>
	<cewolf:chartpostprocessor id="ppChart" />
</cewolf:chart>

<p>
	<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line1" renderer="cewolf" width="500" height="300"/>' width="500" height="300">
</p>
<p>
	<img src='<% out.print( request.getContextPath() ); %>/<cewolf:imgurl chartid="line2" renderer="cewolf" width="500" height="300"/>' width="500" height="300">
</p>