import {constants} from "../config/constants";
import {getRequest, putRequest} from "../utils/ajax";

export function addComment(bookId, content, callback) {
    let url = `${constants.base_url}comment/${bookId}/${content}`;
    putRequest(url, {}, callback);
}

export function getComments(bookId, callback) {
    let url = `${constants.base_url}comment/${bookId}`;
    getRequest(url, callback);
}

export function updateAction(commentId, newAction, callback) {
    let url = `${constants.base_url}comment/action/${commentId}/${newAction}`;
    putRequest(url, {}, callback);
}