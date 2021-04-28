import React from "react";
import '../css/bookDetails.css'
import {Image, Dropdown, Menu, Button, message} from "antd";
import {getBookById} from "../service/bookService";

class BookDetails extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedRegion: '上海',
            bookTitle: null,
            bookDetails: null,
            bookDescription: null,
            bookPrice: null,
            bookCover: null,
        };
    }

    handleBookInfo = data => {
        this.setState({
            bookTitle: data.bookTitle,
            bookDetails: data.bookDetails,
            bookPrice: data.bookPrice,
            bookCover: data.bookCover,
            bookDescription: data.bookDescription,
            bookAuthor: data.bookAuthor,
        });
    };

    componentDidMount() {
        getBookById(this.props.bookId, this.handleBookInfo);
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
                    <Image src={this.state.bookCover} style={{width: '450px', height: '540px'}}/>
                </div>
                <div className="browse-book-info">
                    <div className="browse-book-title">{`《${this.state.bookTitle}》`}</div>
                    <div className="browse-book-labels">
                        <span className="book-label">奇幻</span>
                        <span className="book-label">治愈</span>
                        <span className="book-label">文艺</span>
                    </div>
                    <div className="browse-book-price">
                        <b>价格</b>
                        <span className="price">{`¥${this.state.bookPrice}`}</span>
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
                        <div className="author">{`作者：${this.state.bookAuthor}`}</div>
                        {this.state.bookDetails !== '' ? this.state.bookDetails : this.state.bookDescription}
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