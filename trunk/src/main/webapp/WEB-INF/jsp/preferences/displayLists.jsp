<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<div id="displaylist" class="preferences">
    <div id="pagecontentheader"><h2>Preferencje</h2></div>

    <table cellspacing="0">
        <tr>
            <td class="rightcells">

                <div id="pagecontentsubheader"><h3>Ustawienia list</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <form action="" method="post">
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="5" class="lastcol">jakis tytul - ustawienia danych osobowych</th>
                                </tr>
                                <tr class="numberofitems">
                                    <td class="blank">&nbsp;</td>
                                    <td> 10</td>
                                    <td> 25</td>
                                    <td> 50</td>
                                    <td class="lastcol">100</td>
                                </tr>
                                <tr>
                                    <td class="blank">Nowe zgłoszenia</td>
                                    <td><input type="radio" name="x" /></td>
                                    <td><input type="radio" name="x" /></td>
                                    <td><input type="radio" name="x" /></td>
                                    <td class="lastcol"><input type="radio" name="x" /></td>
                                </tr>
                                <tr>
                                    <td class="blank">Zgłoszone przeze mnie</td>
                                    <td><input type="radio" name="s" /></td>
                                    <td><input type="radio" name="s" /></td>
                                    <td><input type="radio" name="s" /></td>
                                    <td class="lastcol"><input type="radio" name="s" /></td>
                                </tr>
                                <tr>
                                    <td class="blank">Przypisane do mnie</td>
                                    <td><input type="radio" name="w" /></td>
                                    <td><input type="radio" name="w" /></td>
                                    <td><input type="radio" name="w" /></td>
                                    <td class="lastcol"><input type="radio" name="w" /></td>
                                </tr>
                                <tr>
                                    <td class="blank">Filtry własne...</td>
                                    <td><input type="radio" name="w" /></td>
                                    <td><input type="radio" name="w" /></td>
                                    <td><input type="radio" name="w" /></td>
                                    <td class="lastcol"><input type="radio" name="w" /></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="5" class="lastcol">Przypisane do mnie</th>
                                </tr>
                                <tr class="numberofitems">
                                    <td class="blank">&nbsp;</td>
                                    <td> 10</td>
                                    <td> 25</td>
                                    <td> 50</td>
                                    <td class="lastcol">100</td>
                                </tr>
                                <tr>
                                    <td class="blank">dfsdfsdfsdf</td>
                                    <td><input type="radio" name="m" /></td>
                                    <td><input type="radio" name="m" /></td>
                                    <td><input type="radio" name="m" /></td>
                                    <td class="lastcol"><input type="radio" name="m" /></td>
                                </tr>
                                <tr>
                                    <td class="blank">dfsdfsdfsdf</td>
                                    <td><input type="radio" name="cm" /></td>
                                    <td><input type="radio" name="cm" /></td>
                                    <td><input type="radio" name="cm" /></td>
                                    <td class="lastcol"><input type="radio" name="cm" /></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0" class="standardtable">
                                <tr>
                                    <th colspan="5" class="lastcol">Przypisane do mnie</th>
                                </tr>
                                <tr class="numberofitems">
                                    <td class="blank">&nbsp;</td>
                                    <td> 10</td>
                                    <td> 25</td>
                                    <td> 50</td>
                                    <td class="lastcol">100</td>
                                </tr>
                                <tr>
                                    <td class="blank">rozwiązane</td>
                                    <td><input type="radio" name="se" /></td>
                                    <td><input type="radio" name="se" /></td>
                                    <td><input type="radio" name="se" /></td>
                                    <td class="lastcol"><input type="radio" name="se" /></td>
                                </tr>
                                <tr>
                                    <td class="blank">nierozwiązane</td>
                                    <td><input type="radio" name="cs" /></td>
                                    <td><input type="radio" name="cs" /></td>
                                    <td><input type="radio" name="cs" /></td>
                                    <td class="lastcol"><input type="radio" name="cs" /></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0" class="standardtable password">
                                <tr>
                                    <th colspan="5" class="lastcol">Zmiana hasła</th>
                                </tr>
                                <tr>
                                    <td>Login</td>
                                    <td class="lastcol">niezmienny</td>
                                </tr>
                                <tr>
                                    <td>Haslo</td>
                                    <td class="lastcol"><input type="password" /></td>
                                </tr>
                                <tr>
                                    <td>Haslo 2</td>
                                    <td class="lastcol"><input type="password" /></td>
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

                        </form>
                    </div>
                    <div class="contentbottom"></div>
                </div>

            </td>
        </tr>
    </table>

</div>
