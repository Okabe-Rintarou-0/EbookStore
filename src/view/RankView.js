import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import RankList from "../components/RankList";

const {Content} = Layout;

class RankView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <RankList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default RankView;