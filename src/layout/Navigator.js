import React from "react";
import "../css/general.css"
import '../css/navigator.css'
import logoUrl from "../assets/logo.png"
import {getUser, logout, setUserSignature} from "../service/userService";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {
    faBook, faComment,
    faFire,
    faHome,
    faList,
    faMoneyBill,
    faShoppingCart,
    faStar,
    faUser
} from "@fortawesome/free-solid-svg-icons";
import {Avatar, Col, Dropdown, Menu, Modal, Row} from "antd";
import {Header} from "antd/es/layout/layout";
import {DownOutlined, LogoutOutlined, SettingOutlined, UserOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import IconUpload from "../components/IconUpload";
import Input from "antd/es/input";
import {getVisit} from "../service/visitService";

class Navigator extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showPersonConfigModal: false,
            editSignature: false,
            userSignature: "",
            username: "",
            userIcon: "",
            userId: 0,
            visit: '',
            userIdentity: -1
        };
    }

    handleUserInfo = data => {
        this.setState({
            userSignature: data.userSignature === '' ? '这个人很懒，一句话也没有留下。' : data.userSignature,
            username: data.username,
            userId: data.userId,
            userIcon: data.userIcon,
            userIdentity: data.userIdentity,
        });
    };

    componentDidMount() {
        getUser(this.handleUserInfo);
        getVisit(visit => {
            this.setState({visit: visit})
        });
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
                        <b style={{marginLeft: '8px'}}>
                            访问量:{this.state.visit}
                        </b>
                    </Col>
                </Row>
            </Col>
        );
    };

    renderMenuItem = () => {
        let renderContent = [];
        let identity = this.state.userIdentity;
        if (identity === 0) { //user
            renderContent.push(
                <Menu.Item key="cart">
                    <FontAwesomeIcon icon={faShoppingCart} className={"nav-icon"}/>
                    <Link exact to={"/cart"}>购物车</Link>
                </Menu.Item>);
            renderContent.push(
                <Menu.Item key="favor">
                    <FontAwesomeIcon icon={faStar} className={"nav-icon"}/>
                    <Link exact to={"/favourite"}>收藏夹</Link>
                </Menu.Item>);
            renderContent.push(
                <Menu.Item key="order">
                    <FontAwesomeIcon icon={faList} className={"nav-icon"}/>
                    <Link to={"/order"}>订单</Link>
                </Menu.Item>
            );
            renderContent.push(
                <Menu.Item key="statistic">
                    <FontAwesomeIcon icon={faBook} className={"nav-icon"}/>
                    <Link to={"/statistic"}>购买统计</Link>
                </Menu.Item>
            )
        } else if (identity === 1) {
            renderContent.push(
                <Menu.Item key="books">
                    <FontAwesomeIcon icon={faBook} className={"nav-icon"}/>
                    <Link exact to={"/manager/books"}>书籍管理</Link>
                </Menu.Item>);
            renderContent.push(
                <Menu.Item key="users">
                    <FontAwesomeIcon icon={faUser} className={"nav-icon"}/>
                    <Link exact to={"/manager/users"}>用户管理</Link>
                </Menu.Item>);
            renderContent.push(
                <Menu.Item key="orders">
                    <FontAwesomeIcon icon={faList} className={"nav-icon"}/>
                    <Link exact to={"/manager/orders"}>订单管理</Link>
                </Menu.Item>);
            renderContent.push(
                <Menu.Item key="consumption">
                    <FontAwesomeIcon icon={faMoneyBill} className={"nav-icon"}/>
                    <Link exact to={"/manager/consumption"}>消费排名</Link>
                </Menu.Item>);
        }
        return renderContent;
    };

    renderMenu = () => {
        return (
            <Col span={14}>
                <Row>
                    <Menu mode="horizontal" style={{backgroundColor: "antiquewhite"}}>
                        <Menu.Item key="main">
                            <FontAwesomeIcon icon={faHome} className={"nav-icon"}/>
                            <Link exact to={"/"}>主页</Link>
                        </Menu.Item>
                        <Menu.Item key="rank">
                            <FontAwesomeIcon icon={faFire} className={"nav-icon"}/>
                            <Link exact to={"/rank"}>排行榜</Link>
                        </Menu.Item>
                        {this.renderMenuItem()}
                        <Menu.Item key="forum">
                            <FontAwesomeIcon icon={faComment} className={"nav-icon"}/>
                            <Link exact to={"/chat"}>在线讨论</Link>
                        </Menu.Item>
                        {/*<Menu.Item key="share">*/}
                        {/*    <FontAwesomeIcon icon={faShare} className={"nav-icon"}/>*/}
                        {/*    <a href="">分享页面</a>*/}
                        {/*</Menu.Item>*/}
                        {/*<SubMenu key="SubMenu" icon={<InfoCircleFilled/>} title="更多信息">*/}
                        {/*    <Menu.ItemGroup title="联系我们">*/}
                        {/*        <Menu.Item key="setting:1" icon={<GithubFilled/>}><a*/}
                        {/*            href={'https://gitee.com/ymwm233/cat-store/'} target={'_blank'}/>Github</Menu.Item>*/}
                        {/*        <Menu.Item key="setting:2" icon={<WechatFilled/>}>Wechat</Menu.Item>*/}
                        {/*        <Menu.Item key="setting:3" icon={<QqCircleFilled/>}>QQ</Menu.Item>*/}
                        {/*    </Menu.ItemGroup>*/}
                        {/*</SubMenu>*/}
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
                    <Link exact to={"/Link"} onClick={logout}>退出登录</Link>
                </Menu.Item>
            </Menu>
        );
        return (
            <Col span={4}>
                <Row>
                    <Col span={8} offset={4}>
                        <Avatar size={64} src={this.state.userIcon}/>
                    </Col>
                    <Row align="bottom">
                        <Col span={24}>
                            <Dropdown overlay={menu}>
                                <a className="ant-dropdown-link"
                                   style={{textDecoration: 'none', color: 'black'}}>
                                    {this.state.username} <DownOutlined/>
                                </a>
                            </Dropdown>
                        </Col>
                    </Row>
                </Row>
            </Col>
        );
    };

    showPersonalConfig = (e) => {
        this.setState({
            showPersonalConfigModal: true,
        })
    };

    onPersonalConfigModalOK = () => {
        setUserSignature(this.state.userSignature);
        this.setState({
            showPersonalConfigModal: false,
            editSignature: false,
        })
    };

    onPersonalConfigModalCancel = () => {
        this.setState({
            showPersonalConfigModal: false,
            editSignature: false,
        })
    };

    endEdit = (e) => {
        let userSignature = e.target.value;
        this.setState({
            userSignature: userSignature,
            editSignature: false,
        })
    };

    renderUserSignature = () => {
        if (this.state.editSignature)
            return (<Input placeholder="请输入新的个性签名" onPressEnter={this.endEdit}/>);
        else return (<p onDoubleClick={this.edit}>{this.state.userSignature}</p>);
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
                            <Avatar size={128} src={this.state.userIcon}/>
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
                            {this.renderUserSignature()}
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