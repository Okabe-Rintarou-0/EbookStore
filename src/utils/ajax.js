export default function postRequest(url, json, callback) {
    console.log(json);
    console.log(JSON.stringify(json));
    let opts = {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include" //to upload cookies from client
    };

    fetch(url, opts)
        .then(response => response.json())
        .then(data => {
            callback(data);
        })
        .catch(error => {
            console.log(error);
        })
}