<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/inc/taglibs.jsp" %>
<html>
	<head>
		<title><tiles:getAsString name="title"/></title>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
		<script language="javascript" type="text/javascript" src="<c:url value="/js/tiny_mce/tiny_mce.js"/>"></script>
		<script type="text/javascript">
			tinyMCE.init({ 
				width : "500",
				mode : "textareas",
				editor_selector : "mceEditor",
				theme : "advanced",
				theme_advanced_buttons1 : "bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright, justifyfull,bullist,numlist,undo,redo,link,unlink",
				theme_advanced_buttons2 : "",
				theme_advanced_buttons3 : "",
				theme_advanced_toolbar_location : "top",
				theme_advanced_toolbar_align : "left",
				theme_advanced_path : false,
				extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]"
			});
		</script> 
		<script type="text/javascript">
			/***********************************************
			* Show Hint script- © Dynamic Drive (www.dynamicdrive.com)
			* This notice MUST stay intact for legal use
			* Visit http://www.dynamicdrive.com/ for this script and 100s more.
			***********************************************/
			var horizontal_offset="9px" //horizontal offset of hint box from anchor link
			var vertical_offset="0" //horizontal offset of hint box from anchor link. No need to change.
			var ie=document.all
			var ns6=document.getElementById&&!document.all
			function getposOffset(what, offsettype){
				var totaloffset=(offsettype=="left")? what.offsetLeft : what.offsetTop;
				var parentEl=what.offsetParent;
				while (parentEl!=null){
					totaloffset=(offsettype=="left")? totaloffset+parentEl.offsetLeft : totaloffset+parentEl.offsetTop;
					parentEl=parentEl.offsetParent;
				}
				return totaloffset;
			}
			function iecompattest(){
				return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
			}
			function clearbrowseredge(obj, whichedge){
				var edgeoffset=(whichedge=="rightedge")? parseInt(horizontal_offset)*-1 : parseInt(vertical_offset)*-1
				if (whichedge=="rightedge"){
					var windowedge=ie && !window.opera? iecompattest().scrollLeft+iecompattest().clientWidth-30 : window.pageXOffset+window.innerWidth-40
					dropmenuobj.contentmeasure=dropmenuobj.offsetWidth
					if (windowedge-dropmenuobj.x < dropmenuobj.contentmeasure)
						edgeoffset=dropmenuobj.contentmeasure+obj.offsetWidth+parseInt(horizontal_offset)
				} else {
					var windowedge=ie && !window.opera? iecompattest().scrollTop+iecompattest().clientHeight-15 : window.pageYOffset+window.innerHeight-18
					dropmenuobj.contentmeasure=dropmenuobj.offsetHeight
					if (windowedge-dropmenuobj.y < dropmenuobj.contentmeasure)
						edgeoffset=dropmenuobj.contentmeasure-obj.offsetHeight
				}
				return edgeoffset
			}
			function showhint(menucontents, obj, e, tipwidth){
				if ((ie||ns6) && document.getElementById("hintbox")){
					dropmenuobj=document.getElementById("hintbox")
					dropmenuobj.innerHTML=menucontents
					dropmenuobj.style.left=dropmenuobj.style.top=-500
					if (tipwidth!=""){
						dropmenuobj.widthobj=dropmenuobj.style
						dropmenuobj.widthobj.width=tipwidth
					}
					dropmenuobj.x=getposOffset(obj, "left")
					dropmenuobj.y=getposOffset(obj, "top")
					dropmenuobj.style.left=dropmenuobj.x-clearbrowseredge(obj, "rightedge")+obj.offsetWidth+"px"
					dropmenuobj.style.top=dropmenuobj.y-clearbrowseredge(obj, "bottomedge")+"px"
					dropmenuobj.style.visibility="visible"
					obj.onmouseout=hidetip
				}
			}
			function hidetip(e){
				dropmenuobj.style.visibility="hidden"
				dropmenuobj.style.left="-500px"
			}
			function createhintbox(){
				var divblock=document.createElement("div")
				divblock.setAttribute("id", "hintbox")
				document.body.appendChild(divblock)
			}
			if (window.addEventListener)
				window.addEventListener("load", createhintbox, false)
			else if (window.attachEvent)
				window.attachEvent("onload", createhintbox)
			else if (document.getElementById)
				window.onload=createhintbox
		</script>
		<script type="text/javascript" src="<c:url value="/js/calendar.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/lang/calendar-en.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/calendar-setup.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/script.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/tabview.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/borders.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/hint.js"/>"></script>

		<link href="<c:url value="/themes/hd/css/flavie.ico"/>" rel="shortcut icon" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/hd-base.css"/>" />
		<%-- link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/style.css"/>" / --%>
		<link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/main.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/nav.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/view.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/themes/hd/css/tabview.css"/>" />
		<style type="text/css">
		body {
			behavior: url(<c:url value="/themes/hd/css/csshover.htc"/>);
		}	
		#hintbox{ /*CSS for pop up hint box */
			position:absolute;
			top: 0;
			background-color: lightyellow;
			width: 150px; /*Default width of hint.*/ 
			padding: 3px;
			border:1px solid black;
			font:normal 11px Verdana;
			line-height:18px;
			z-index:100;
			border-right: 3px solid black;
			border-bottom: 3px solid black;
			visibility: hidden;
		}
		.hintanchor{ /*CSS for link that shows hint onmouseover*/
			font-weight: bold;
			background: yellow;
			color: navy;
			margin: 3px 8px;
		}
		#infoarea textarea {
			position: fixed;
			top: 0;
			right: 0;
			z-index: 99;
			width: 400px;
			height: 150px;
			border: 5px double #B45E7B;
			display: none;
		}
		</style>
		<style type="text/css">
			@import url(<c:url value="/themes/hd/css/calendar-hd.css"/>);
		</style>
	</head>
	<body>
		<div id="pagecontainer">
			<div id="pageheader"><a href="<c:url value="/"/>">Helpdesk Management System</a></div>
            <div id="pagemenu"><tiles:insert attribute="menuPanel" /></div>
			<div id="pagecontent"><tiles:insert attribute="content" /></div>
			<div id="pagefooter"><tiles:insert attribute="footer" /></div>
		</div>
		<div id="filterbox"><tiles:insert attribute="filterForm" /></div>
		<div id="infoarea"><textarea></textarea></div>
	</body>
</html>
