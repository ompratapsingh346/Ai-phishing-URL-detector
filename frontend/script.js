async function scanUrl() {

    const url =
        document.getElementById("urlInput").value;

    const response = await fetch(
        "http://localhost:8080/scan",
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                url: url
            })
        }
    );

    const data = await response.json();

    document.getElementById("result").innerText =
        data.result;
}