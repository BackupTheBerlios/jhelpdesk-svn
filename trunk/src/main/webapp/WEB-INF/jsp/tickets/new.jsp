<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>formularz nowego zgłoszenia</h1>

<form:form commandName="ticket">
    <label for="zglaszacz">Zgłaszający:</label>
    <form:input id="zglaszacz" path="notifier"/><font color="red"><form:errors path="notifier" /></font><br/>

    <label for="kategoria">Kategoria:</label>
    <form:select id="kategoria" path="ticketCategory" items="${categories}" itemValue="ticketCategoryId" itemLabel="categoryName"/><br/>
    <label for="waznosc">Ważność:</label>
    <form:select id="waznosc" path="ticketPriority" items="${priorities}" itemValue="priorityId" itemLabel="priorityName"/><br/>
    <label for="przyczyna">Przyczyna:</label>
    <form:textarea id="przyczyna" path="subject" rows="6" cols="40" />
    <font color="red"><form:errors path="subject" /></font><br/>

    <label for="opis">Opis zgłoszenia:</label>
    <form:textarea id="opis" path="description" rows="6" cols="40" />
    <font color="red"><form:errors path="description" /></font><br/>

    <label for="step_by_step">Kroki by powtórzyć:</label>
    <form:textarea id="step_by_step" path="stepByStep" rows="6" cols="40" /><br/>

    <span>Zarządzanie dodatkowymi plikami</span><br/>
    <input type="submit" value="Zapisz"/>
</form:form>
