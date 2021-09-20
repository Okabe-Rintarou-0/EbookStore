import {getRequest} from "../utils/ajax";
import {message} from "antd";

export function addFavouriteBook(bookId) {
    const url = `http://localhost:8080/favour/add?bookId=${bookId}`;
    getRequest(url, data => {
            if (data.status < 0)
                message.warn(data.message, 1).then(null);
            else
                message.success(data.message, 1).then(null);
        }
    );
}

export function deleteFavouriteBook(bookId, callback) {
    const url = `http://localhost:8080/favour/delete?bookId=${bookId}`;
    getRequest(url, callback);
}

export function moveToCart(bookId, callback) {
    const url = `http://localhost:8080/favour/move?bookId=${bookId}`;
    getRequest(url, callback);
}

export function getFavouriteBooks(callback) {
    const url = `http://localhost:8080/favour/all`;
    getRequest(url, callback);
}