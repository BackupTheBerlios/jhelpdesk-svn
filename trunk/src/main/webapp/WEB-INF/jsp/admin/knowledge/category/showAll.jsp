<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="showallsections" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="rightcells">
                <div id="pagecontentsubheader"><h3>Baza wiedzy - sekcje</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0">
                            <tr>
                                <td>
                                    <a class="btn" href="<c:url value="/manage/knowledge/category/edit.html"/>">Dodaj nową sekcję</a>
                                </td>
                            </tr>
                        </table>
                        <c:if test="${not empty categories}">
                            <br />
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th class="rowNumber">Lp.</th>
                                    <th>Nazwa sekcji</th>
                                    <th class="artnumber">Ilość art.</th>
                                    <th colspan="5" class="lastcol">Akcje</th>
                                </tr>
                                <c:forEach var="category" items="${categories}">
                                    <tr>
                                        <td class="rowNumber">.</td>
                                        <td><c:out value="${category.categoryName}"/></td>
                                        <td class="artnumber"><c:out value="${category.articlesCount}"/></td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/knowledge/article/showAll.html?categoryId=${category.articleCategoryId}"/>">Art</a>
                                        </td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/knowledge/category/edit.html?categoryId=${category.articleCategoryId}"/>">Edit</a>
                                        </td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/knowledge/category/remove.html?categoryId=${category.articleCategoryId}"/>">Del</a>
                                        </td>
                                        <td class="ticketEdit">
                                            <a href="<c:url value="/manage/knowledge/category/up.html?categoryId=${category.articleCategoryId}"/>">Up</a>
                                        </td>
                                        <td class="lastcol ticketEdit">
                                            <a href="<c:url value="/manage/knowledge/category/down.html?categoryId=${category.articleCategoryId}"/>">Down</a>
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
