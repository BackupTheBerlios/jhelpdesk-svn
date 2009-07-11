function jGetXY( oEvent ) {

	var position = { x: 0, y: 0, xx: 0, yy: 0 };
	var oEvent = ( oEvent ) ? oEvent : window.event;
	var oTarget = ( oEvent.target ) ? oEvent.target : oEvent.srcElement;
	
	if ( oEvent.pageX || oEvent.pageY ) {
		position.x = oEvent.pageX;
		position.y = oEvent.pageY;
		position.yy = oEvent.clientY;
	} else if ( oEvent.clientX || oEvent.clientY ) {
		position.x = oEvent.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
		position.y = oEvent.clientY + document.body.scrollTop + document.documentElement.scrollTop;
		position.yy = oEvent.clientY;
	}

	iTimeoutId = setTimeout( function( ) {
		jShowDesc( oEvent, position, oTarget );
	}, 1000 );

}

function jShowDesc( oEvent, position, oTarget ) {

	var oLink = oTarget.nextSibling;
	var oHint = document.getElementById( "hint" );
	var oLinkText = ( oLink.textContent ) ? oLink.textContent : oLink.innerText;
	var oHintText = document.getElementById( "hintmiddle" );
	oHintText.innerHTML = oLinkText;
	
	var M = 15;
	var h = 150;
	var H = window.innerHeight ? window.innerHeight : screen.availHeight;
	
	var oArea = document.getElementById( "infoarea" ).firstChild;
	oArea.innerHTML = "X: " + position.x + "\nY: " + position.y + "\ntarget: " + oTarget.nodeName;
	oArea.innerHTML += "\nh: " + h;
	oArea.innerHTML += "\nH: " + H;
	oArea.innerHTML += "\nxx: " + position.xx;
	oArea.innerHTML += "\nyy: " + position.yy;
	
	var oArrowB = document.getElementById( "hintlb" );
	var oArrowT = document.getElementById( "hintlt" );
	oHint.style.color = "red";
	if ( position.yy > h ) {
		oHint.style.top = position.y - h - 10 + "px";
		oArrowB.style.display = "block";
		oArrowB.style.top = position.y - 95 + "px";
		oArrowB.style.left = position.x + M + "px";
	} else if ( H - position.yy > h ) {
		oHint.style.top = position.y + 5 + "px";
		oArrowT.style.display = "block";
		oArrowT.style.top = position.y + M + "px";
		oArrowT.style.left = position.x + M + "px";
	}

	oArea.innerHTML += "\ny: " + oHint.style.top;
	oHint.style.left = position.x + M + "px";
	oHint.style.display = "block";
}

function jHideDesc( oEvent ) {
	clearTimeout( iTimeoutId );
	var oHint = document.getElementById("hint");
	oHint.style.display = "none";
	var oArrowB = document.getElementById( "hintlb" );
	var oArrowT = document.getElementById( "hintlt" );
	oArrowB.style.display = "none";
	oArrowT.style.display = "none";
}

