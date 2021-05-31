import React from 'react'
import {Col, Image, message, Row, Table,} from "antd";
import {deleteBooks, getBooks, getBooksByKeyword, putOnSale, undercarriageBooks} from "../service/bookService";
import ExpandedBookDetails from "../components/ExpandedBookDetails";
import Button from "antd/es/button";
import {history} from "../utils/history";
import Search from "antd/es/input/Search";

class BookManagementList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            books: [],
        }
    }

    onSearch = (value) => {
        let keyword = value.toLowerCase();
        getBooksByKeyword(keyword, this.handleSearchResults);
    };

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

    preHandleBooks = books => {
        books.map((book, index) => {
            book.key = index;
            book.state = book.forSale ? '销售中' : '已下架';
        });
    };

    handleSearchResults = books => {
        this.preHandleBooks(books);
        this.setState({
            books: books,
        });
    };

    handleBooks = books => {
        this.preHandleBooks(books);
        this.setState({
            books: books,
            selectedRowKeys: [], // Check here to configure the default column
            editRowKey: -1,
        })
    };

    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys});
    };

    componentDidMount() {
        getBooks(this.handleBooks)
    }

    handleDeleteBooks = () => {
        let bookIdList = [];
        let selectedRowKeys = this.state.selectedRowKeys;
        selectedRowKeys.map((key) => {
            bookIdList.push(this.state.books[key].bookId);
        });
        deleteBooks(bookIdList, msg => {
            if (msg.status > 0)
                message.success(msg.message);
            else
                message.warn(msg.message);
        });
        setTimeout(() => {
            history.go(0);
        }, 500);
    };

    handlePutOnSale = () => {
        let bookIdList = [];
        let selectedRowKeys = this.state.selectedRowKeys;
        selectedRowKeys.map((key) => {
            bookIdList.push(this.state.books[key].bookId);
        });
        putOnSale(bookIdList, msg => {
            if (msg.status > 0)
                message.success(msg.message);
            else
                message.warn(msg.message);
        });
        setTimeout(() => {
            history.go(0);
        }, 500);
    };

    handleUndercarriageBooks = () => {
        let bookIdList = [];
        let selectedRowKeys = this.state.selectedRowKeys;
        selectedRowKeys.map((key) => {
            bookIdList.push(this.state.books[key].bookId);
        });
        console.log(bookIdList);
        undercarriageBooks(bookIdList, msg => {
            if (msg.status > 0)
                message.success(msg.message);
            else
                message.warn(msg.message);
        });
        setTimeout(() => {
            history.go(0);
        }, 500);
    };

    renderTable = () => {
        const columns = [
            {
                title: '商品名称',
                dataIndex: 'bookTitle',
            },
            {
                title: '作者',
                dataIndex: 'bookAuthor',
            },
            {
                title: '价格',
                sorter: {
                    compare: (a, b) => a.bookPrice - b.bookPrice,
                    multiple: 2,
                },
                dataIndex: 'bookPrice',
            },
            {
                title: '库存',
                dataIndex: 'bookStock',
            },
            {
                title: '状态',
                dataIndex: 'state'
            }
        ];
        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
            onSelect: this.onSelect,
            onSelectAll: this.onSelectAll,
        };
        return (
            <div>
                {this.renderSearchBar()}
                <Table columns={columns}
                       rowSelection={rowSelection}
                       dataSource={this.state.books}
                       scroll={{y: 473}}
                       expandable={{
                           expandedRowRender: (book) => {
                               return (
                                   <ExpandedBookDetails
                                       bookCover={book.bookCover}
                                       bookDescription={book.bookDescription}
                                       bookDetails={book.bookDetails}
                                   />
                               );
                           },
                           rowExpandable: record => record.name !== 'Not Expandable',
                       }}
                       footer={
                           () =>
                               <Row justify="end" gutter={20}>
                                   <Col>
                                       <Button onClick={this.handlePutOnSale} type="primary" danger>
                                           上架
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.handleUndercarriageBooks} type="primary" danger>
                                           下架
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.handleDeleteBooks} type="primary" danger>
                                           删除
                                       </Button>
                                   </Col>
                               </Row>}
                />
            </div>
        );
    };

    render() {
        return (
            <>
                {this.renderTable()}
            </>
        )
    }
}

export default BookManagementList;