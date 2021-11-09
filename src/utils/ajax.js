export function postRequest(url, json, callback) {
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

export function putRequest(url, json, callback) {
    let opts = {
        method: "PUT",
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

export function getRequest(url, callback) {
    let opts = {
        method: "GET",
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

export function postRequestForText(url, json, callback) {
    let opts = {
        method: "POST",
        body: JSON.stringify(json),
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: "include" //to upload cookies from client
    };

    fetch(url, opts)
        .then(response => response.text())
        .then(data => {
            callback(data);
        })
        .catch(error => {
            console.log(error);
        })
}