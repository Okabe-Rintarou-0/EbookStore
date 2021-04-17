import Navigator from "../layout/Navigator";
import React from "react";
import SiderBar from "../layout/Sider";
import BookCarousel from "../components/Carousel";
import BookList from "../components/BookList";
import '../css/home.css';
import {Col, Input, Layout, Row} from "antd";

const {Search} = Input;
const {Content, Sider} = Layout;

class HomeView extends React.Component {

    renderSearchBar = () => {
        return (
            <Row align={"center"}>
                <Col span={12} style={{margin: '20px'}}>
                    <Search
                        placeholder="请输入搜索关键词"
                        allowClear
                        enterButton="Search"
                        size="large"
                    />
                </Col>
            </Row>
        );
    };

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
                            {this.renderSearchBar()}
                            <BookCarousel/>
                            <div className="recommend-title">
                                <span className="circle-word">好</span>
                                <b className="title">书推荐</b>
                                <div className="horizontal-line">
                                </div>
                                <BookList/>
                            </div>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default HomeView;