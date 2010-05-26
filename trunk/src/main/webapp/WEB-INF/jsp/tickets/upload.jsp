<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <body>
        <h1>Dawaj plik</h1>
        <form:form commandName="fileBean" enctype="multipart/form-data">
            <spring:bind path="fileBean.file">
                <input type="file" name="file"/>
            </spring:bind>
            <input type="submit"/>
        </form:form>
        <c:if test="${uploaded}">
            <script type="text/javascript" src="<c:url value="/js/jquery-1.3.1.min.js"/>"></script>
            <script type="text/javascript" src="<c:url value="/js/fancybox/fancybox1.js"/>"></script>
            <script type="text/javascript">
                $(window).load(function(){
                    parent.$.fancybox.close();
                });
            </script>
        </c:if>
    </body>
</html>