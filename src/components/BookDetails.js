import React from "react";
import '../css/bookDetails.css'
import {Button, Card, Col, Image, Menu, message, Modal, Row, Select} from "antd";
import {getBookById, getConcernedBookInfo} from "../service/bookService";
import {addToCart} from "../service/cartSerivce";
import {addFavouriteBook} from "../service/favouriteService";

const {Meta} = Card;
const {Option} = Select;

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
            showConcernedBookInfo: false,
            tags: [],
            concernedBookInfo: [],
            readyToShowSelector: false,
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
            bookStock: data.bookStock,
            forSale: data.forSale,
            tags: data.bookTag.split(' '),
        }, () => {
        });
    };

    componentDidMount() {
        getBookById(this.props.bookId, this.handleBookInfo);
    }

    closeModal = () => {
        this.setState({
            showConcernedBookInfo: false,
            readyToShowSelector: false,
        })
    };

    handleConcernedInfo = data => {
        console.log(data);
        this.setState({
            concernedBookInfo: data,
            readyToShowSelector: true,
        })
    };

    showConcernedInfo = () => {
        this.setState({
            showConcernedBookInfo: true,
        });
        getConcernedBookInfo(this.state.bookTitle, "Tmall", this.handleConcernedInfo);
    };

    renderConcernedBooks = () => {
        let books = [];
        this.state.concernedBookInfo.map(book => {
            books.push((
                <Col span={8}>
                    <Card
                        hoverable
                        style={{width: 240}}
                        cover={<img alt="book"
                                    src={book.bookCover}/>}
                    >
                        <Meta title={"价格: " + book.bookPrice} description={"源网址: " + book.bookLink}/>
                        <a href={book.bookLink} target={'_blank'}>访问</a>
                    </Card>,
                </Col>
            ))
        });
        return books;
    };

    handleChange = value => {
        console.log("value", value);
        getConcernedBookInfo(this.state.bookTitle, value, this.handleConcernedInfo);
    };

    renderSelector = () => (
        this.state.readyToShowSelector ?
            <>
                <Select defaultValue="Jingdong" style={{width: 120}} onChange={this.handleChange}>
                    <Option value="Jingdong">Jingdong</Option>
                    <Option value="Tmall">Tmall</Option>
                    <Option value="Dangdang">Dangdang</Option>
                </Select>
            </> : null
    );

    renderConcernedBookInfoModal = () => {
        return this.state.showConcernedBookInfo ? (
            <Modal title="相关图书/周边"
                   width={'800px'}
                   visible={true}
                   onCancel={this.closeModal}
                   footer={
                       <Button key="back" onClick={this.closeModal}>
                       </Button>
                   }>
                <Row>
                    {this.renderConcernedBooks()}
                </Row>
                <Row>
                    {this.renderSelector()}
                </Row>
            </Modal>
        ) : null;
    };

    onAddToCart = (e) => {
        let text = e.target.innerText;
        message
            .loading(text + '中...', 1)
            .then(
                () => {
                    addToCart(this.props.bookId);
                }
            )
    };

    onAddToFavourite = e => {
        let text = e.target.innerText;
        message
            .loading(text + '中...', 1)
            .then(
                () => {
                    addFavouriteBook(this.props.bookId);
                }
            )
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
                    <div className="browse-book-stock">
                        <b>库存</b>
                        <span className="stock">{this.state.forSale ? `${this.state.bookStock}件` : '已下架'}</span>
                    </div>
                    <div className="browse-book-description">
                        <div className="author">{`作者：${this.state.bookAuthor}`}</div>
                        {this.state.bookDetails !== null ? (this.state.bookDetails.length > 250 ? this.state.bookDetails.slice(0, 250) + "..." : this.state.bookDetails) : null}
                    </div>
                    <div className="browse-book-option">
                    <span className="browse-book-button">
                        <button className="pay-now" onClick={this.showConcernedInfo}>
                            价位对比
                        </button>
                    </span>
                        <span className="browse-book-button">
                        <Button className="add-to-favourite" onClick={this.onAddToFavourite}>
                            加入收藏
                        </Button>
                    </span>
                        <span className="browse-book-button">
                        <Button className="add-to-cart" onClick={this.onAddToCart} disabled={!this.state.forSale}>
                            加入购物车
                        </Button>
                    </span>
                    </div>
                </div>
                {this.renderConcernedBookInfoModal()}
            </div>
        );
    }
}

export default BookDetails;