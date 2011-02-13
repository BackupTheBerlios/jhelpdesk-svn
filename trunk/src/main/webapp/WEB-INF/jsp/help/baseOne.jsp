<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<c:choose>
    <c:when test="${article != null}">
        ${article.title}
        <br/><br/>
        ${article.lead}
        <br/><br/>
        ${article.body}
        <hr/>
        <h3>Powiązane zgłoszenia</h3>
        <ol>
            <c:forEach items="${article.associatedTickets}" var="ticket">
                <li><a href="<c:url value="/ticketDetails.html?ticketId=${ticket.ticketId}"/>"><c:out
                        value="${ticket.subject}"/></a></li>
            </c:forEach>
        </ol>
        <hr/>
        <h3>Komentarze</h3>
        <c:forEach items="${article.comments}" var="comment">
            <b><c:out value="${comment.title}"/></b><br/>
            <c:out value="${comment.body}"/><br/><br/>
        </c:forEach>
        <hr/>
        <h4>Dodaj komentarz</h4>

        <form action="" method="post">
            <input type="hidden" name="articleId" value="${article.articleId}">
            <textarea rows="4" cols="40"></textarea><br/>
            <input type="submit" value="Dodaj komentarz"/>
        </form>
    </c:when>
    <c:otherwise>${message}</c:otherwise>
</c:choose>

