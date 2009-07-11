<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<span>
	<a href="<c:url value="/stats/saviour/showAll.html?letter=0-5"/>">A-E</a> |
	<a href="<c:url value="/stats/saviour/showAll.html?letter=6-10"/>">F-J</a> |
	<a href="<c:url value="/stats/saviour/showAll.html?letter=11-15"/>">K-N</a> |
	<a href="<c:url value="/stats/saviour/showAll.html?letter=16-22"/>">O-T</a> |
	<a href="<c:url value="/stats/saviour/showAll.html?letter=23-30"/>">U-Ż</a> |
	
	<a href="<c:url value="/stats/saviour/showAll.html?letter=0-30"/>">wszystkie</a>
	<hr/>
	<c:forEach items="${users}" var="user">
		<a href="<c:url value="/stats/saviour/stats.html?saviour=${user.userId}"/>"><c:out value="${user}"/></a><br/>
	</c:forEach>
</span>