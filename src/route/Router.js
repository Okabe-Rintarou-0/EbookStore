import React from 'react';
import {BrowserRouter as Router, Route, Switch, Redirect} from 'react-router-dom';
import {history} from "../utils/history";
import LoginView from '../view/LoginView'
import LoginRoute from "./LoginRoute";
import HomeView from "../view/HomeView";
import BookView from "../view/BookView";
import ShoppingCartView from "../view/ShoppingCartView";
import BookBrowseView from "../view/BookBrowseView";
import PaymentResult from "../components/PaymentResult";

class BasicRoute extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router history={history}>
                <Switch>
                    <Route exact path="/" component={LoginView}/>
                    <Route path="/home" component={HomeView}/>
                    <Route path="/book" component={BookView}/>
                    <Route path="/cart" component={ShoppingCartView}/>
                    <Route path="/browse" component={BookBrowseView}/>
                    <Route path="/status" component={PaymentResult}/>
                    <Redirect from={'/*'} to={{pathname: "/"}}/>
                </Switch>
            </Router>
        )
    }
}

export default BasicRoute;