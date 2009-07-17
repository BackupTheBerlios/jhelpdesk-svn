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
                                    <a class="btn" href="<c:url value="/manage/knowledge/article/edit.html"/>">Dodaj nowy artykuł</a>
                                </td>
                            </tr>
                        </table>
                        <c:if test="${not empty articles}">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Lp.</th>
                                    <th>Tytul</th>
                                    <th>Autor</th>
                                    <th>Data</th>
                                    <th colspan="4" class="lastcol">Akcje</th>
                                </tr>
                                <c:forEach var="article" items="${articles}">
                                    <tr>
                                        <td>.</td>
                                        <td><c:out value="${article.title}"/></td>
                                        <td>jasio:)</td>
                                        <td>2005/12/23</td>
                                        <td class="bugEdit">
                                            <a href="<c:url value="/manage/knowledge/article/edit.html?articleId=${article.knowledgeId}"/>">Edit</a>
                                        </td>
                                        <td class="bugEdit">
                                            <a href="<c:url value="/manage/knowledge/article/remove.html?articleId=${article.knowledgeId}"/>">Del</a>
                                        </td>
                                        <td class="bugEdit">
                                            <a href="<c:url value="/manage/knowledge/article/up.html?articleId=${article.knowledgeId}"/>">Up</a>
                                        </td>
                                        <td class="lastcol bugEdit">
                                            <a href="<c:url value="/manage/knowledge/article/down.html?articleId=${article.knowledgeId}"/>">Down</a>
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
