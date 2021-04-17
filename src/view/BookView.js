import React from "react";
import Navigator from "../layout/Navigator";
import SiderBar from "../layout/Sider";
import BookDetails from "../components/BookDetails";
import BookComments from "../components/BookComments";
import {Layout} from "antd";

const {Footer, Content, Sider} = Layout;

class BookView extends React.Component {
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
                            <BookDetails/>
                            <BookComments/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default BookView;