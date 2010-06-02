<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                            <div><button><h3 class="firstStep"><span>Osoba zgłaszająca</span></h3></button></div>
                            <div class="active"><button><h3><span>Opis zgłoszenia</span></h3></button></div>
                            <div><button><h3><span>Krok po kroku...</span></h3></button></div>
                            <div><button><h3><span>Dodatkowe pliki</span></h3></button></div>
                            <div><button><h3 class="lastStep"><span>Podsumowanie</span></h3></button></div>
                        </div>
                    </td>
                    <td id="middlecenter">
                        <form:form action="${formURL}" commandName="hdticket" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="currentPage" value="2"/>
                            <table id="table2" cellspacing="0">
                                <tr><td colspan="2">Przyczyna zgłoszenia (max. 255 znaków):</td></tr>
                                <tr>
                                    <td colspan="2" id="reasonarea">
                                        <form:textarea id="treasonarea" cssClass="textinput" rows="5" path="subject"
                                                       onfocus="borderFocus(this.id)" onblur="borderBlur(this.id)"/>
                                        <form:errors path="subject" cssStyle="color: red"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left">Kategoria</td>
                                    <td align="left" width="35%">Ważność</td>
                                </tr>
                                <tr>
                                    <td>
                                        <form:select id="kategoria" path="ticketCategory" items="${categories}" itemValue="ticketCategoryId" itemLabel="categoryName"/>
                                    </td>
                                    <td>
                                        <form:select id="waznosc" path="ticketPriority" items="${priorities}" itemValue="priorityId" itemLabel="priorityName"/>
                                    </td>
                                </tr>
                                <tr><td colspan="2">Opis zgłoszenia:</td></tr>
                                <tr>
                                    <td id="descarea" colspan="2">
                                        <form:textarea id="tdescarea" cssClass="mceEditor" rows="10" path="description"/>
                                        <form:errors path="description" cssStyle="color: red"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input class="btn" type="submit" name="_target0" value="&laquo; Cofnij"/> <input class="btn" type="submit" name="_target2" value="Dalej &raquo;"/>
                                    </td>
                                </tr>
                            </table>
                        </form:form>
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
