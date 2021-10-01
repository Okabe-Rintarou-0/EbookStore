import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import BookManagementList from "../managerComponents/BookManagementList";

const {Content} = Layout;

class BookManagementView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <BookManagementList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default BookManagementView;