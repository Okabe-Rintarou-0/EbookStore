import {postRequest} from "../utils/ajax";
import {message} from "antd";

export function modifyOrder(orderId, orderReceiver, orderAddress, orderTel, purchaseNumber, callback) {
    const url = `http://localhost:8080/modifyOrder?orderId=${orderId}`;
    let postData;
    if (orderReceiver !== undefined && orderAddress !== undefined && orderTel !== undefined) {
        postData = {
            'orderReceiver': orderReceiver,
            'orderAddress': orderAddress,
            'orderTel': orderTel,
        };
    }
    if (purchaseNumber !== undefined)
        postData = {...postData, 'purchaseNumber': purchaseNumber};
    postRequest(url, postData, callback);
}

export function deleteOrder(orderId, callback) {
    const url = `http://localhost:8080/deleteOrder?orderId=${orderId}`;
    postRequest(url, {}, callback);
}

export function placeOrder(postData, callback) {
    const url = `http://localhost:8080/placeOrder`;
    postRequest(url, postData, callback);
}

export function getAllOrders(callback) {
    const url = 'http://localhost:8080/getAllOrders';
    postRequest(url, {}, callback);
}