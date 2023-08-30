import Navigator from "../layout/Navigator";
import React from "react";
import SiderBar from "../layout/Sider";
import BookList from "../components/BookList";
import '../css/home.css';
import {Layout} from "antd";

const {Content, Sider} = Layout;

class HomeView extends React.Component {

    render() {
        return (
            <>
                <div id={"home-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Sider width={200} className="site-layout-background">
                            <SiderBar/>
                        </Sider>
                        <Content>
                            <BookList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default HomeView;