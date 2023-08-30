import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import FavouriteContent from "../components/FavouriteContent";

const {Content} = Layout;

class FavouriteView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <FavouriteContent/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default FavouriteView;