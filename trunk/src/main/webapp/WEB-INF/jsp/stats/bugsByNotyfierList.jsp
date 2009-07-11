<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<span>
	<a href="<c:url value="/stats/bugs/notifyier.html?letter=A"/>">A</a>
	<c:forTokens items="B,C,Ć,D,E,F,G,H,I,J,K,L,Ł,M,N,O,Ó,P,R,S,Ś,T,U,V,W,X,Y,Z,Ź,Ż" delims="," var="letter">
		| <a href="<c:url value="/stats/bugs/notifyier.html?letter=${letter}"/>" <c:if test="${param.letter eq letter}">class="current"</c:if> ><c:out value="${letter}"/></a>
	</c:forTokens>
	| <a href="<c:url value="/stats/bugs/notifyier.html?letter=."/>">wszystkie</a>
	<hr/>
	<c:forEach items="${users}" var="user">
		<a href="<c:url value="/stats/bugs/notifyier.html?stats=${user.userId}"/>"><c:out value="${user}"/></a><br/>
	</c:forEach>
</span>