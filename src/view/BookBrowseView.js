import React from "react";
import Navigator from "../layout/Navigator";
import SiderBar from "../layout/Sider";
import BookBrowser from "../components/BookBrowser";
import {Layout} from "antd";

const {Content, Sider} = Layout;

class BookBrowseView extends React.Component {
    render() {
        return (
            <>
                <div id={"book-detail-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Sider width={200} className="site-layout-background">
                            <SiderBar/>
                        </Sider>
                        <Content>
                            <BookBrowser/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default BookBrowseView;