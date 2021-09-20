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
import ManagerRoute from "./ManagerRoute";
import BookManagementView from "../view/BookManagementView";
import UserManagementView from "../view/UserManagementView";
import OrderManagementView from "../view/OrderManagementView";
import RankView from "../view/RankView";
import ConsumptionManagementView from "../view/ConsumptionManagementView";
import PurchaseStatisticView from "../view/PurchaseStatisticView";
import RegisterView from "../view/RegisterView";
import RegisterRoute from "./RegisterRoute";
import ChatRoomView from "../view/ChatRoomView";

class BasicRoute extends React.Component {

    constructor(props) {
        super(props);
        history.listen((location, action) => {
        });
    }

    render() {
        return (
            <Router history={history}>
                <Switch>
                    <PrivateRoute exact path="/" component={HomeView}/>
                    <LoginRoute exact path="/login" component={LoginView}/>
                    <RegisterRoute exact path="/register" component={RegisterView}/>
                    <PrivateRoute path="/book/:bookId" component={BookView}/>
                    <PrivateRoute exact path="/cart" component={ShoppingCartView}/>
                    <PrivateRoute exact path="/favourite" component={FavouriteView}/>
                    <PrivateRoute exact path="/order" component={OrderView}/>
                    <PrivateRoute exact path="/rank" component={RankView}/>
                    <PrivateRoute exact path="/status/success" component={PaymentSuccess}/>
                    <PrivateRoute exact path="/status/fail" component={PaymentFail}/>
                    <PrivateRoute exact path="/statistic" component={PurchaseStatisticView}/>
                    <PrivateRoute exact path="/chat" component={ChatRoomView}/>
                    <ManagerRoute exact path="/manager/orders" component={OrderManagementView}/>
                    <ManagerRoute exact path="/manager/books" component={BookManagementView}/>
                    <ManagerRoute exact path="/manager/users" component={UserManagementView}/>
                    <ManagerRoute exact path="/manager/consumption" component={ConsumptionManagementView}/>
                    <ManagerRoute exact path="/manager/orders" component={OrderManagementView}/>
                    <Redirect from={'/*'} to={{pathname: "/"}}/>
                </Switch>
            </Router>
        )
    }
}

export default BasicRoute;