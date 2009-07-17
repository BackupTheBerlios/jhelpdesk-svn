<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="editsection" class="management">
    <div id="pagecontentheader"><h2>Zarządzanie</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="leftcells">
                <div id="pagecontentsubheader"><h3>Edycja sekcji</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <form action="" method="post">
                            <c:if test="${section.knowledgeSectionId != null}">
                                <spring:bind path="section.knowledgeSectionId">
                                    <input type="hidden" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                                </spring:bind>
                            </c:if>
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <td>Nazwa sekcji</td>
                                    <td class="lastcol">
                                        <spring:bind path="section.sectionName">
                                            <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
                                                   <c:if test="${not empty status.errorMessage}">class="hintanchor"
                                                       onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
                                                   </c:if>/>
                                        </spring:bind>
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
                        </form>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
</div>
