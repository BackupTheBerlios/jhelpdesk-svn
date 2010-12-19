<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="personaldata" class="preferences">
    <div id="pagecontentheader"><h2>Preferencje</h2></div>
    <table cellspacing="0">
        <tr>
            <td class="leftcells">
                <div id="pagecontentsubheader"><h3>Ustawienia danych osobowych</h3></div>
                <div id="content">
                    <div class="contenttop"></div>
                    <div class="contentmiddle">
                        <form:form modelAttribute="personalData">
                            <table cellspacing="0" class="standardtable">
                                <tr><th colspan="2" class="lastcol">Dane osobowe</th></tr>
                                <tr>
                                    <td class="blank">Imie</td>
                                    <td class="lastcol"><form:input path="firstName"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Nazwisko</td>
                                    <td class="lastcol"><form:input path="lastName"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Email</td>
                                    <td class="lastcol"><form:input path="email"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Telefon</td>
                                    <td class="lastcol"><form:input path="phone"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Mobile</td>
                                    <td class="lastcol"><form:input path="mobile"/></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="2">
                                        <input name="_cancel" type="submit" value="Anuluj" class="btn" />
                                        <input type="submit" value="Zapisz" class="btn" />
                                    </td>
                                </tr>
                            </table>
                        </form:form>
                        <br />
                        <form action="../password/change.html" method="post">
                            <table cellspacing="0" class="standardtable">
                                <tr><th colspan="2" class="lastcol">Zmiana hasła</th></tr>
                                <tr>
                                    <td class="blank">Login</td>
                                    <td class="lastcol">${personalData.login}</td>
                                </tr>
                                <tr>
                                    <td class="blank">Hasło</td>
                                    <td class="lastcol"><input type="password" name="password" maxlength="128"/></td>
                                </tr>
                                <tr>
                                    <td class="blank">Powtórz hasło</td>
                                    <td class="lastcol"><input type="password" name="repeated" maxlength="128"/></td>
                                </tr>
                            </table>
                            <br />
                            <table cellspacing="0">
                                <tr>
                                    <td colspan="2">
                                        <input name="_cancel" type="submit" value="Anuluj" class="btn" />
                                        <input type="submit" value="Zmień hasło" class="btn" />
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