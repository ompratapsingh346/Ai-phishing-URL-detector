document
    .getElementById("checkBtn")
    .addEventListener("click", async () => {

    let [tab] = await chrome.tabs.query({
        active: true,
        currentWindow: true
    });

    let currentURL = tab.url;

    if (currentURL.startsWith("chrome://")) {
    document.getElementById("result").innerHTML =
        "Cannot scan Chrome internal pages";
    return;
   }

    let response = await fetch(
        "http://localhost:8080/scan",
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                url: currentURL
            })
        }
    );

    let data = await response.json();

    document.getElementById("result").innerHTML =
        `
        <p><b>URL:</b> ${currentURL}</p>
        <p><b>Result:</b> ${data.result}</p>
        `;
});