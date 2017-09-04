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
    window.location.href = location;
}

function goHome() {
    window.location.href = "https://boomboompower.github.io/ToggleChat/";
}

function download(version) {
    window.open("dl/ToggleChat-" + version + "-SNAPSHOT.jar");
}