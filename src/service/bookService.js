import {getRequest, postRequest} from "../utils/ajax";

export function getBooksByPage(page, callback) {
    const url = `http://localhost:8080/book?page=${page}`;
    getRequest(url, callback);
}

export function getBooks(callback) {
    const url = `http://localhost:8080/book/getAll`;
    getRequest(url, callback);
}

export function getBooksByKeyword(keyword, callback) {
    const url = `http://localhost:8080/book/search?keyword=${keyword}`;
    getRequest(url, callback);
}

export function getBookById(bookId, callback) {
    const url = `http://localhost:8080/book/get?bookId=${Number(bookId)}`;
    getRequest(url, callback);
}

export function getConcernedBookInfo(bookTitle, website, callback) {
    const url = `http://localhost:8080/book/concerned?bookTitle=${bookTitle}&website=${website}`;
    getRequest(url, callback);
}

export function deleteBooks(bookIdList, callback) {
    const url = 'http://localhost:8080/book/delete';
    postRequest(url, bookIdList, callback)
}

export function undercarriageBooks(bookIdList, callback) {
    const url = 'http://localhost:8080/book/undercarriage';
    postRequest(url, bookIdList, callback)
}

export function putOnSale(bookIdList, callback) {
    const url = 'http://localhost:8080/book/putOnSale';
    postRequest(url, bookIdList, callback)
}

export function postModifiedBook(book, callback) {
    const url = 'http://localhost:8080/book/modify';
    postRequest(url, book, callback);
}

export function getRankedBooks(startNEndDates, callback) {
    console.log("startNEndDates", startNEndDates);
    if (startNEndDates.length === 2) {
        const url = `http://localhost:8080/book/rank?from=${startNEndDates[0]}&to=${startNEndDates[1]}`;
        getRequest(url, callback)
    } else {
        const url = `http://localhost:8080/book/rank/all`;
        getRequest(url, callback);
    }
}