// import config from 'config'
import {postRequest} from "../utils/ajax";
import {message} from "antd";
import {history} from "../utils/history";

export function login(data) {
    const callback = data => {
        if (data.status > 0) {
            localStorage.setItem('user', JSON.stringify(data.data));
            history.push("/");
            history.go(0);
            message.success(data.message);
        } else {
            message.error(data.message);
        }
    };
    const url = 'http://localhost:8080/login';
    postRequest(url, data, callback);
}

export function checkSession(callback) {
    const url = 'http://localhost:8080/checkSession';
    postRequest(url, {}, callback);
}

export function logout() {
    const url = 'http://localhost:8080/logout';
    const callback = data => {
        if (data.status > 0) {
            localStorage.removeItem('user');
            history.push("/login");
            message.success(data.message);
        } else {
            message.error(data.message);
        }
    };
    postRequest(url, {}, callback);
}

export function getUser(callback) {
    const url = 'http://localhost:8080/getUser';
    postRequest(url, {}, callback);
}

export function getUserProperty(callback) {
    const url = 'http://localhost:8080/getUserProperty';
    postRequest(url, {}, callback);
}

export function setUserSignature(userSignature, callback) {
    const url = 'http://localhost:8080/setUserSignature';
    let requestBody = {userSignature: userSignature};
    // console.log(JSON.stringify(userSignature));
    postRequest(url, requestBody, callback);
}

export function getAllUsers(callback) {
    const url = 'http://localhost:8080/manager/getAllUsers';
    postRequest(url, {}, callback);
}

export function banUsers(userIdList, callback) {
    const url = 'http://localhost:8080/manager/banUsers';
    postRequest(url, userIdList, callback);
}

export function unbanUsers(userIdList, callback) {
    const url = 'http://localhost:8080/manager/unbanUsers';
    postRequest(url, userIdList, callback);
}