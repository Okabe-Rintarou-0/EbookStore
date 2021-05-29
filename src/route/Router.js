import React from 'react';
import {BrowserRouter as Router, Switch, Redirect} from 'react-router-dom';
import {history} from "../utils/history";
import LoginView from '../view/LoginView'
import LoginRoute from "./LoginRoute";
import PrivateRoute from "./PrivateRoute";
import HomeView from "../view/HomeView";
import BookView from "../view/BookView";
import ShoppingCartView from "../view/ShoppingCartView";
import PaymentSuccess from "../components/PaymentSuccess";
import FavouriteView from "../view/FavouriteView";
import PaymentFail from "../components/PaymentFail";
import OrderView from "../view/OrderView";

class BasicRoute extends React.Component {

    constructor(props) {
        super(props);
        history.listen((location, action) => {
            // clear alert on location change
            // console.log(location, action);
        });
    }

    render() {
        return (
            <Router history={history}>
                <Switch>
                    <PrivateRoute exact path="/" component={HomeView}/>
                    <LoginRoute exact path="/login" component={LoginView}/>
                    <PrivateRoute path="/book/:bookId" component={BookView}/>
                    <PrivateRoute exact path="/cart" component={ShoppingCartView}/>
                    <PrivateRoute exact path="/favourite" component={FavouriteView}/>
                    <PrivateRoute exact path="/order" component={OrderView}/>
                    <PrivateRoute exact path="/status/success" component={PaymentSuccess}/>
                    <PrivateRoute exact path="/status/fail" component={PaymentFail}/>
                    <Redirect from={'/*'} to={{pathname: "/"}}/>
                </Switch>
            </Router>
        )
    }
}

export default BasicRoute;