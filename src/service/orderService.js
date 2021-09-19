import {getRequest, postRequest} from "../utils/ajax";

export function placeOrder(postData, callback) {
    const url = `http://localhost:8080/order/user/place`;
    postRequest(url, postData, callback);
}

export function getAllOrders(callback) {
    const url = 'http://localhost:8080/order/user/all';
    getRequest(url, callback);
}

export function getAllOrdersForManager(callback) {
    const url = 'http://localhost:8080/order/manager/all';
    getRequest(url, callback);
}

export function searchOrdersForManager(startNEndDates, callback) {
    console.log(startNEndDates);
    const url = 'http://localhost:8080/order/manager/search';
    postRequest(url, startNEndDates, callback);
}

export function searchOrders(startNEndDates, callback) {
    console.log(startNEndDates);
    const url = 'http://localhost:8080/order/user/search';
    postRequest(url, startNEndDates, callback);
}