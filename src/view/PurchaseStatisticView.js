import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import PurchaseStatisticList from "../components/PurchaseStatisticList";

const {Content} = Layout;

class PurchaseStatisticView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <PurchaseStatisticList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default PurchaseStatisticView;