<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="editsection" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="leftcells">
                <div id="pagecontentsubheader"><h3>Edycja artykułu</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <c:url var="url" value="/manage/kb/articles/${formAction}.html"/>
                        <form:form commandName="article" action="${url}">
                            <c:if test="${article.articleId != null}">
                                <form:hidden path="articleId"/>
                            </c:if>
                            <form:hidden path="category"/>
                            <form:hidden path="author"/>
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <td>Tytuł</td>
                                    <td class="lastcol">
                                        <spring:bind path="article.title">
                                            <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
                                                   <c:if test="${not empty status.errorMessage}">class="hintanchor"
                                                       onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
                                                   </c:if>/>
                                        </spring:bind>
                                        <form:errors path="title"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Wstęp</td>
                                    <td class="lastcol">
                                        <spring:bind path="article.lead">
                                            <textarea
                                                name="<c:out value="${status.expression}"/>" rows="4" cols="30"
                                                <c:if test="${not empty status.errorMessage}">class="hintanchor"
                                                    onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
                                                </c:if>><c:out value="${status.value}"/></textarea>
                                        </spring:bind>
                                        <form:errors path="lead"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Treść główna</td>
                                    <td class="lastcol">
                                        <spring:bind path="article.body">
                                            <textarea
                                                name="<c:out value="${status.expression}"/>" rows="4" cols="30"
                                                <c:if test="${not empty status.errorMessage}">class="hintanchor"
                                                    onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
                                                </c:if>><c:out value="${status.value}"/></textarea>
                                        </spring:bind>
                                        <form:errors path="body"/>
                                    </td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="2">
                                        <input type="submit" value="zapisz" class="btn" />
                                    </td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
</div>
