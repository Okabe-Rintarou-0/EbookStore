import {getRequest, postRequest} from "../utils/ajax";
import {message} from "antd";
import {history} from "../utils/history";

export function login(data) {
    const callback = data => {
        if (data.status > 0) {
            localStorage.setItem('user', JSON.stringify(data.data));
            history.push("/");
            history.go(0);
            message.success(data.message).then(null);
        } else {
            message.error(data.message).then(null);
        }
    };
    const url = 'http://localhost:8080/login';
    postRequest(url, data, callback);
}

export function checkSession(callback) {
    const url = 'http://localhost:8080/checkSession';
    getRequest(url, callback);
}

export function logout() {
    const url = 'http://localhost:8080/logout';
    const callback = data => {
        if (data.status > 0) {
            localStorage.removeItem('user');
            history.push("/login");
            message.success(data.message).then(null);
        } else {
            message.error(data.message).then(null);
        }
    };
    getRequest(url, callback);
}

export function getUser(callback) {
    const url = 'http://localhost:8080/user';
    getRequest(url, callback);
}

export function setUserSignature(userSignature, callback) {
    const url = `http://localhost:8080/user/signature?newSig=${userSignature}`;
    getRequest(url, callback);
}

export function getAllUsers(callback) {
    const url = 'http://localhost:8080/user/all';
    getRequest(url, callback);
}

export function banUsers(userIdList, callback) {
    const url = 'http://localhost:8080/user/ban';
    postRequest(url, userIdList, callback);
}

export function unbanUsers(userIdList, callback) {
    const url = 'http://localhost:8080/user/unban';
    postRequest(url, userIdList, callback);
}

export function checkDuplication(account, callback) {
    const url = `http://localhost:8080/register/checkDup?account=${account}`;
    getRequest(url, callback);
}

export function register(registerInfo, callback) {
    const url = `http://localhost:8080/register`;
    postRequest(url, registerInfo, callback);
}