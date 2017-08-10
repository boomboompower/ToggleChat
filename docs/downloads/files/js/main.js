document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('topNav').addEventListener('click', openNav);
	document.getElementById('back').addEventListener('click', goBack);

	$("#hr").animate({
		width: "20%"
	}, 3000, function() {
		log("-");
	});
});

function openNav() {
	change("-250px", 2);
    setNav("250px", 2);
	
	document.getElementById("navBar").style.borderLeftWidth = "2px";
	document.getElementById("navBar").style.borderLeftStyle = "dashed";
}

function closeNav() {
	change("0", 1);
	setNav("0", 1);
	
	document.getElementById("navBar").style.borderLeftWidth = "0px";
	document.getElementById("navBar").style.borderLeftStyle = "none";
}

function change(width, time) {
	$("#main").animate({
		marginLeft: width
	}, time * 10);
}

function setNav(setWidth, time) {
	$("#navBar").animate({
		width: setWidth
	}, time * 10);
}

function download(version) {
    window.open("dl/ToggleChat-" + version + "-SNAPSHOT.jar");
}

function log(message) {
	console.log(message);
}

function goBack() {
    history.back();
}