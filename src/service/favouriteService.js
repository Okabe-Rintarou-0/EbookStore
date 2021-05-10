import {postRequest} from "../utils/ajax";

export function addFavouriteBook(bookId, callback) {
    const url = `http://localhost:8080/addFavouriteBook?bookId=${bookId}`;
    postRequest(url, {}, callback);
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