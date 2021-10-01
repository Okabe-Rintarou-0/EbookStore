import React from 'react'
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import UserManagementList from "../managerComponents/UserManagementList";

const {Content} = Layout;

class UserManagementView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <UserManagementList/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default UserManagementView;