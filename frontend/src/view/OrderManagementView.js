import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import OrderManagementList from "../managerComponents/OrderManagementList";

const {Content} = Layout;

class OrderManagementView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <OrderManagementList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default OrderManagementView;