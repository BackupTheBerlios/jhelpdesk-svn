function borderFocus(x) {
	document.getElementById(x).style.borderColor = "#47BF4C";
	document.getElementById(x).style.borderStyle = "solid";
	document.getElementById(x).style.borderWidth = "1px";
	document.getElementById(x).style.backgroundColor = "#EBFADE";
}

function borderBlur(x) {
	document.getElementById(x).style.borderColor = "#7F9DB9";
	document.getElementById(x).style.borderStyle = "solid";
	document.getElementById(x).style.borderWidth = "1px";
	document.getElementById(x).style.backgroundColor = "#FFFFFF";
}
