import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import ConsumptionManagementList from "../managerComponents/ConsumptionManagementList";

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