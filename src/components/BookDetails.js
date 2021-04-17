import React from "react";
import '../css/bookDetails.css'
import bookUrl from '../assets/books/mushishi.jpg'
import {Image, Dropdown, Menu, Button, message} from "antd";
import {act} from "@testing-library/react";

class BookDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {selectedRegion: '上海',};
    }

    sendMessage = (e) => {
        let text = e.target.innerText;
        message
            .loading(text + '中...', 1)
            .then(() => message.success(text + '成功！', 1.5));
    };

    static defaultProps = {
        selectedRegion: "上海",
    };

    setSelectRegion = (region) => {
        this.setState({selectedRegion: region})
    };

    onClick = ({key}) => {
        this.setSelectRegion(key);
    };

    render() {
        const menu = (
            <Menu onClick={this.onClick}>
                <Menu.Item key={"上海"}>
                    {/* eslint-disable-next-line no-script-url */}
                    <a rel="noopener noreferrer" href="javascript:void(0);">
                        上海
                    </a>
                </Menu.Item>
                <Menu.Item key={"北京"}>
                    {/* eslint-disable-next-line no-script-url */}
                    <a rel="noopener noreferrer" href="javascript:void(0);">
                        北京
                    </a>
                </Menu.Item>
                <Menu.Item key={"广州"}>
                    {/* eslint-disable-next-line no-script-url */}
                    <a rel="noopener noreferrer" href="javascript:void(0);">
                        广州
                    </a>
                </Menu.Item>
            </Menu>
        );
        return (
            <div style={{
                display: 'flex',
                flexFlow: 'row',
                // backgroundColor: 'red',
                margin: '40px 40px 0 40px'
            }}>
                <div className="browse-book-cover">
                    <Image src={bookUrl} style={{width: '450px', height: '540px'}}/>
                </div>
                <div className="browse-book-info">
                    <div className="browse-book-title">《虫师》第一卷</div>
                    <div className="browse-book-labels">
                        <span className="book-label">奇幻</span>
                        <span className="book-label">治愈</span>
                        <span className="book-label">文艺</span>
                    </div>
                    <div className="browse-book-price">
                        <b>价格</b>
                        <span className="price">¥30</span>
                    </div>
                    <div className="browse-book-postal-fee">
                        <b>运费</b>
                        <Dropdown overlay={menu} placement="bottomCenter">
                            <Button style={{
                                fontFamily: 'Arial serif',
                                marginLeft: '10px'
                            }}>{this.state.selectedRegion}</Button>
                        </Dropdown>
                    </div>
                    <div className="browse-book-description">
                        <div className="author">作者：漆原友纪</div>
                        在这个世界中，还住着一群与常见动植物截然不同的生物。远古以来，人们敬畏地称它们为“虫”。它们有自己的生存方式，而这种方式却可能有悖于人类的常识，甚至危害人类的生存。于是就出现了“虫师”这种职业，他们云游四方，对虫的生命形态，生存方式进行研究，并接受人们的委托，解决可能是由虫引起的怪异事件。银古，正是他们的一员。
                        银古，他出入穷乡僻壤去追寻虫的足迹。银古穿越草木的意识，找到结症，予以化解。他一路走来，与少年天才画师、写虫之卷的女孩，保佑一方平安的大师……惺惺相惜，又黯然别离。在这里，共存与牺牲，始终是最伤感的话题……
                    </div>
                    <div className="browse-book-option">
                    <span className="browse-book-button">
                        <button className="pay-now">
                            立即购买
                        </button>
                    </span>
                        <span className="browse-book-button">
                        <Button className="add-to-favourite" onClick={this.sendMessage}>
                            加入收藏
                        </Button>
                    </span>
                        <span className="browse-book-button">
                        <button className="add-to-cart" onClick={this.sendMessage}>
                            加入购物车
                        </button>
                    </span>
                    </div>
                </div>
            </div>
        );
    }
}

export default BookDetails;