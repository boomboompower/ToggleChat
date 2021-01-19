/*
 *     Copyright (C) 2021 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('topNav').addEventListener('click', openNav);

	$("#hr").animate({
		width: "30%"
	}, 3000, function() {
		log("-");
	});
});

function openPage(pageType) {
    var location = "https://boomboompower.github.io/ToggleChat/";

    if (pageType == "downloads") {
        location = "https://boomboompower.github.io/ToggleChat/downloads";
    } else if (pageType == "toggles") {
        location = "https://boomboompower.github.io/ToggleChat/toggles";
    }
    window.location.href = location;
}

function downloadPage() {
    window.location = "https://boomboompower.github.io/ToggleChat/downloads";
}

function sourcePage() {
    window.open("https://github.com/boomboompower/ToggleChat");
}

function forumThread() {
    window.open("https://hypixel.net/threads/997547");
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