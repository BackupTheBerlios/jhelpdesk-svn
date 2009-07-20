<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>

<c:out value="${announcement.createDate}"/>
<br/><br/>
<c:out value="${announcement.title}"/>
<br/><br/>
<c:out value="${announcement.lead}"/>
<br/><br/>
<c:out value="${announcement.body}"/>
