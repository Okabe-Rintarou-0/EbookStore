import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import OrderContent from "../components/OrderContent";

const {Content} = Layout;

class OrderView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <OrderContent/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default OrderView;