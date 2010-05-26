<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<a href="<c:url value="/preferences/filters/new.html"/>">Dodaj nowy filtr</a>
<ul>
    <c:forEach items="${filters}" var="f">
        <li>
            <a href="<c:url value="/preferences/filters/${f.id}/details.html"/>"><c:out value="${f.name}"/></a>
            <a href="<c:url value="/preferences/filters/${f.id}/edit.html"/>">Edytuj</a>
            <a href="<c:url value="/preferences/filters/${f.id}/delete.html"/>">Usuń</a>
        </li>
    </c:forEach>
</ul>