import '../css/login.css'
import '../css/general.css'
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import React from 'react'
import {faEye, faEyeSlash, faLock, faUser} from "@fortawesome/free-solid-svg-icons";
import {login} from "../service/userService";
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

    handleSubmit = e => {
        e.preventDefault();
        let loginForm = e.target;
        console.log(loginForm);
        let account = loginForm['account'].value;
        let pwd = loginForm['password'].value;
        if (account === '' || pwd === '') {
            message.warn("账号和密码均不能为空！");
            return;
        }
        let loginInfo = {
            'account': account,
            'password': pwd
        };
        login(loginInfo);
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
                    <input className="login-info-input account" type="text" name="account" id={"account"}
                           placeholder="Please input your account." autoFocus="autofocus" autoComplete="on"/>
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
                <Link to={"/register"} className="login-concerned-href register">Register</Link>
            </form>
        );
    }
}

export default LoginForm;