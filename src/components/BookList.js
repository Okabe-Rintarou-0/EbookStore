import React from "react";
import BookPreview from "./BookPreview";
import {Col, Input, Pagination, Row, Modal, Button} from "antd";
import 'antd/dist/antd.css'
import {getBooks, getBooksByKeyword} from "../service/bookService";
import BookCarousel from "./Carousel";
import {scrollBackToTop} from "../utils/auxfunc";

const {Search} = Input;

class BookList extends React.Component {

    state = {
        books: [],
        pageIndex: 1,
    };

    onSearch = (value) => {
        let keyword = value.toLowerCase();
        getBooksByKeyword(keyword, this.handleBooksInfo);
    };

    handleBooksInfo = data => {
        this.setState({
            books: data,
        });
    };

    componentDidMount() {
        getBooks(this.handleBooksInfo);
    }


    renderSearchBar = () => {
        return (
            <Row align={"center"}>
                <Col span={12} style={{margin: '20px'}}>
                    <Search
                        placeholder="请输入搜索关键词"
                        allowClear
                        enterButton="Search"
                        size="large"
                        onSearch={this.onSearch}
                    />
                </Col>
            </Row>
        );
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        scrollBackToTop();
    }

    renderBooks = () => {
        let renderContent = [];
        for (let i = 15 * (this.state.pageIndex - 1); (i < this.state.books.length && i < this.state.pageIndex * 15); ++i)
            renderContent.push(
                <Col span={8}>
                    <BookPreview data={this.state.books[i]}/>
                </Col>
            );

        return renderContent;
    };

    handlePageChange = pageIndex => {
        this.setState({
            pageIndex: pageIndex,
        })
    };

    render() {
        return (
            <>
                {this.renderSearchBar()}
                {/*{this.renderCarousel()}*/}
                <Row>
                    {this.renderBooks()}
                </Row>
                <Row justify={"end"}>
                    <Col span={4}>
                        <Pagination simple current={this.state.pageIndex} defaultCurrent={1}
                                    total={this.state.books.length} pageSize={15}
                                    style={{marginBottom: '20px'}} onChange={this.handlePageChange}/>
                    </Col>
                </Row>
            </>
        );
    }
}

export default BookList;