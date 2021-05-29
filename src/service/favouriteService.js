import {postRequest} from "../utils/ajax";
import {message} from "antd";

export function addFavouriteBook(bookId) {
    const url = `http://localhost:8080/addFavouriteBook?bookId=${bookId}`;
    postRequest(url, {}, data => {
            if (data.status < 0)
                message.warn(data.message, 1);
            else
                message.success(data.message, 1);
        }
    );
}

export function deleteFavouriteBook(bookId, callback) {
    const url = `http://localhost:8080/deleteFavouriteBook?bookId=${bookId}`;
    postRequest(url, {}, callback);
}

export function moveToCart(bookId, callback) {
    const url = `http://localhost:8080/moveToCart?bookId=${bookId}`;
    postRequest(url, {}, callback);
}

export function getFavouriteBooks(callback) {
    const url = `http://localhost:8080/getFavouriteBooks`;
    postRequest(url, {}, callback);
}