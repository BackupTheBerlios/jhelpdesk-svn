<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="filter" action="list.html" method="post">
    <div id="boxShadow" class="shadow">
        <div id="box">
            <div class="links" style="padding-top: 2px">
                <span style="font-size: 12px; font-weight: bold; color: white; padding-left: 5px; ">
                    <c:out value="${filter.name}" default="Filtr zmodyfikowany"/></span> |
                <a href="javascript:sweeptoggle('contract')">Ukryj wszystko</a> | 
                <a href="javascript:sweeptoggle('expand')">Pokaż wszystko</a>
            </div>
            <div class="panelsBg">
                <div id="panels">
                    <div id="boxleft">
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc1')"/>Data wprowadzenia
                        </div>
                        <div id="sc1" class="switchcontent">
                            <table>
                                <tr>
                                    <td class="t1"><label for="f_date_start">Od:</label></td>
                                    <td class="t2"><form:input id="f_date_start" path="beginDate" readonly="1"/></td>
                                    <td>
                                        <img alt="" src="<c:url value="/themes/hd/i/cal.gif"/>" id="f_trigger_c"
                                             style="cursor: pointer; border: 0px;" title="Date selector"/>
                                        <script type="text/javascript">
                                            Calendar.setup({
                                                inputField : "f_date_start", ifFormat : "%Y-%m-%d",    button : "f_trigger_c", align : "BR", singleClick : true
                                            });
                                        </script>
                                    </td>
                                </tr>
                            </table>
                            <table>
                                <tr>
                                    <td class="t1"><label for="f_date_end">Do:</label></td>
                                    <td class="t2"><form:input id="f_date_end" path="endDate" readonly="1"/></td>
                                    <td>
                                        <img alt="" src="<c:url value="/themes/hd/i/cal.gif"/>" id="f_trigger_d"
                                             style="cursor: pointer; border: 0px;" title="Date selector"/>
                                        <script type="text/javascript">
                                            Calendar.setup({
                                                inputField : "f_date_end", ifFormat : "%Y-%m-%d", button : "f_trigger_d", align : "BR", singleClick : true
                                            });
                                        </script>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc2')"/>Ważność
                        </div>
                        <div id="sc2" class="switchcontent">
                            <select id="ticketPriorities" name="ticketPriorities" multiple="multiple">
                                <c:forEach items="${priorities}" var="tp">
                                    <option value="${tp}"
                                            <c:forEach items="${filter.ticketPriorities}" var="ftp">
                                                <c:if test="${tp eq ftp}">selected="selected"</c:if>
                                            </c:forEach>
                                            >${tp.priorityName}</option>
                                </c:forEach>
                            </select>
                            <input id="_ticketPriorities" type="hidden" name="_ticketPriorities" value="1"/>
                        </div>
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc3')"/>Kategoria
                        </div>
                        <div id="sc3" class="switchcontent">
                            <select id="ticketCategories" name="ticketCategories" multiple="multiple">
                                <c:forEach items="${categories}" var="tc">
                                    <option value="${tc.ticketCategoryId}"
                                            <c:forEach items="${filter.ticketCategories}" var="ftc">
                                                <c:if test="${tc eq ftc}">selected="selected"</c:if>
                                            </c:forEach>
                                            >${tc.categoryName}</option>
                                </c:forEach>
                            </select>
                            <input id="_ticketCategories" type="hidden" name="_ticketCategories" value="1"/>
                        </div>
                    </div>
                    <div id="boxright">
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc4')"/>Status
                        </div>
                        <div id="sc4" class="switchcontent">
                            <select id="ticketStatuses" name="ticketStatuses" multiple="multiple">
                                <c:forEach items="${statuses}" var="ts">
                                    <option value="${ts}"
                                            <c:forEach items="${filter.ticketStatuses}" var="fts">
                                                <c:if test="${ts eq fts}">selected="selected"</c:if>
                                            </c:forEach>
                                            >${ts.statusName}</option>
                                </c:forEach>
                            </select>
                            <input id="_ticketStatuses" type="hidden" name="_ticketStatuses" value="1"/>
                        </div>
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc5')"/>Zgłaszający
                        </div>
                        <div id="sc5" class="switchcontent">
                            <select id="notifiers" name="notifiers" multiple="multiple">
                                <c:forEach items="${notifiers}" var="n">
                                    <option value="${n.login}"
                                            <c:forEach items="${filter.notifiers}" var="fs">
                                                <c:if test="${fs.userId == n.userId}">selected="selected"</c:if>
                                            </c:forEach>
                                            >${n.fullName}</option>
                                </c:forEach>
                            </select>
                            <input id="_notifiers" type="hidden" name="_notifiers" value="1"/>
                        </div>
                        <div class="headers">
                            <img alt="-/+" src="<c:url value="/commons/i/minus.gif"/>" class="showstate"
                                 onClick="expandcontent(this, 'sc6')"/>Rozwiązujący
                        </div>
                        <div id="sc6" class="switchcontent">
                            <select id="saviours" name="saviours" multiple="multiple">
                                <c:forEach items="${saviours}" var="s">
                                    <option value="${s.login}"
                                            <c:forEach items="${filter.saviours}" var="fs">
                                                <c:if test="${fs.userId == s.userId}">selected="selected"</c:if>
                                            </c:forEach>
                                            >${s.fullName}</option>
                                </c:forEach>
                            </select>
                            <input id="_saviours" type="hidden" name="_saviours" value="1"/>
                        </div>
                    </div>
                </div>
                <div class="buttons">
                    <input type="button" onClick="clearForm(document.forms[0])" value="Wyczyść wszystko"/>
                    <input type="submit" value="Zastosuj filtr"/>
                </div>
            </div>
        </div>
        <input type="hidden" id="_formSent" name="_formSent" value="true"/>
    </div>
</form:form>
