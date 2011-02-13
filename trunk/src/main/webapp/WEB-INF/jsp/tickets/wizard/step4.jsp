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
                            <div><button><h3 class="firstStep"><span>Osoba zgłaszająca</span></h3></button></div>
                            <div><button><h3><span>Opis zgłoszenia</span></h3></button></div>
                            <div><button><h3><span>Krok po kroku...</span></h3></button></div>
                            <div class="active"><button><h3><span>Dodatkowe pliki</span></h3></button></div>
                            <div><button><h3 class="lastStep"><span>Podsumowanie</span></h3></button></div>
                        </div>
                    </td>
                    <td id="middlecenter">
                        <span>
                            <script type="text/javascript" src="<c:url value="/js/jquery-1.3.1.min.js"/>"></script>
                                <script type="text/javascript">
                                    $(document).ready(function() {
                                        $("a.group").fancybox({
                                            'hideOnContentClick': false,
                                            'hideOnOverlayClick': false,
                                            'frameWidth' : 320,
                                            'frameHeight' : 150
                                        });
                                    });
                                </script>
                                <script type="text/javascript" src="<c:url value="/js/jquery.easing.1.3.js"/>"></script>
                                <link rel="stylesheet" type="text/css" href="<c:url value="/js/fancybox/fancybox.css"/>"/>
                                <script type="text/javascript" src="<c:url value="/js/fancybox/fancybox1.js"/>"></script>
                                <a class="group iframe"
                                   href="<c:url value="/tickets/uploadFile.html?ticketstamp=${ticket.ticketstamp}"/>">Dołącz plik</a>
                        </span>
                        <c:forEach var="file" items="${hdticket.addFilesList}" varStatus="status">
                        <ul>
                            <td class="tdnumber"><c:out value="${status.count}"/></td>
                            <td class="tdfile"><c:out value="${file.originalFileName}"/></td>
                            <td class="tdsize"><c:out value="${file.fileSize}"/></td>
                            <td class="tdlink lastcol"><input type="image" name="x" src="<c:url value="/themes/blue/i/delete.gif"/>" /></td>
                            <ul>
                                </c:forEach>
                        <form action="<c:url value="${formURL}"/>" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="currentPage" value="4"/>
                            <table class="upload" cellspacing="0">
                                <tr>
                                    <td style="color: red; font-weight: bold; font-size: 15px">Do obczajenia jeszcze ten mechanizm!</td>
                                </tr>
                                <tr class="buttons">
                                    <td>
                                        <input class="btn" type="submit" name="_target2" value="&laquo; Cofnij"/>
                                        <input class="btn" type="submit" name="_target4" value="Dalej &raquo;"/>
                                    </td>
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
