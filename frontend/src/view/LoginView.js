import React from 'react'
import LoginForm from "../components/LoginForm";
import iconUrl from '../assets/icon.gif'
import logoUrl from '../assets/logo.png'

class LoginView extends React.Component {
    render() {
        return (
            <div id="login-page">
                <div id={"login-bg"}>
                </div>
                <div id="login-box">
                    <div id="login-icon-part">
                        <img id="user-login-icon" src={iconUrl}
                             alt=""/>
                    </div>
                    <LoginForm/>
                    <div id="login-logo">
                        <a>
                            <img className="logo" src={logoUrl} alt=""/>
                        </a>
                        <a className="more-info-href more-info" href="">More info</a>
                        <a className="more-info-href contact-us" href=""> Contact us</a>
                    </div>
                </div>
            </div>
        );
    }
}

export default LoginView;




