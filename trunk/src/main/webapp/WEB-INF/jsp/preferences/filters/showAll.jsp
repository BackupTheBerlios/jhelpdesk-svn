<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<a href="<c:url value="/preferences/filters/new.html"/>">Dodaj nowy filtr</a>
<ul>
    <c:forEach items="${filters}" var="f">
        <li><a href="<c:url value="/preferences/filters/showFilter.html?id=${f.ticketFilterId}"/>"><c:out value="${f.name}"/></a></li>
    </c:forEach>
</ul>