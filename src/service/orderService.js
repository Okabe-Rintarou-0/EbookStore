import {postRequest} from "../utils/ajax";

export function modifyOrder(orderId, orderReceiver, orderAddress, orderTel, callback) {
    const url = `http://localhost:8080/modifyOrder?orderId=${orderId}`;
    let postData = {
        'orderReceiver': orderReceiver,
        'orderAddress': orderAddress,
        'orderTel': orderTel,
    };
    postRequest(url, postData, callback);
}

export function addToCart(bookId, callback) {
    const url = `http://localhost:8080/addToCart?bookId=${bookId}`;
    postRequest(url, {}, callback);
}

export function deleteOrder(orderId, callback) {
    const url = `http://localhost:8080/deleteOrder?orderId=${orderId}`;
    postRequest(url, {}, callback);
}

export function placeOrder(orderId, callback) {
    const url = `http://localhost:8080/placeOrder?orderId=${orderId}`;
    postRequest(url, {}, callback);
}

export function getAllOrders(callback) {
    const url = 'http://localhost:8080/getAllOrders';
    postRequest(url, {}, callback);
}