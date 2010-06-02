<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<html>
    <head>
        <title><tiles:getAsString name="title"/></title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
        <script language="javascript" type="text/javascript" src="<c:url value="/js/tiny_mce/tiny_mce.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/js/tiny_init.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/calendar.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/lang/calendar-en.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/calendar-setup.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/script.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/tabview.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/borders.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/js/hint.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/hd-base.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/calendar-hd.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/nav.css"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/tabview.css"/>" />
        <style type="text/css">
            body {behavior: url(<c:url value="/themes/hd/css/csshover.htc"/>);}
            #hintbox{position:absolute;top: 0;background-color: yellow;width: 150px;padding: 3px;border:1px solid black;font:normal 11px Verdana;
                      line-height:18px;z-index:100;border-right:3px solid black;border-bottom:3px solid black;visibility: hidden;}
            .hintanchor{font-weight:bold;background:yellow;color:navy;margin:3px 8px;}
            #infoarea textarea {position: fixed;top:0;right:0;z-index:99;width:400px;height:150px;border:5px double #B45E7B;display:none;}
        </style>
    </head>
    <body>
        <div id="pagecontainer">
            <div id="pageheader"><a href="<c:url value="/"/>">jHelpdesk Management System</a></div>
            <div id="pagemenu"><tiles:insertAttribute name="menuPanel" /></div>
            <div id="pagecontent"><tiles:insertAttribute name="content" /></div>
            <div id="pagefooter"><tiles:insertAttribute name="footer" /></div>
        </div>
        <div id="filterbox"><tiles:insertAttribute name="filterForm" ignore="true"/></div>
        <div id="infoarea"><textarea cols="1" rows="2"></textarea></div>
    </body>
</html>
