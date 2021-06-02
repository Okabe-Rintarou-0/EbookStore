import {postRequest} from "../utils/ajax";

export function getConsumption(callback) {
    const url = 'http://localhost:8080//manager/getConsumption';
    postRequest(url, {}, callback);
}
