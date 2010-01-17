<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="ticketFilter">
    Nazwa: <form:input path="name"/><font color="red"><form:errors path="name" /></font><br/>
    Od: <form:input path="beginDate" /><br/>
    Do: <form:input path="endDate" /><br/>
    Kategoria: <form:select path="ticketCategories" items="${ticketCategories}" itemValue="ticketCategoryId" itemLabel="categoryName"/><br/>
    Ważność: <form:select path="ticketPriorities" items="${ticketPriorities}"/><br/>
    Status: <form:select path="ticketStatuses" items="${ticketStatuses}"/><br/>

    Notifiers: <form:select path="notifiers" items="${activeUsers}" itemValue="login"/><br/>
    Saviours <form:select path="saviours" items="${activeUsers}" itemValue="login"/><br/><br/>

    <input type="submit"/>
    
</form:form>