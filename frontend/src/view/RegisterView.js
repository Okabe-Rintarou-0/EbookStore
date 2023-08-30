import React from 'react'
import iconUrl from '../assets/icon.gif'
import logoUrl from '../assets/logo.png'
import RegisterForm from "../components/RegisterForm";

class RegisterView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div id="register-page">
                <div id={"login-bg"}>
                </div>
                <div id="register-box">
                    <div id="register-icon-part">
                        <img id="user-register-icon" src={iconUrl}
                             alt=""/>
                    </div>
                    <RegisterForm/>
                </div>
            </div>
        );
    }
}

export default RegisterView;




