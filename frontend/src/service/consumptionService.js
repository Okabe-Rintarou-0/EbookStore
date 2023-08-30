import {postRequest} from "../utils/ajax";

export function getConsumption(callback) {
    const url = 'http://localhost:8080/manager/getConsumption';
    postRequest(url, {}, callback);
}
export function searchConsumptions(startNEndDates, callback) {
    console.log(startNEndDates);
    const url = 'http://localhost:8080/manager/getConsumptionInRange';
    postRequest(url, startNEndDates, callback);
}

export function getConsumptionsGroupByBooks(startNEndDates, callback) {
    console.log(startNEndDates);
    const url = 'http://localhost:8080/getConsumptionsGroupByBooks';
    postRequest(url, startNEndDates, callback);
}