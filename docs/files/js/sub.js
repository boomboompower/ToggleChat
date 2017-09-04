document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('home').addEventListener('click', goHome);
});

function openPage(pageType) {
    var location = "https://boomboompower.github.io/ToggleChat/";

    if (pageType == "downloads") {
        location = "https://boomboompower.github.io/ToggleChat/downloads";
    } else if (pageType == "toggles") {
        location = "https://boomboompower.github.io/ToggleChat/toggles";
    }
    window.location.assign(location);
}

function goHome() {
    window.location.assign("https://boomboompower.github.io/ToggleChat/");
}