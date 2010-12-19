<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="displaylist" class="preferences">
    <div id="pagecontentheader"><h2>Preferencje</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="rightcells">
                <div id="pagecontentsubheader"><h3>Ustawienia list</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <form:form modelAttribute="dlPrefs">
                            <form:hidden path="id"/>
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th>Lista/ilość zgłoszeń</th>
                                    <th>10</th>
                                    <th>25</th>
                                    <th>50</th>
                                    <th class="lastcol">100</th>
                                </tr>
                                <tr>
                                    <td class="blank">Listy zgłoszeń</td>
                                    <td><form:radiobutton path="ticketsListSize" value="10"/></td>
                                    <td><form:radiobutton path="ticketsListSize" value="25"/></td>
                                    <td><form:radiobutton path="ticketsListSize" value="50"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketsListSize" value="100"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Lista wiadomości</td>
                                    <td><form:radiobutton path="announcementsListSize" value="10"/></td>
                                    <td><form:radiobutton path="announcementsListSize" value="25"/></td>
                                    <td><form:radiobutton path="announcementsListSize" value="50"/></td>
                                    <td class="lastcol"><form:radiobutton path="announcementsListSize" value="100"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Lista użytkowników</td>
                                    <td><form:radiobutton path="usersListSize" value="10"/></td>
                                    <td><form:radiobutton path="usersListSize" value="25"/></td>
                                    <td><form:radiobutton path="usersListSize" value="50"/></td>
                                    <td class="lastcol"><form:radiobutton path="usersListSize" value="100"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Lista filtrów</td>
                                    <td><form:radiobutton path="filtersListSize" value="10"/></td>
                                    <td><form:radiobutton path="filtersListSize" value="25"/></td>
                                    <td><form:radiobutton path="filtersListSize" value="50"/></td>
                                    <td class="lastcol"><form:radiobutton path="filtersListSize" value="100"/></td>
                                </tr>
                            </table>
                            <br/>
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="2">
                                        <input name="_cancel" type="submit" value="anuluj" class="btn" />
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
