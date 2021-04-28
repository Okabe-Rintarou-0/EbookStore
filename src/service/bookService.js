import postRequest from "../utils/ajax";

export function getBooks(callback) {
    const url = 'http://localhost:8080/getBooks';
    postRequest(url, {}, callback);
}

export function getBookById(bookId, callback) {
    const url = `http://localhost:8080/getBookById?bookId=${Number(bookId)}`;
    postRequest(url, {}, callback);
}