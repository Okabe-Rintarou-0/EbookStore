import {getRequest} from "../utils/ajax";
import {message} from "antd";

export function addToCart(bookId) {
    const url = `http://localhost:8080/cart/add?bookId=${bookId}`;
    getRequest(url, data => {
        if (data.status < 0)
            message.warn(data.message, 1).then(null);
        else
            message.success(data.message, 1).then(null);
    });
}

export function deleteCart(cartId, callback) {
    const url = `http://localhost:8080/cart/delete?cartId=${cartId}`;
    getRequest(url, callback);
}

export function getAllCartItems(callback) {
    const url = 'http://localhost:8080/cart/all';
    getRequest(url, callback);
}

export function searchCartItems(keyword, callback) {
    const url = `http://localhost:8080/cart/search?keyword=${keyword}`;
    getRequest(url, callback);
}