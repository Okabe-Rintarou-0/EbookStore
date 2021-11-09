import React from 'react';

import {Avatar, Button, Col, Input, Layout, List, Row} from 'antd';
import {getLocalTime} from "../utils/timeUtils";

const {Footer} = Layout;
let ws;

class ChatRoomPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            messages: [],
        };
    }

    connectWs = () => {
        ws = new WebSocket(`ws://localhost:8080/chat`);
        ws.onmessage = this.onMessage;
        ws.onopen = this.sendJoinMsg;
    };

    sendJoinMsg = () => {
        let joinMsg = {};
        joinMsg.type = "Action";
        joinMsg.action = "Join";
        joinMsg.message = "";
        ws.send(JSON.stringify(joinMsg));
    };

    sendChatMsg = () => {
        let chatMsg = {};
        chatMsg.type = "Action";
        chatMsg.action = "Chat";
        let chatInput = this.refs.chatInput;
        chatMsg.message = chatInput.input.value;
        if (chatMsg.message.length === 0) return;
        ws.send(JSON.stringify(chatMsg));
        chatInput.state.value = '';
        chatInput.focus();
    };

    sendPingPongMsg = () => {
        let pingPongMsg = {};
        pingPongMsg.type = 'PingPong';
        pingPongMsg.timestamp = new Date().getTime();
        ws.send(JSON.stringify(pingPongMsg));
    };

    onMessage = e => {
        let message = JSON.parse(e.data);
        console.log("received: " + JSON.stringify(message));
        let type = message.type;
        let info = message.info;
        let timestamp = message.timestamp;
        let roomMemberInfo = message.roomMemberInfo;
        switch (type) {
            case 'Info':
                switch (info) {
                    case 'Join':
                        this.addMessage('Action', roomMemberInfo.userIcon, roomMemberInfo.username, timestamp, '加入讨论室');
                        break;
                    case 'Leave':
                        this.addMessage('Action', roomMemberInfo.userIcon, roomMemberInfo.username, timestamp, '离开讨论室');
                        break;
                    default:
                        break;
                }
                break;
            case 'Chat':
                this.addMessage('Action', roomMemberInfo.userIcon, roomMemberInfo.username, timestamp, message.message);
                break;
            case 'PingPong':
                this.sendPingPongMsg();
                break;
            default:
                break;
        }
    };

    componentDidMount() {
        this.connectWs();
    }

    ///You can also use useEffect();
    componentWillUnmount() {
        console.log("触发！");
        ws.close();
    }

    addMessage = (type, icon, name, timestamp, message) => {
        let newMsg = {};
        newMsg.type = type;
        newMsg.icon = icon;
        newMsg.name = name;
        newMsg.timestamp = timestamp;
        newMsg.message = message;
        this.setState({
            messages: [...this.state.messages, newMsg]
        });
    };

    render() {
        return (
            <>
                <List
                    itemLayout="horizontal"
                    dataSource={this.state.messages}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta
                                avatar={<Avatar src={item.icon}/>}
                                title={<p>{item.name}</p>}
                                description={`${getLocalTime(item.timestamp)} ${item.message}`}
                            />
                        </List.Item>
                    )}
                />
                <Footer style={{backgroundColor: 'antiquewhite'}}>
                    <Row justify={"middle"} gutter={8}>
                        <Col span={6}>
                            <Input ref={"chatInput"} placeholder="Start your chat here." focus={true}/>
                        </Col>
                        <Col span={2}>
                            <Button type="primary" htmlType="submit"
                                    onClick={this.sendChatMsg}
                                    style={{
                                        backgroundColor: '#fb7d38',
                                        border: '#FB6825 1px solid',
                                        height: '30px',
                                        width: '100%',
                                        textAlign: 'center'
                                    }}>
                                <p>Send</p>
                            </Button>
                        </Col>
                    </Row>
                </Footer>
            </>
        );
    }
}

export default ChatRoomPanel;