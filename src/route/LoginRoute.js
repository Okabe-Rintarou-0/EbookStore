import React from 'react';
import {Route, Redirect} from 'react-router-dom'
import {checkSession} from "../service/userService";

export class LoginRoute extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isAuthorized: false,
            hasChecked: false
        }
    }

    checkAuthority = data => {
        let isAuthorized = (data.status === 0);
        this.setState({
            isAuthorized: isAuthorized,
            hasChecked: true
        })
    };

    componentDidMount() {
        checkSession(this.checkAuthority);
    }

    render() {
        const {component: Component, path = "/", exact = false, strict = false} = this.props;

        if (!this.state.hasChecked) return null;

        return <Route path={path} exact={exact} strict={strict} render={props => (
            this.state.isAuthorized ? (
                <Redirect to={{
                    pathname: '/',
                    state: {from: props.location}
                }}/>
            ) : (
                <Component {...props}/>
            )
        )}/>
    }
}

export default LoginRoute;