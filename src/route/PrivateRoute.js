import React from 'react';
import {Route, Redirect} from 'react-router-dom'
import {checkSession} from "../service/userService";

export class PrivateRoute extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isAuthorized: false,
            hasChecked: false
        }
    }

    checkAuthority = data => {
        console.log(data);
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
                <Component {...props}/>
            ) : (
                <Redirect to={{
                    pathname: '/login',
                    state: {from: props.location}
                }}/>
            )
        )}/>
    }
}

export default PrivateRoute;