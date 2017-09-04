document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('home').addEventListener('click', goHome);
});

function goHome() {
    window.location.href = "https://boomboompower.github.io/ToggleChat/";
}

function download(version) {
    window.open("dl/ToggleChat-" + version + "-SNAPSHOT.jar");
}