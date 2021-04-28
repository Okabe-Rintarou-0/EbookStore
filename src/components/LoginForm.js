import '../css/login.css'
import '../css/general.css'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import React from 'react'
import {faEye, faEyeSlash, faLock, faUser} from "@fortawesome/free-solid-svg-icons";
import {login, checkSession} from "../service/userService";
import {history} from "../utils/history";
import {message} from "antd";

class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eye_open: true,
            pwdHidden: true,
            username: "",
            password: "",
        };
    }

    handleLogin = data => {
        if (data.status > 0) {
            localStorage.setItem('user', JSON.stringify(data.data));
            history.push("/");
            message.success(data.message);
        } else {
            message.error(data.message);
        }
    };

    handleSubmit = e => {
        e.preventDefault();
        let loginForm = e.target;
        console.log(loginForm['username'].value);
        console.log(loginForm['password'].value);
        let loginInfo = {
            'userAccount': loginForm['username'].value,
            'userPassword': loginForm['password'].value
        };

        login(loginInfo);
        // checkSession();
    };

    toggleEye = () => {
        this.setState({
            eye_open: !this.state.eye_open,
            pwdHidden: !this.state.pwdHidden,
        })
    };

    onClickEye = () => {
        this.toggleEye();
    };

    renderEye = () => {
        return this.state.eye_open ? (
                <FontAwesomeIcon icon={faEye} className={"login-icon"} onClick={this.onClickEye}
                                 style={{left: '40px', cursor: 'pointer'}}/>
            ) :
            (
                <FontAwesomeIcon icon={faEyeSlash} className={"login-icon"} onClick={this.onClickEye}
                                 style={{left: '40px', cursor: 'pointer'}}/>
            );
    };

    render() {
        return (
            <form id="user-login-info" onSubmit={this.handleSubmit}>
                <div className="login-bar">
                    <FontAwesomeIcon icon={faUser} className={"login-icon"}/>
                    <input className="login-info-input username" type="text" name="user-name" id={"username"}
                           placeholder="Please input your username." autoFocus="autofocus" autoComplete="on"/>
                </div>
                <div className="login-bar">
                    <FontAwesomeIcon icon={faLock} className={"login-icon"}/>
                    <input className="login-info-input password" type={this.state.pwdHidden ? "password" : "text"}
                           name="user-password"
                           id={"password"}
                           placeholder="Please input your password." autoComplete="on" defaultValue={""}/>
                    {this.renderEye()}
                </div>
                <div className="login-bar">
                    <input type="submit" className="login-info-input" id={"login-submit"} name="submit"
                           value={"Login"}
                           style={{outline: 'orange', cursor: 'pointer', color: 'black'}}>
                    </input>
                </div>
                <a className="login-concerned-href forget-pwd" href="">Forget Password</a>
                <a className="login-concerned-href register" href="">Register</a>
            </form>
        );
    }
}

export default LoginForm;