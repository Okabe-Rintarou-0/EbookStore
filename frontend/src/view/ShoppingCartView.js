import React from 'react'
import Navigator from "../layout/Navigator";
import {Layout} from "antd";
import CartContent from "../components/CartContent";
import '../css/shoppingcart.css'

const {Content} = Layout;

class ShoppingCartView extends React.Component {

    constructor(props) {
        super(props);
    }
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <CartContent/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default ShoppingCartView;