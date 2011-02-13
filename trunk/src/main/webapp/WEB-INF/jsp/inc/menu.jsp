<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<ul class="level1">
    <li class="submenu first"><a href="<c:url value="/desktop/main.html"/>"><span><fmt:message key="main.menu.desktop"/></span></a>
    <li class="submenu first"><a href="javascript:none()"><span><fmt:message key="main.menu.tickets"/></span></a>
        <ul class="level2">
            <li class="first"><a href="<c:url value="/tickets/new.html"/>"><span>Zgłoś problem</span></a></li>
            <li class="first"><a href="<c:url value="/tickets/wizzard.html"/>"><span>Kreator zgłoszenia</span></a></li>
            <%--<li><a href="<c:url value="/newTicket.html"/>"><span>Zgłoś problem</span></a></li>--%>
            <c:if test="${not empty sessionScope.user.filters}">
                <li><a href="#"><span>------------</span></a></li>
                <c:forEach var="f" items="${sessionScope.user.filters}">
                    <li><a href="<c:url value="/tickets/byFilter/${f.id}/list.html"/>"><span><c:out value="${f.name}"/></span></a></li>
                </c:forEach>
            </c:if>
            <li><a><span>------------</span></a></li>
            <li class="last"><a href="<c:url value="/preferences/filters/list.html"/>"><span>Zarządzaj tym menu</span></a></li>
        </ul>
    </li>
    <li class="submenu"><a href="javascript:none();"><span><fmt:message key="main.menu.settings"/></span></a>
        <ul class="level2">
            <li><a href="<c:url value="/preferences/lookAndFeel.html"/>"><span>Wygląd, język...</span></a></li>
            <li><a href="<c:url value="/preferences/displayLists.html"/>"><span>Ustawienia list</span></a></li>
            <li><a href="<c:url value="/preferences/eventNotify.html"/>"><span>Powiadomienia</span></a></li>
            <li><a href="<c:url value="/preferences/personalData.html"/>"><span>Zmiana danych</span></a></li>
            <li><a href="<c:url value="/preferences/filters/list.html"/>"><span>Zarządzanie filtrami</span></a></li>
        </ul>
    </li>
    <auth:check requiredRole="10">
        <li class="submenu"><a href="javascript:none();"><span><fmt:message key="main.menu.management"/></span></a>
            <ul class="level2">
                <li class="first"><a href="<c:url value="/announcements/list.html"/>"><span>Wiadomości</span></a></li>
                <li class="last"><a href="<c:url value="/manage/users/list.html"/>"><span>Użytkownicy</span></a></li>
                <li><a href="<c:url value="/manage/category/list.html"/>"><span>Kategorie zgłoszeń</span></a></li>
                <li class="last"><a href="<c:url value="/manage/kb/categories/all.html"/>"><span>Baza wiedzy - sekcje</span></a></li>
            </ul>
        </li>
    </auth:check>
    <li class="submenu"><a href="javascript:none();"><span><fmt:message key="main.menu.help"/></span></a>
        <ul class="level2">
            <li class="first"><a href="<c:url value="/help/index.html"/>"><span>Spis treści</span></a></li>
            <li><a href="<c:url value="/help/kb/index.html"/>"><span>Baza wiedzy</span></a></li>
            <li class="last"><a href="<c:url value="/help/about.html"/>"><span>O programie</span></a></li>
        </ul>
    </li>
    <li class="submenu last logout"><a href="<c:url value="/logout.html"/>"><span><fmt:message key="main.menu.logout"/></span></a></li>
</ul>
