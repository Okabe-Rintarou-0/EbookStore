import React from "react";
import {BookOutlined, FireOutlined, GiftFilled} from '@ant-design/icons';
import {Menu} from "antd";

const {SubMenu} = Menu;

class SiderBar extends React.Component {
    render() {
        return (
            <Menu
                mode="inline"
                defaultSelectedKeys={['1']}
                defaultOpenKeys={['sub1', 'sub2', 'sub3']}
                style={{height: '100%', borderRight: 0,}}
            >
                <SubMenu key="sub1" icon={<BookOutlined/>} title="书籍分类">
                    <Menu.ItemGroup key="g1" title="漫画">
                        <Menu.Item key="1">
                            <a href={"/browse"}>热血王道</a>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <a href={"/browse"}>悬疑推理</a>
                        </Menu.Item>
                        <Menu.Item key="3">
                            <a href={"/browse"}>浪漫言情</a>
                        </Menu.Item>
                        <Menu.Item key="4">
                            <a href={"/browse"}>动作冒险</a>
                        </Menu.Item>
                        <Menu.Item key="5">
                            <a href={"/browse"}>萌系漫画</a>
                        </Menu.Item>
                    </Menu.ItemGroup>
                    <Menu.ItemGroup key="g2" title="科学书籍">
                        <Menu.Item key="6">
                            <a href={"/browse"}>计算机科学</a>
                        </Menu.Item>
                        <Menu.Item key="7">
                            <a href={"/browse"}>社会科学</a>
                        </Menu.Item>
                        <Menu.Item key="8">
                            <a href={"/browse"}>经济学</a>
                        </Menu.Item>
                        <Menu.Item key="9">
                            <a href={"/browse"}>人文科学</a>
                        </Menu.Item>
                        <Menu.Item key="10">
                            <a href={"/browse"}>自然科学</a>
                        </Menu.Item>
                    </Menu.ItemGroup>
                    <Menu.ItemGroup key="g3" title="教辅">
                        <Menu.Item key="11">
                            <a href={"/browse"}>中学教辅</a>
                        </Menu.Item>
                        <Menu.Item key="12">
                            <a href={"/browse"}>小学教辅</a>
                        </Menu.Item>
                        <Menu.Item key="13">
                            <a href={"/browse"}>婴幼儿读物</a>
                        </Menu.Item>
                    </Menu.ItemGroup>
                    <Menu.ItemGroup key="g4" title="小说">
                        <Menu.Item key="14">
                            <a href={"/browse"}>悬疑推理</a>
                        </Menu.Item>
                        <Menu.Item key="15">
                            <a href={"/browse"}>浪漫言情</a>
                        </Menu.Item>
                        <Menu.Item key="16">
                            <a href={"/browse"}>文艺治愈</a>
                        </Menu.Item>
                    </Menu.ItemGroup>
                </SubMenu>
                <SubMenu key="sub2" icon={<FireOutlined/>} title="热销榜">
                    <Menu.Item key="17">鬼灭之刃</Menu.Item>
                    <Menu.Item key="18">王者天下</Menu.Item>
                    <Menu.Item key="19">海贼王</Menu.Item>
                </SubMenu>
                <SubMenu key="sub3" icon={<GiftFilled/>} title="新书推荐">
                    <Menu.Item key="20">React从入门到入土</Menu.Item>
                    <Menu.Item key="21">图解HTTP</Menu.Item>
                    <Menu.Item key="22">图解TCP/IP</Menu.Item>
                </SubMenu>
            </Menu>
        );
    }
}

export default SiderBar;