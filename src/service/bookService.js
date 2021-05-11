import {postRequest, postRequestForText} from "../utils/ajax";

export function getBooks(callback) {
    const url = 'http://localhost:8080/getBooks';
    postRequest(url, {}, callback);
}

export function getBooksByKeyword(keyword, callback) {
    const url = `http://localhost:8080/getBooksByKeyword?keyword=${keyword}`;
    postRequest(url, {}, callback);
}

export function getBookById(bookId, callback) {
    const url = `http://localhost:8080/getBookById?bookId=${Number(bookId)}`;
    postRequest(url, {}, callback);
}

export function getCommentsById(bookId, callback) {
    const url = `http://localhost:8080/getComments?bookId=${Number(bookId)}`;
    postRequest(url, {}, callback);
}

export function getBookTitle(bookId, callback) {
    const url = `http://localhost:8080/getBookTitle?bookId=${Number(bookId)}`;
    postRequestForText(url, {}, callback);
}

export function getConcernedBookInfo(bookTitle, callback) {
    const url = `http://localhost:8080/getConcernedBookInfo?bookTitle=${bookTitle}`;
    postRequest(url, {}, callback);
}