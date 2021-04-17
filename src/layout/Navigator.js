import React from "react";
import "../css/general.css"
import '../css/navigator.css'
import logoUrl from "../assets/logo.png"
import iconUrl from "../assets/icon.gif"
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {
    faBookReader,
    faHome,
    faPhone,
    faShare,
    faShoppingCart,
    faStar
} from "@fortawesome/free-solid-svg-icons";
import {Row, Col, Menu, Avatar, Dropdown, Modal} from "antd";
import {Header} from "antd/es/layout/layout";
import {
    DownOutlined,
    GithubFilled, InfoCircleFilled,
    LogoutOutlined,
    QqCircleFilled,
    SettingOutlined,
    UserOutlined,
    WechatFilled
} from "@ant-design/icons";
import {Link} from "react-router-dom";
import IconUpload from "../components/IconUpload";
import Input from "antd/es/input";

const {SubMenu} = Menu;

class Navigator extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showPersonConfigModal: false,
            editSignature: false,
            personalizedSignature: "ずっと真夜でいいのに"
        };
    }

    renderLogo = () => {
        return (
            <Col span={6}>
                <Row>
                    <Col span={6}>
                        <img src={logoUrl} alt="" style={{height: '60px', width: '60px'}}/>
                    </Col>
                    <Col span={18}>
                        <b style={{
                            fontFamily: 'Impact, serif',
                            fontSize: '20px',
                            fontWeight: 'lighter',
                            color: '#666666'
                        }}>A store full of books and...cats!</b>
                    </Col>
                </Row>
            </Col>
        );
    };

    renderMenu = () => {
        return (
            <Col span={14}>
                <Row>
                    <Menu mode="horizontal" style={{backgroundColor: "antiquewhite"}}>
                        <Menu.Item key="main">
                            <FontAwesomeIcon icon={faHome} className={"nav-icon"}/>
                            <Link exact to={"/home"}>主页</Link>
                        </Menu.Item>
                        <Menu.Item key="cart">
                            <FontAwesomeIcon icon={faShoppingCart} className={"nav-icon"}/>
                            <Link exact to={"/cart"}>购物车</Link>
                        </Menu.Item>
                        <Menu.Item key="favor">
                            <FontAwesomeIcon icon={faStar} className={"nav-icon"}/>
                            <Link exact to={"/home"}>收藏夹</Link>
                        </Menu.Item>
                        <Menu.Item key="service" disabled>
                            <FontAwesomeIcon icon={faPhone} className={"nav-icon"}/>
                            <a href="">客服</a>
                        </Menu.Item>
                        <Menu.Item key="forum" disabled>
                            <FontAwesomeIcon icon={faBookReader} className={"nav-icon"}/>
                            <a href="">论坛</a>
                        </Menu.Item>
                        <Menu.Item key="share">
                            <FontAwesomeIcon icon={faShare} className={"nav-icon"}/>
                            <a href="">分享页面</a>
                        </Menu.Item>
                        <SubMenu key="SubMenu" icon={<InfoCircleFilled/>} title="更多信息">
                            <Menu.ItemGroup title="联系我们">
                                <Menu.Item key="setting:1" icon={<GithubFilled/>}>Github</Menu.Item>
                                <Menu.Item key="setting:2" icon={<WechatFilled/>}>Wechat</Menu.Item>
                                <Menu.Item key="setting:3" icon={<QqCircleFilled/>}>QQ</Menu.Item>
                            </Menu.ItemGroup>
                        </SubMenu>
                    </Menu>
                </Row>
            </Col>
        )
    };

    renderUserInfo = () => {
        const menu = (
            <Menu>
                <Menu.Item key="2" icon={<UserOutlined/>}>
                    用户信息
                </Menu.Item>
                <Menu.Item key="3" icon={<SettingOutlined/>} onClick={this.showPersonalConfig}>
                    {/* eslint-disable-next-line no-script-url */}
                    个人设置
                </Menu.Item>
                <Menu.Item key="1" icon={<LogoutOutlined/>}>
                    <Link exact to={"/"}>退出登录</Link>
                </Menu.Item>
            </Menu>
        );
        return (
            <Col span={4}>
                <Row>
                    <Col span={8} offset={4}>
                        <Avatar size={64} src={iconUrl}/>
                    </Col>
                    <Row align="bottom">
                        <Col span={24}>
                            <Dropdown overlay={menu}>
                                <a className="ant-dropdown-link"
                                   style={{textDecoration: 'none', color: 'black'}}>
                                    孤独の観測者 <DownOutlined/>
                                </a>
                            </Dropdown>
                        </Col>
                    </Row>
                </Row>
            </Col>
        );
    };

    showPersonalConfig = (e) => {
        console.log(e);
        this.setState({
            showPersonalConfigModal: true,
        })
    };

    onPersonalConfigModalOK = () => {
        this.setState({
            showPersonalConfigModal: false,
        })
    };

    onPersonalConfigModalCancel = () => {
        this.setState({
            showPersonalConfigModal: false,
        })
    };

    endEdit = (e) => {
        this.setState({
            editSignature: false,
            personalizedSignature: e.target.value,
        })
    };

    renderPersonalizedSignature = () => {
        if (this.state.editSignature)
            return (<Input placeholder="请输入新的个性签名" onPressEnter={this.endEdit}/>);
        else return (<p onDoubleClick={this.edit}>{this.state.personalizedSignature}</p>);
    };

    edit = () => {
        this.setState({
            editSignature: true,
        })
    };

    renderPersonalConfigModal = () => {
        if (this.state.showPersonalConfigModal)
            return (
                <Modal title="个人设置" visible={true} onOk={this.onPersonalConfigModalOK}
                       onCancel={this.onPersonalConfigModalCancel}
                       okText={"确认修改"} cancelText={"取消"}>
                    <Row>
                        <Col span={8}>
                            <Avatar size={128} src={iconUrl}/>
                        </Col>
                    </Row>
                    <Row style={{marginTop: '10px'}}>
                        <Col span={8}>
                            <IconUpload/>
                        </Col>
                    </Row>
                    <Row style={{marginTop: '10px'}}>
                        <Col span={8}>
                            <b>个性签名:&nbsp;</b>
                            {this.renderPersonalizedSignature()}
                        </Col>
                    </Row>
                </Modal>
            );
        else return null;
    };

    render() {
        return (
            <Header style={{height: 'auto', backgroundColor: 'antiquewhite', zIndex: '2'}}>
                <Col span={24}>
                    <Row>
                        {this.renderLogo()}
                        {this.renderMenu()}
                        {this.renderUserInfo()}
                        {this.renderPersonalConfigModal()}
                    </Row>
                </Col>
            </Header>
        );
    }
}

export default Navigator;