<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/preferences/filters/save.html" var="formUrl"/>
<form:form action="${formUrl}" commandName="filter" method="post">
    <form:hidden path="id"/>
    <form:hidden path="tfStamp"/>
    <form:hidden path="owner.userId"/>
    <div class="contenttop"></div>
    <div class="contentmiddle">
        <table cellspacing="0" id="table1" class="wide">
            <tbody>
            <tr class="top">
                <td id="topcenter">&nbsp;</td>
                <td id="topright">
                    <div>&nbsp;</div>
                </td>
            </tr>
            <tr class="middle">
                <td id="middlecenter">
                    <c:if test="${not empty message}">${message}</c:if>
                    <p style="background-color: #FFFFDD; margin: 0 0 10px 20px; border: 1px solid #D7D7D7; padding:6px;font-family:Verdana,sans-serif;font-size:12px;">
                        Uwaga! Jeśli w polu wyboru daty nie będzie żadnej wartości to pole to nie będzie
                        brane pod uwagę podczas wyszukiwania <i>(jeśli np. wybrana zostanie tylko wartość <b>Do</b>
                        to stanie się ona górną granicą wyszukiwania i nie będzie dolnej)</i>.<br/>
                        W przypadku list niewybranie żadnej wartości jest jednoznaczne z usunięciem tego pola z
                        kryteriów
                        wyszukiwania. Jeśli jednak zaznaczone zostaną wszystkie wartości to nowe - te, które pojawią się
                        po zbudowaniu filtra - nie będą brane pod uwagę.
                    </p>
                    <table id="table2">
                        <tr>
                            <td class="w100 p20l">Nazwa*:</td>
                            <td><form:input path="name" cssClass="w250"/>
                                <font color="red"><form:errors path="name"/></font>
                            </td>
                        </tr>
                        <tr>
                            <td class="p20l">Opis:</td>
                            <td><form:textarea path="description" cssClass="w250"/></td>
                        </tr>
                        <tr>
                            <td class="p20l">Od:</td>
                            <td><form:input path="beginDate" cssClass="w250"/></td>
                        </tr>
                        <tr>
                            <td class="p20l">Do:</td>
                            <td><form:input path="endDate" cssClass="w250"/></td>
                        </tr>
                        <tr>
                            <td class="p20l">Kategoria:</td>
                            <td>
                                <select id="ticketCategories" class="w255" name="ticketCategories" multiple="multiple">
                                    <c:forEach items="${ticketCategories}" var="tc">
                                        <option value="${tc.ticketCategoryId}"
                                                <c:forEach items="${filter.ticketCategories}" var="ftc">
                                                    <c:if test="${tc eq ftc}">selected="selected"</c:if>
                                                </c:forEach>
                                                >${tc.categoryName}</option>
                                    </c:forEach>
                                </select>
                                <input id="_ticketCategories" type="hidden" name="_ticketCategories" value="1"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="p20l">Ważność:</td>
                            <td>
                                <select id="ticketPriorities" class="w255" name="ticketPriorities" multiple="multiple">
                                    <c:forEach items="${ticketPriorities}" var="tp">
                                        <option value="${tp}"
                                                <c:forEach items="${filter.ticketPriorities}" var="ftp">
                                                    <c:if test="${tp eq ftp}">selected="selected"</c:if>
                                                </c:forEach>
                                                >${tp.priorityName}</option>
                                    </c:forEach>
                                </select>
                                <input id="_ticketPriorities" type="hidden" name="_ticketPriorities" value="1"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="p20l">Status:</td>
                            <td>
                                <select id="ticketStatuses" class="w255" name="ticketStatuses" multiple="multiple">
                                    <c:forEach items="${ticketStatuses}" var="ts">
                                        <option value="${ts}"
                                                <c:forEach items="${filter.ticketStatuses}" var="fts">
                                                    <c:if test="${ts eq fts}">selected="selected"</c:if>
                                                </c:forEach>
                                                >${ts.statusName}</option>
                                    </c:forEach>
                                </select>
                                <input id="_ticketStatuses" type="hidden" name="_ticketStatuses" value="1"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="p20l">Notifiers:</td>
                            <td>
                                <select id="notifiers" class="w255" name="notifiers" multiple="multiple">
                                    <c:forEach items="${notifiers}" var="n">
                                        <option value="${n.login}"
                                                <c:forEach items="${filter.notifiers}" var="fs">
                                                    <c:if test="${fs.userId == n.userId}">selected="selected"</c:if>
                                                </c:forEach>
                                                >${n.fullName}</option>
                                    </c:forEach>
                                </select>
                                <input id="_notifiers" type="hidden" name="_notifiers" value="1"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="p20l">Saviours:</td>
                            <td>
                                <select id="saviours" class="w255" name="saviours" multiple="multiple">
                                    <c:forEach items="${saviours}" var="s">
                                        <option value="${s.login}"
                                                <c:forEach items="${filter.saviours}" var="fs">
                                                    <c:if test="${fs.userId == s.userId}">selected="selected"</c:if>
                                                </c:forEach>
                                                >${s.fullName}</option>
                                    </c:forEach>
                                </select>
                                <input id="_saviours" type="hidden" name="_saviours" value="1"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" class="btn" value="Zapisz"/></td>
                        </tr>
                    </table>
                </td>
                <td id="middleright">&nbsp;</td>
            </tr>
            <tr class="bottom">
                <td id="bottomcenter">&nbsp;</td>
                <td id="bottomright">
                    <div>&nbsp;</div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="contentbottom"></div>
</form:form>
