import {getRequest} from "../utils/ajax";

export function getVisit(callback) {
    let url = 'http://localhost:8080/visit';
    getRequest(url, callback);
}