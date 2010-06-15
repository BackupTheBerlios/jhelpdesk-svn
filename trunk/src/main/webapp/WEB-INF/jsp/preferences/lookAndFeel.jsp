<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="lookandfeel" class="preferences">
    <div id="pagecontentheader"><h2>Preferencje</h2></div>
    <form:form modelAttribute="preferences">
    <form:hidden path="id"/>
    <table cellspacing="0">
        <tr>
            <td class="rightcells">
                <div id="pagecontentsubheader"><h3>Wygląd, język, strona startowa</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <table cellspacing="0" class="standardtable">
                            <tr><th colspan="3" class="lastcol">Temat</th></tr>
                            <tr class="options">
                                <td style="width: 186px;">
                                    <form:radiobutton id="t1" path="theme" value="blue"/>
                                    <label for="t1">Blue (standardowy)</label><br />
                                    <div>obrazek</div>
                                </td>
                                <td style="width: 186px;">
                                    <form:radiobutton id="t2" path="theme" value="green" disabled="true"/>
                                    <label for="t2">Green (nie istnieje)</label><br />
                                    <div>obrazek</div>
                                </td>
                                <td class="lastcol" style="width: 188px;">
                                    <form:radiobutton id="t3" path="theme" value="bluestroke"/>
                                    <label for="t3">Bluestroke</label><br />
                                    <div>obrazek</div>
                                </td>
                            </tr>
                        </table>
                        <br />
                        <table cellspacing="0" class="standardtable">
                            <tr><th colspan="5" class="lastcol">Strona startowa (zgłoszenia podać z aktualmych filtrów)</th></tr>
                            <tr class="options">
                                <td style="width: 111px;">
                                    <form:radiobutton id="v1" path="welcomePage" value="desktop"/>
                                    <label for="v1">biurko</label></td>
                                <td style="width: 111px;">
                                    <form:radiobutton id="v2" path="welcomePage" value="tickets"/>
                                    <label for="v2">zgłoszenia</label></td>
                                <td style="width: 111px;">
                                    <form:radiobutton id="v3" path="welcomePage" value="newTicket"/>
                                    <label for="v3">nowe zgłoszenie</label></td>
                                <td class="lastcol" style="width: 112px;">
                                    <form:radiobutton id="v4" path="welcomePage" value="kBase"/>
                                    <label for="v4">baza wiedzy</label></td>
                            </tr>
                        </table>
                        <br />
                        <table cellspacing="0" class="standardtable">
                            <tr><th colspan="4" class="lastcol">Wybór języka interfejsu</th></tr>
                            <tr class="options">
                                <td style="width: 140px;">
                                    <form:radiobutton id="l1" path="preferredLocale" value="pl"/>
                                    <label for="l1">polski</label></td>
                                <td style="width: 140px;">
                                    <form:radiobutton id="l2" path="preferredLocale" value="en"/>
                                    <label for="l2">angielski</label></td>
                                <td style="width: 140px;">
                                    <form:radiobutton id="l3" path="preferredLocale" value="es"/>
                                    <label for="l3">hiszpański</label></td>
                                <td class="lastcol" style="width: 140px;">
                                    <form:radiobutton id="l4" path="preferredLocale" value="pt" disabled="true"/>
                                    <label for="l4">portugalski</label></td>
                            </tr>
                        </table>
                        <br />
                        <table cellspacing="0">
                            <tr>
                                <td colspan="2">
                                    <input name="_cancel" type="submit" value="anuluj" class="btn" />
                                    <input type="submit" value="zapisz" class="btn" />
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="contentbottom"></div>
                </div>
            </td>
        </tr>
    </table>
    </form:form>
</div>