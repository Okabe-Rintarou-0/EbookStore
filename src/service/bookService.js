import {getRequest, postRequest} from "../utils/ajax";
import {constants} from "../config/constants";

export function getBooksByPage(page, callback) {
    const url = `${constants.base_url}book?page=${page}`;
    getRequest(url, callback);
}

export function searchBooksByTags(tags, callback) {
    let tags_str = '';
    for (let i = 0; i < tags.length; ++i) {
        tags_str = tags_str.concat(tags[i] + ',');
    }
    tags_str = tags_str.substr(0, tags_str.length - 1);
    console.log("tags: " + tags_str);
    const url = `${constants.base_url}book/tag/${tags_str}`;

    getRequest(url, callback);
}

export function getBooks(callback) {
    const url = `${constants.base_url}book/getAll`;
    getRequest(url, callback);
}

export function getBooksByKeyword(keyword, callback) {
    const url = `${constants.base_url}book/search?keyword=${keyword}`;
    getRequest(url, callback);
}

export function getBookById(bookId, callback) {
    const url = `${constants.base_url}book/get?bookId=${Number(bookId)}`;
    getRequest(url, callback);
}

export function getConcernedBookInfo(bookTitle, website, callback) {
    const url = `${constants.base_url}book/concerned?bookTitle=${bookTitle}&website=${website}`;
    getRequest(url, callback);
}

export function deleteBooks(bookIdList, callback) {
    const url = `${constants.base_url}book/delete`;
    postRequest(url, bookIdList, callback)
}

export function undercarriageBooks(bookIdList, callback) {
    const url = `${constants.base_url}book/undercarriage`;
    postRequest(url, bookIdList, callback)
}

export function putOnSale(bookIdList, callback) {
    const url = `${constants.base_url}book/putOnSale`;
    postRequest(url, bookIdList, callback)
}

export function postModifiedBook(book, callback) {
    const url = `${constants.base_url}book/modify`;
    postRequest(url, book, callback);
}

export function getRankedBooks(startNEndDates, callback) {
    console.log("startNEndDates", startNEndDates);
    if (startNEndDates.length === 2) {
        const url = `${constants.base_url}book/rank?from=${startNEndDates[0]}&to=${startNEndDates[1]}`;
        getRequest(url, callback)
    } else {
        const url = `${constants.base_url}book/rank/all`;
        getRequest(url, callback);
    }
}