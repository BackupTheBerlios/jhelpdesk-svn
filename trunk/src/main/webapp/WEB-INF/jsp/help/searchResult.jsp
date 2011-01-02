<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<c:forEach var="a" items="${result}">
    <%-- itemy to obiekty Article --%>
    <c:out value="${a.articleId}"/><br/>
    <c:out value="${a.title}"/><br/>
    <c:out value="${a.lead}"/><br/>
    <c:out value="${a.createDate}"/><br/>
    <a href="<c:url value="/help/base/showOne.html?id=${a.articleId}"/>">Cały tekst</a>
    <hr/>
</c:forEach>