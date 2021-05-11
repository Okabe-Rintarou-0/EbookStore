import {postRequest, postRequestForText} from "../utils/ajax";

export function postCommentAction(commentId, like, dislike, callback) {
    const url = `http://localhost:8080/postUserCommentAction?commentId=${commentId}`;
    const postData = {
        'like': like,
        'dislike': dislike,
    };
    postRequest(url, postData, callback);
}

export function getCommentAction(commentId, callback) {
    const url = `http://localhost:8080/getCommentAction?commentId=${commentId}`;
    postRequestForText(url, {}, callback);
}