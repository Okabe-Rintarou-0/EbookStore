import {postRequest, postRequestForText} from "../utils/ajax";

export function getBooksByPage(page,callback) {
    const url = `http://localhost:8080/getBooksByPage?page=${page}`;
    postRequest(url, {}, callback);
}

export function getBooks(callback) {
    const url = `http://localhost:8080/getBooks`;
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

export function getConcernedBookInfo(bookTitle, website, callback) {
    const url = `http://localhost:8080/getConcernedBookInfo?bookTitle=${bookTitle}&website=${website}`;
    postRequest(url, {}, callback);
}

export function deleteBooks(bookIdList, callback) {
    const url = 'http://localhost:8080/manager/deleteBooks';
    postRequest(url, bookIdList, callback)
}

export function undercarriageBooks(bookIdList, callback) {
    const url = 'http://localhost:8080/manager/undercarriage';
    postRequest(url, bookIdList, callback)
}

export function putOnSale(bookIdList, callback) {
    const url = 'http://localhost:8080/manager/putOnSale';
    postRequest(url, bookIdList, callback)
}

export function postModifiedBook(book, callback) {
    const url = 'http://localhost:8080/manager/postModifiedBook';
    postRequest(url, book, callback);
}

export function getRankedBooks(startNEndDates, callback) {
    console.log("startNends", startNEndDates);
    const url = 'http://localhost:8080/getRankedBooks';
    postRequest(url, startNEndDates, callback)
}