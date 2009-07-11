function getXYForDesc( eventHD ) {
	var wspolrzedne = { left: 15, top: 10 };
	if(eventHD.pageX) {
		wspolrzedne.left += eventHD.pageX;
		wspolrzedne.top  += eventHD.pageY;
	} else if( eventHD.clientX) {
		wspolrzedne.left += eventHD.clientX + document.body.scrollLeft - document.body.clientLeft;
		wspolrzedne.top  += eventHD.clientY + document.body.scrollTop  - document.body.clientTop;
		if(document.body.parentElement && document.body.parentElement.clientLeft) {
			var rodzicBody = document.body.parentElement;
			wspolrzedne.left += rodzicBody.scrollLeft - rodzicBody.clientLeft;
			wspolrzedne.top  += rodzicBody.scrollTop  - rodzicBody.clientTop;
		}
	}
	return wspolrzedne;

}

function showDesc( eventHD, descId ) {
	var wsp = getXYForDesc( eventHD );
	document.getElementById( descId ).style.position = "absolute";
	document.getElementById( descId ).style.top = wsp.top;
	document.getElementById( descId ).style.left = wsp.left;
	document.getElementById( descId ).style.display = "block";
}
function hideDesc( eventHD, hideId ) {
	//document.getElementById( hideId ).style.visibility = "hidden";
	document.getElementById( hideId ).style.display = "none";
}

function showForm() {
	var el = document.getElementById('filterbox');
	var bt = document.getElementById('filterbutton');
	if( el.style.display != 'block' ) {
		el.style.display = 'block';
	} else {
		el.style.display = 'none';
	}
	bt.blur();
}
function hideForm() {
	var el = document.getElementById('filterbox');
	if( el.style.display != 'none' ) {
		el.style.display = 'none';
	} else {
		el.style.display = 'block';
	}
	bt.blur();
}

function blank() {}
function none() {}
function show(id) {
	var el = document.getElementById( id );
	if( el.style.display != 'block' ) {
		el.style.display = 'block';
	} else {
		el.style.display = 'none';
	}
}









/***********************************************
 * Switch Content script II- © Dynamic Drive (www.dynamicdrive.com)
 * This notice must stay intact for legal use. Last updated April 2nd, 2005.
 * Visit http://www.dynamicdrive.com/ for full source code
 ***********************************************/

var enablepersist="on" //Enable saving state of content structure using session cookies? (on/off)
var memoryduration="7" //persistence in # of days

var contractsymbol='i/minus.gif' //Path to image to represent contract state.
var expandsymbol='i/plus.gif' //Path to image to represent expand state.

/////No need to edit beyond here //////////////////////////

	function getElementbyClass(rootobj, classname) {
		var temparray=new Array();
			var inc = 0;
			var rootlength = rootobj.length;
			for (i = 0; i < rootlength; i++) {
				if (rootobj[i].className == classname)
					temparray[inc++] = rootobj[i];
			}
		return temparray;
	}

function sweeptoggle(ec) {
	var inc = 0;
		while (ccollect[inc]) {
			ccollect[inc].style.display = (ec == "contract" ) ? "none" : "";
				inc++;
		}
	revivestatus();
}


function expandcontent(curobj, cid) {
	if (ccollect.length > 0) {
		document.getElementById(cid).style.display = (document.getElementById(cid).style.display != "none") ? "none" : "";
			curobj.src = (document.getElementById(cid).style.display == "none") ? expandsymbol : contractsymbol;
	}
}

	function revivecontent() {
		selectedItem = getselectedItem();
			selectedComponents = selectedItem.split("|");
			for (i = 0; i < selectedComponents.length - 1; i++)
				document.getElementById(selectedComponents[i]).style.display = "none";
	}

function revivestatus(){
	var inc=0
		while (statecollect[inc]){
			if (ccollect[inc].style.display=="none")
				statecollect[inc].src=expandsymbol
			else
				statecollect[inc].src=contractsymbol
					inc++
		}
}

function get_cookie(Name) { 
	var search = Name + "="
		var returnvalue = "";
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search)
			if (offset != -1) { 
				offset += search.length
					end = document.cookie.indexOf(";", offset);
				if (end == -1) end = document.cookie.length;
				returnvalue=unescape(document.cookie.substring(offset, end))
			}
	}
	return returnvalue;
}

function getselectedItem(){
	if (get_cookie(window.location.pathname) != ""){
		selectedItem=get_cookie(window.location.pathname)
			return selectedItem
	}
	else
		return ""
}

function saveswitchstate(){
	var inc=0, selectedItem=""
		while (ccollect[inc]){
			if (ccollect[inc].style.display=="none")
				selectedItem+=ccollect[inc].id+"|"
					inc++
		}
	if (get_cookie(window.location.pathname)!=selectedItem){ //only update cookie if current states differ from cookie's
		var expireDate = new Date()
			expireDate.setDate(expireDate.getDate()+parseInt(memoryduration))
			document.cookie = window.location.pathname+"="+selectedItem+";path=/;expires=" + expireDate.toGMTString()
	}
}

function do_onload(){
	uniqueidn=window.location.pathname+"firsttimeload"
		var alltags=document.all? document.all : document.getElementsByTagName("*")
		ccollect=getElementbyClass(alltags, "switchcontent")
		statecollect=getElementbyClass(alltags, "showstate")
		if (enablepersist=="on" && get_cookie(window.location.pathname)!="" && ccollect.length>0)
			revivecontent()
				if (ccollect.length>0 && statecollect.length>0)
					revivestatus()
}

if (window.addEventListener)
	window.addEventListener("load", do_onload, false)
else if (window.attachEvent)
	window.attachEvent("onload", do_onload)
else if (document.getElementById)
	window.onload=do_onload

	if (enablepersist=="on" && document.getElementById)
	window.onunload=saveswitchstate


