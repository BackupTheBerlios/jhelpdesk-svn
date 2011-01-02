<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showallarticles" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="rightcells">
                <div id="pagecontentsubheader"><h3>Baza wiedzy - artykuły</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0">
                            <tr>
                                <td>
                                    <a href="<c:url value="/manage/kb/category/${categoryId}/articles/new.html"/>"
                                       class="btn">Dodaj nowy artykuł</a>
                                </td>
                            </tr>
                        </table>
                        <c:if test="${not empty articles}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Lp.</th>
                                    <th>Tytuł</th>
                                    <th>Autor</th>
                                    <th>Data</th>
                                    <th colspan="4" class="lastcol">Akcje</th>
                                </tr>
                                <c:forEach var="article" items="${articles}">
                                    <tr>
                                        <td>.</td>
                                        <td><c:out value="${article.title}"/></td>
                                        <td><c:out value="${article.author}"/></td>
                                        <td><c:out value="${article.createDate}"/></td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/kb/category/${categoryId}/articles/${article.articleId}/edit.html"/>">Edit</a>
                                        </td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/kb/category/${categoryId}/articles/${article.articleId}/remove.html"/>">Del</a>
                                        </td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/kb/article/up.html?articleId=${article.articleId}"/>">Up</a>
                                        </td>
                                        <td class="lastcol ticketEdit">
                                            <a href="<c:url value="/manage/kb/article/down.html?articleId=${article.articleId}"/>">Down</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
</div>
