import '../css/register.css'
import '../css/general.css'
import {Link, Redirect} from "react-router-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import React from 'react'
import {faEye, faEyeSlash, faLock, faMailBulk, faUser, faUserCircle} from "@fortawesome/free-solid-svg-icons";
import {message, Tooltip} from "antd";
import {checkDuplication, register} from "../service/userService";

class RegisterForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eye_open: true,
            pwdHidden: true,
            username: "",
            password: "",
            duplicate: false,
            showUserAccountTip: false,
            userAccountTips: '用户名不能为空',
            showEmailTip: false,
            emailTips: '用户名不能为空',
        };
    }

    handleRegister = data => {
        if (data.status > 0) {
            message.success(data.message);
            this.setState({
                redirect: true,
            })
        } else {
            message.error(data.message);
        }
    };


    handleSubmit = e => {
        e.preventDefault();
        let registerForm = e.target;
        let userAccount = registerForm['user_account'].value;
        let username = registerForm['username'].value;
        let email = registerForm['email'].value;
        let password = registerForm['password'].value;
        let passwordConfirm = registerForm['password_confirm'].value;
        let isEmpty = str => {
            if (str === null || str === undefined || str.length === undefined || str.length === null) {
                return true;
            } else return str.length === 0;
        };
        if (isEmpty(userAccount) || isEmpty(username) || isEmpty(email) || isEmpty(password) || isEmpty(passwordConfirm)) {
            message.warn("注册信息的任意一项都不能为空！");
            return;
        }
        if (passwordConfirm !== password) {
            message.warn("两次输入的密码不一致！请重新输入！");
            return;
        }
        // console.log(this.state.duplicate);
        // console.log(email);
        // console.log(this.isEmail(email));
        if (userAccount.length < 5 || this.state.duplicate || !this.isEmail(email)) {
            message.warn("请按照提示修改您的注册信息！");
            return;
        }
        let registerInfo = {
            userAccount: userAccount,
            username: username,
            email: email,
            password: password,
        };
        register(registerInfo, this.handleRegister)
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
                <FontAwesomeIcon icon={faEyeSlash} className={"register-icon"} onClick={this.onClickEye}
                                 style={{left: '40px', cursor: 'pointer'}}/>
            );
    };

    handleCheckResponse = res => {
        if (res)
            this.setState({
                showUserAccountTip: true,
                duplicate: true,
                userAccountTips: '当前账户已被注册！请换个名字！',
            });
        else
            this.setState({
                duplicate: false,
                showUserAccountTip: false,
            })
    };

    onAccountChange = e => {
        let account = e.target.value;
        if (account.length < 5) {
            this.setState({
                showUserAccountTip: true,
                duplicate: false,
                userAccountTips: '账户长度不得少于5个字',
            });
            return;
        }
        checkDuplication(account, this.handleCheckResponse);
    };

    isEmail = (str) => /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+(\.([a-zA-Z]{2,4}))+$/.test(str);

    onEmailChange = e => {
        let email = e.target.value;
        let isValid = this.isEmail(email);
        console.log(isValid);
        this.setState({
            showEmailTip: !isValid,
            emailTips: isValid ? '' : '非法的邮箱地址！请输入正确的邮箱地址！',
        })
    };

    render() {
        return (
            <form id="user-register-info" onSubmit={this.handleSubmit}>
                <div className="register-bar">
                    <Tooltip title={this.state.userAccountTips} color={'volcano'}
                             visible={this.state.showUserAccountTip}
                             trigger='focus'>
                        <FontAwesomeIcon icon={faUser} className={"register-icon"}/>
                        <input className="register-info-input user_account" type="text" name="user_account"
                               id={"user_account"}
                               placeholder="Please input your user account." autoFocus="autofocus" autoComplete="on"
                               onChange={this.onAccountChange}/>
                    </Tooltip>
                </div>
                <div className="register-bar">
                    <FontAwesomeIcon icon={faUserCircle} className={"register-icon"}/>
                    <input className="register-info-input username" type="text" name="user-name" id={"username"}
                           placeholder="Please input your username." autoFocus="autofocus" autoComplete="on"
                    />
                </div>
                <div className="register-bar">
                    <Tooltip title={this.state.emailTips} color={'volcano'}
                             visible={this.state.showEmailTip}
                             trigger='focus'>
                        <FontAwesomeIcon icon={faMailBulk} className={"register-icon"}/>
                        <input className="register-info-input email" type="text" name="email" id={"email"}
                               placeholder="Please input your email." autoFocus="autofocus" autoComplete="on"
                               onChange={this.onEmailChange}
                        />
                    </Tooltip>
                </div>
                <div className="register-bar">
                    <FontAwesomeIcon icon={faLock} className={"register-icon"}/>
                    <input className="register-info-input password" type={this.state.pwdHidden ? "password" : "text"}
                           name="user-password"
                           id={"password"}
                           placeholder="Please input your password." autoComplete="on" defaultValue={""}/>
                    {this.renderEye()}
                </div>
                <div className="register-bar">
                    <FontAwesomeIcon icon={faLock} className={"register-icon"}/>
                    <input className="register-info-input password" type={this.state.pwdHidden ? "password" : "text"}
                           name="user-password"
                           id={"password_confirm"}
                           placeholder="Please confirm your password." autoComplete="on" defaultValue={""}/>
                    {this.renderEye()}
                </div>
                <div className="register-bar">
                    <input type="submit" className="register-info-input" id={"register-submit"} name="submit"
                           value={"Register"}
                           style={{outline: 'orange', cursor: 'pointer', color: 'black', fontSize: '18px'}}>
                    </input>
                </div>
                <Link to={"/login"} className="register-concerned-href register">Back to login</Link>
                {this.state.redirect ? <Redirect exact to={"/login"}/> : null}
            </form>
        );
    }
}

export default RegisterForm;