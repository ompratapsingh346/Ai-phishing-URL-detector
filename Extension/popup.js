document.getElementById("scanBtn")
.addEventListener("click", async () => {

    const tabs =
        await chrome.tabs.query({
            active:true,
            currentWindow:true
        });

    const currentUrl =
        tabs[0].url;

    const response =
        await fetch(
            "http://localhost:8080/scan",
            {

                method:"POST",

                headers:{
                    "Content-Type":"application/json"
                },

                body:JSON.stringify({

                    url:currentUrl
                })
            }
        );

    const data =
        await response.json();

    const resultBox =
        document.getElementById("resultBox");

    let reasonsHtml = "";

    data.reasons.forEach(reason => {

        reasonsHtml += `<li>${reason}</li>`;
    });

    resultBox.innerHTML = `

        <h2 class="${
            data.result === "Safe"
            ? "safe"
            : "phishing"
        }">

            ${data.result}

        </h2>

        <p>

            <strong>Risk Score:</strong>

            ${data.riskScore}

        </p>

        <p>

            <strong>URL:</strong>

            ${data.url}

        </p>

        <p>

            <strong>Reasons:</strong>

        </p>

        <ul>

            ${reasonsHtml}

        </ul>
    `;
});