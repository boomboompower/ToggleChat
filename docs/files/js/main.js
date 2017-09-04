document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('topNav').addEventListener('click', openNav);

	$("#hr").animate({
		width: "30%"
	}, 3000, function() {
		log("-");
	});
});

function downloadPage() {
    window.location = "downloads";
}

function openNav() {
	change("-250px", 2);
    setNav("250px", 2);
}

function closeNav() {
	change("0", 1);
	setNav("0", 1);
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

function log(message) {
	console.log(message);
}