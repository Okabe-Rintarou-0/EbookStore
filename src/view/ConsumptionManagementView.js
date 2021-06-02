import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import OrderManagementList from "../ManageComponents/OrderManagementList";
import ConsumptionManagementList from "../ManageComponents/ConsumptionManagementList";

const {Content} = Layout;

class ConsumptionManagementView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <ConsumptionManagementList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default ConsumptionManagementView;