<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp"%>
<ul class="level1">
	<li class="submenu first"><a href="<c:url value="/desktop/main.html"/>"><span><fmt:message key="label.desktop"/></span></a>
	<li class="submenu first"><a href="javascript:none()"><span><fmt:message key="label.zgloszenia"/></span></a>
	<ul class="level2">
		<li class="first"><a href="<c:url value="/newTicket.html"/>"><span>Zgłoś problem</span></a></li>
		<li><a href="<c:url value="/showNewTickets.html"/>"><span>Nieprzypisane</span></a></li>
		<li><a href="<c:url value="/showTickets.html"/>"><span>Wszystkie</span></a></li>
		<li><a href="<c:url value="/showTicketsNotifiedByMe.html"/>"><span>Zgłoszene przez mnie</span></a></li>
		<li class="last"><a href="<c:url value="/showTicketsAssignedToMe.html"/>"><span>Przypisane do mnie</span></a></li>
	</ul>
	</li>
	<!-- 
	<li class="submenu"><a href="javascript:none();"><span><fmt:message key="label.stats"/></span></a>
	<ul class="level2">
		<li class="submenu first"><a href="javascript:none();"><span>Zgłoszenia...</span></a>
		<ul class="level3">
			<li class="first"><a href="<c:url value="/stats/tickets/category.html"/>"><span>w/g kategorii</span></a></li>
			<li><a href="<c:url value="/stats/tickets/priority.html"/>"><span>w/g ważności</span></a></li>
			<li class="last"><a href="<c:url value="/stats/tickets/notifyier.html"/>"><span>w/g zgłaszającego</span></a></li>
		</ul>
		</li>
		<li><a href="<c:url value="/stats/saviour/showAll.html"/>"><span>Rozwiązujący...</span></a></li>
		<li><a href="<c:url value="/stats/notifier/showAll.html"/>"><span>Zgłaszający...</span></a></li>
		<li class="submenu last"><a href="<c:url value="/stats/myStats.html"/>"><span>Moje statystyki</span></a></li>
	</ul>
	</li>
	 -->
	<li class="submenu"><a href="javascript:none();"><span><fmt:message key="label.preferences"/></span></a>
	<ul class="level2">
		<li><a href="<c:url value="/preferences/lookAndFeel.html"/>"><span>Wygląd, język...</span></a></li>
		<li><a href="<c:url value="/preferences/displayLists.html"/>"><span>Ustawienia list</span></a></li>
		<li><a href="<c:url value="/preferences/eventNotify.html"/>"><span>Powiadomienia</span></a></li>
		<li><a href="<c:url value="/preferences/personalData.html"/>"><span>Zmiana danych</span></a></li>
	</ul>
	</li>
	<auth:check requiredRole="10">
	<li class="submenu"><a href="javascript:none();"><span><fmt:message key="label.management"/></span></a>
	<ul class="level2">
		<li class="first"><a href="<c:url value="/manage/information/showAll.html"/>"><span>Wiadomości</span></a></li>
		<li class="last"><a href="<c:url value="/manage/users/showAll.html"/>"><span>Użytkownicy</span></a></li>
		<li><a href="<c:url value="/manage/category/showAll.html"/>"><span>Kategorie zgłoszeń</span></a></li>
		<li class="last"><a href="<c:url value="/manage/knowledge/section/showAll.html"/>"><span>Baza wiedzy - sekcje</span></a></li>
		<li class="last"><a href="<c:url value="/manage/knowledge/article/showAll.html"/>"><span>Baza wiedzy - artykuły</span></a></li>
	</ul>
	</li>
	</auth:check>
	<li class="submenu"><a href="javascript:none();"><span><fmt:message key="label.help"/></span></a>
	<ul class="level2">
		<li class="first"><a href="<c:url value="/help/index.html"/>"><span>Spis treści</span></a></li>
		<li><a href="<c:url value="/help/base.html"/>"><span>Baza wiedzy</span></a></li>
		<li class="last"><a href="<c:url value="/help/about.html"/>"><span>O programie</span></a></li>
	</ul>
	</li>
	<li class="submenu last logout"><a href="<c:url value="/logout.html"/>"><span>Wyloguj</span></a></li>
</ul>
