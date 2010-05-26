<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="evennotify" class="preferences">
    <div id="pagecontentheader"><h2>Preferencje</h2></div>
    <form:form commandName="preferences">
        <table cellspacing="0">
            <tr>
                <td class="rightcells">
                    <div id="pagecontentsubheader"><h3>Ustawienia powiadamiania</h3></div>
                    <div id="content">
                        <div class="contenttop"></div>
                        <div class="contentmiddle">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="6" class="lastcol">Powiadomienia o zgłoszeniach</th>
                                </tr>
                                <tr class="numberofitems">
                                    <td class="blank">&nbsp;</td>
                                    <td>Po zdarzeniu</td>
                                    <td>1 raz / dzień</td>
                                    <td>1 raz / tydzień</td>
                                    <td>1 raz / miesiąć</td>
                                    <td class="lastcol">nigdy</td>
                                </tr>
                                <tr>
                                    <td class="blank">przypisanie zgłoszenia  (zawsze na biurku)</td>
                                    <td><form:radiobutton path="ticketAssign" value="1"/></td>
                                    <td><form:radiobutton path="ticketAssign" value="2"/></td>
                                    <td><form:radiobutton path="ticketAssign" value="3"/></td>
                                    <td><form:radiobutton path="ticketAssign" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketAssign" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">przypisanie zgłoszenia komuś innemu (zawsze na biurku)</td>
                                    <td><form:radiobutton path="ticketAssignOther" value="1"/></td>
                                    <td><form:radiobutton path="ticketAssignOther" value="2"/></td>
                                    <td><form:radiobutton path="ticketAssignOther" value="3"/></td>
                                    <td><form:radiobutton path="ticketAssignOther" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketAssignOther" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">zmiana rozwiazującego</td>
                                    <td><form:radiobutton path="ticketReassign" value="1"/></td>
                                    <td><form:radiobutton path="ticketReassign" value="2"/></td>
                                    <td><form:radiobutton path="ticketReassign" value="3"/></td>
                                    <td><form:radiobutton path="ticketReassign" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketReassign" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">odrzucenie zgłoszenia</td>
                                    <td><form:radiobutton path="ticketReject" value="1"/></td>
                                    <td><form:radiobutton path="ticketReject" value="2"/></td>
                                    <td><form:radiobutton path="ticketReject" value="3"/></td>
                                    <td><form:radiobutton path="ticketReject" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketReject" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">rozwiązanie zgłoszenia</td>
                                    <td><form:radiobutton path="ticketResolve" value="1"/></td>
                                    <td><form:radiobutton path="ticketResolve" value="2"/></td>
                                    <td><form:radiobutton path="ticketResolve" value="3"/></td>
                                    <td><form:radiobutton path="ticketResolve" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketResolve" value="5"/>
                                </tr>
                                <tr>
                                    <td class="blank">zamknięcie zgłoszenia</td>
                                    <td><form:radiobutton path="ticketClose" value="1"/></td>
                                    <td><form:radiobutton path="ticketClose" value="2"/></td>
                                    <td><form:radiobutton path="ticketClose" value="3"/></td>
                                    <td><form:radiobutton path="ticketClose" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketClose" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">dodanie/usunięcie plików do zgłoszenia</td>
                                    <td><form:radiobutton path="ticketFileAddOrRemove" value="1"/></td>
                                    <td><form:radiobutton path="ticketFileAddOrRemove" value="2"/></td>
                                    <td><form:radiobutton path="ticketFileAddOrRemove" value="3"/></td>
                                    <td><form:radiobutton path="ticketFileAddOrRemove" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketFileAddOrRemove" value="51"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">dodanie komentarza</td>
                                    <td><form:radiobutton path="ticketCommentAdd" value="1"/></td>
                                    <td><form:radiobutton path="ticketCommentAdd" value="2"/></td>
                                    <td><form:radiobutton path="ticketCommentAdd" value="3"/></td>
                                    <td><form:radiobutton path="ticketCommentAdd" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketCommentAdd" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">zmiana kategorii</td>
                                    <td><form:radiobutton path="ticketCategoryChange" value="1"/></td>
                                    <td><form:radiobutton path="ticketCategoryChange" value="2"/></td>
                                    <td><form:radiobutton path="ticketCategoryChange" value="3"/></td>
                                    <td><form:radiobutton path="ticketCategoryChange" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketCategoryChange" value="5"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">zmiana ważności</td>
                                    <td><form:radiobutton path="ticketPriorityChange" value="1"/></td>
                                    <td><form:radiobutton path="ticketPriorityChange" value="2"/></td>
                                    <td><form:radiobutton path="ticketPriorityChange" value="3"/></td>
                                    <td><form:radiobutton path="ticketPriorityChange" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="ticketPriorityChange" value="5"/></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="6" class="lastcol">Inne związane ze zgłoszeniami</th>
                                </tr>
                                <tr>
                                    <td class="blank">dodanie/usunięcie kategorii zgłaszanych problemów</td>
                                    <td><form:radiobutton path="addDelTicketCategory" value="1"/></td>
                                    <td><form:radiobutton path="addDelTicketCategory" value="2"/></td>
                                    <td><form:radiobutton path="addDelTicketCategory" value="3"/></td>
                                    <td><form:radiobutton path="addDelTicketCategory" value="4"/></td>
                                    <td class="lastcol"><form:radiobutton path="addDelTicketCategory" value="5"/></td>
                                </tr>
                            </table>
                            <br/>
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="6" class="lastcol">Powiadomienia o zmianach w bazie wiedzy</th>
                                </tr>
                                <tr class="numberofitems">
                                    <td class="blank">&nbsp;</td>
                                    <td>Po zdarzeniu</td>
                                    <td>1 raz / dzień</td>
                                    <td>1 raz / tydzień</td>
                                    <td>1 raz / miesiąć</td>
                                    <td class="lastcol">nigdy</td>
                                </tr>
                                <tr>
                                    <td class="blank">dodanie/usunięcie sekcji w bazie wiedzy</td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td class="lastcol"><input type="checkbox"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">dodanie/usunięcie artykułu w bazie wiedzy</td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td class="lastcol"><input type="checkbox"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">zmiany artykułu z bazie wiedzy</td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td><input type="checkbox"/></td>
                                    <td class="lastcol"><input type="checkbox"/></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="4">
                                        <input name="_cancel" type="submit" value="anuluj" class="btn" />
                                        <input name="_save" type="submit" value="zapisz" class="btn" />
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