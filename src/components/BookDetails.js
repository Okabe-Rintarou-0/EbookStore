import React from "react";
import '../css/bookDetails.css'
import {Image, Dropdown, Menu, Button, message} from "antd";
import {getBookById} from "../service/bookService";
import {addToCart} from "../service/orderService";
import {addFavouriteBook} from "../service/favouriteService";

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
            tags: [],
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
            tags: data.bookTag.split(' '),
        }, () => console.log(this.state.tags));
    };

    componentDidMount() {
        getBookById(this.props.bookId, this.handleBookInfo);
    }

    onAddToCart = (e) => {
        let text = e.target.innerText;
        message
            .loading(text + '中...', 1)
            .then(
                () => {
                    addToCart(this.props.bookId, () => {
                    });
                }
            )
            .then(() => {
                message.success(text + '成功！', 1)
            });
    };

    onAddToFavourite = e => {
        let text = e.target.innerText;
        message
            .loading(text + '中...', 1)
            .then(
                () => {
                    addFavouriteBook(this.props.bookId, () => {
                    });
                }
            )
            .then(() => {
                message.success(text + '成功！', 1)
            });
    };

    static
    defaultProps = {
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
                        <span className="book-tag">{this.state.tags[0]}</span>
                        <span className="book-tag">{this.state.tags[1]}</span>
                        <span className="book-tag">{this.state.tags[2]}</span>
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
                            分享本书
                        </button>
                    </span>
                        <span className="browse-book-button">
                        <Button className="add-to-favourite" onClick={this.onAddToFavourite}>
                            加入收藏
                        </Button>
                    </span>
                        <span className="browse-book-button">
                        <button className="add-to-cart" onClick={this.onAddToCart}>
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