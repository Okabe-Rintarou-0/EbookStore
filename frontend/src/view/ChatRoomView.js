import React from 'react';
import {Layout} from "antd";
import Navigator from "../layout/Navigator";
import ChatRoomPanel from "../components/ChatRoomPanel";

const {Content} = Layout;

class ChatRoomView extends React.Component {
    render() {
        return (
            <>
                <div id={"shopping-cart-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Content>
                            <ChatRoomPanel/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default ChatRoomView;
