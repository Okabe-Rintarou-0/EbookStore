import React from 'react';
import {Route, Redirect} from 'react-router-dom'

export class LoginRoute extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isAuthorized: false,
        }
    }

    render() {
        const {component: Component, path = "/", exact = false, strict = false} = this.props;

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