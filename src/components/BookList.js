import React from "react";
import BookPreview from "./BookPreview";
import {Col, Input, Pagination, Row} from "antd";
import 'antd/dist/antd.css'
import {getBooksByKeyword, getBooksByPage, searchBooksByTags} from "../service/bookService";
import {scrollBackToTop} from "../utils/auxfunc";
import EditableTagGroup from "./EditableTagGroup";
import Button from "antd/es/button";

const {Search} = Input;

class BookList extends React.Component {

    state = {
        books: [],
        pageIndex: 1,
        totalPage: 1,
    };

    onSearch = (value) => {
        let keyword = value.toLowerCase();
        if (keyword.length === 0) {
            let tags = this.refs.tags.state.tags;
            if (tags.length === 0)
                getBooksByPage(0, this.handleBooksInfo);
            else {
                console.log(tags);
                searchBooksByTags(tags, this.handleSearch)
            }
        } else
            getBooksByKeyword(keyword, this.handleSearch);
    };

    handleSearch = books => {
        this.setState({
            books: books,
        });
    };

    handleBooksInfo = res => {
        this.setState({
            books: res.data.books,
            totalPage: res.data.total,
        });
    };

    componentDidMount() {
        getBooksByPage(0, this.handleBooksInfo);
    }


    renderSearchBar = () => {
        return (
            <>
                <Row align={"center"}>
                    <Col span={12} style={{marginTop: '20px'}}>
                        <Search
                            placeholder="请输入搜索关键词"
                            allowClear
                            enterButton="Search"
                            size="large"
                            onSearch={this.onSearch}
                        />
                    </Col>
                </Row>
                <Row align={"center"}>
                    <Col span={12}>
                        <EditableTagGroup ref={"tags"} maxNum={3}/>
                    </Col>
                </Row>
            </>
        );
    };

    componentDidUpdate(prevProps, prevState, snapshot) {
        scrollBackToTop();
    }

    renderBooks = () => {
        let renderContent = [];
        for (let i = 0; i < this.state.books.length; ++i) {
            renderContent.push(
                <Col span={8}>
                    <BookPreview data={this.state.books[i]}/>
                </Col>
            );
        }

        return renderContent;
    };

    handlePageChange = pageIndex => {
        getBooksByPage(pageIndex - 1, this.handleBooksInfo);
        this.setState({
            pageIndex: pageIndex,
        })
    };

    render() {
        return (
            <>
                {this.renderSearchBar()}
                <Row>
                    {this.renderBooks()}
                </Row>
                <Row justify={"end"}>
                    <Col span={4}>
                        <Pagination simple current={this.state.pageIndex} defaultCurrent={1}
                                    total={this.state.totalPage} pageSize={15}
                                    style={{marginBottom: '20px'}} onChange={this.handlePageChange}/>
                    </Col>
                </Row>
            </>
        );
    }
}

export default BookList;