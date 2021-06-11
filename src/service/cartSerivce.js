import {postRequest} from "../utils/ajax";
import {message} from "antd";

export function addToCart(bookId) {
    const url = `http://localhost:8080/addToCart?bookId=${bookId}`;
    postRequest(url, {}, data => {
        if (data.status < 0)
            message.warn(data.message, 1);
        else
            message.success(data.message, 1);
    });
}

export function deleteCart(cartId, callback) {
    const url = `http://localhost:8080/deleteCart?cartId=${cartId}`;
    postRequest(url, {}, callback);
}

export function getAllCartItems(callback) {
    const url = 'http://localhost:8080/getAllCart';
    postRequest(url, {}, callback);
}

export function searchCartItems(keyword, callback) {
    const url = `http://localhost:8080/searchCartItems?keyword=${keyword}`;
    postRequest(url, {}, callback);
}