<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="alltickets" class="ticketslist">
    <div id="pagecontentheader"><h2>Zgłoszenia</h2></div>
    <div id="pagecontentsubheader"><h3>Zgłaszanie problemu</h3></div>
    <div id="content">
        <div class="contenttop"></div>
        <div class="contentmiddle">

            <table id="table1" cellspacing="0">
                <tr class="top">
                    <td id="topleft">&nbsp;</td>
                    <td id="topcenter">&nbsp;</td>
                    <td id="topright"><div>&nbsp;</div></td>
                </tr>
                <tr class="middle">
                    <td id="middleleft">
                        <div id="steps">
                            <div class="active"><button><h3 class="firstStep"><span>Osoba zgłaszająca</span></h3></button></div>
                            <div><button><h3><span>Opis zgłoszenia</span></h3></button></div>
                            <div><button><h3><span>Krok po kroku...</span></h3></button></div>
                            <div><button><h3><span>Dodatkowe pliki</span></h3></button></div>
                            <div><button><h3 class="lastStep"><span>Podsumowanie</span></h3></button></div>
                        </div>
                    </td>
                    <td id="middlecenter">
                        <form action="<c:url value="${formURL}"/>" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="currentPage" value="1"/>
                            <table id="table2" cellspacing="0">
                                <tr>
                                    <td class="tabtitle">Login:</td>
                                    <td>
                                        <spring:bind path="hdticket.notifier">
                                            <c:choose>
                                                <c:when test="${readOnly}">
                                                    <c:out value="${status.expression}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input class="textinput" type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"
                                                           <c:if test="${not empty status.errorMessage}">
                                                               class="hintanchor" onMouseover="showhint('<c:out value="${status.errorMessage}"/>', this, event, '150px')"
                                                           </c:if>
                                                           />
                                                    <input type="image" name="_checkLogin" alt="Znajdź" value="true" src="<c:url value="/themes/blue/i/find.gif"/>" style="border: 0" align="top" />
                                                </c:otherwise>
                                            </c:choose>
                                        </spring:bind>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabtitle">Imie:</td>
                                    <td style="width: 240px">
                                        <c:if test="${ not empty hdticket.notifier }">
                                            <c:out value="${hdticket.notifier.firstName}" />
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabtitle">Nazwisko: </td>
                                    <td style="width: 240px">
                                        <c:if test="${ not empty hdticket.notifier }">
                                            <c:out value="${hdticket.notifier.lastName}" />
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabtitle">Telefon:</td>
                                    <td>
                                        <c:if test="${ not empty hdticket.notifier}">
                                            <c:out value="${hdticket.notifier.phone}"/>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabtitle">Telefon kom.:</td>
                                    <td>
                                        <c:if test="${ not empty hdticket.notifier}">
                                            <c:out value="${hdticket.notifier.mobile}"/>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tabtitle">E-mail:</td>
                                    <td>
                                        <c:if test="${ not empty hdticket.notifier}">
                                            <c:out value="${hdticket.notifier.email}"/>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2"><input class="btn" type="submit" name="_target1" value="Dalej &raquo;"/></td>
                                </tr>
                            </table>
                        </form>
                    </td>
                    <td id="middleright">&nbsp;</td>
                </tr>
                <tr class="bottom">
                    <td id="bottomleft">&nbsp;</td>
                    <td id="bottomcenter">&nbsp;</td>
                    <td id="bottomright"><div>&nbsp;</div></td>
                </tr>
            </table>

        </div>
        <div class="contentbottom"></div>
    </div>
</div>