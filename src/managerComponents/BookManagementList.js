import React from 'react'
import {Col, message, Row, Select, Table} from "antd";
import {deleteBooks, getBooks, getBooksByKeyword, putOnSale, undercarriageBooks} from "../service/bookService";
import ExpandedBookDetails from "../components/ExpandedBookDetails";
import Button from "antd/es/button";
import {history} from "../utils/history";
import Search from "antd/es/input/Search";
import CsvUpload from "../components/CsvUpload";
import EditableTableItem from "../components/EditableTableItem";
import {formBookCsvContent} from "../utils/fileUtils";
import BookModifyModal from "../components/BookModifyModal";

const {Option} = Select;

class BookManagementList extends React.Component { ///todo : add modification functions

    static viewMode = 0;
    static formMode = 1;

    constructor(props) {
        super(props);
        this.state = {
            books: [],
            addedBooks: [],
            mode: BookManagementList.viewMode,
            showModal: false,
            bookToModify: null,
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
            return book;
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
            return key;
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
            return key;
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

    toggleMode = () => {
        this.setState({
            selectedRowKeys: [],
            mode: (this.state.mode + 1) % 2,
        })
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
        switch (this.state.mode) {
            case BookManagementList.viewMode:
                return this.renderTableInViewMode();
            case BookManagementList.formMode:
                return this.renderTableInFormMode();
            default:
                return null;
        }
    };

    addOne = () => {
        let emptyItem = {
            bookTitle: '',
            bookAuthor: '',
            bookPrice: '',
            bookStock: '',
            bookType: '',
            bookDescription: '',
            bookDetails: '',
            bookCover: '',
            forSale: true,
            bookTag: '',
            key: this.state.addedBooks.length ? this.state.addedBooks[this.state.addedBooks.length - 1].key + 1 : 0,
        };
        this.setState({
            addedBooks: [...this.state.addedBooks, emptyItem],
        })
    };

    handleDeleteFormBooks = () => {
        console.log(this.state.selectedRowKeys);
        let filter = this.state.addedBooks.filter(book => {
            if (this.state.selectedRowKeys.indexOf(book.key) === -1) {
                return true;
            }
        });
        this.setState({
            addedBooks: filter,
            selectedRowKeys: []
        })
    };

    formCsv = () => {
        let addedBooks = this.state.addedBooks;
        let len = addedBooks.length;
        if (len === 0) {
            message.warn("无法生成Csv文件，因为当前表格为空！");
            return;
        }
        for (let i = 0; i < len; ++i) {
            let book = addedBooks[i];
            if (book.bookTitle === '' || book.bookAuthor === '' || book.bookPrice === '' || book.bookTag === '' || book.bookStock === ''
                || book.bookDetails === '' || book.bookDescription === '' || book.bookCover === '') {
                message.warn("书籍的任何一项都不得为空！");
                return;
            }
        }
        formBookCsvContent(this.state.addedBooks);
    };

    setter = (target, object, value) => {
        switch (object) {
            case "bookTitle":
                target.bookTitle = value;
                break;
            case "bookPrice":
                target.bookPrice = value;
                break;
            case "bookStock":
                target.bookStock = value;
                break;
            case "bookDescription":
                target.bookDescription = value;
                break;
            case "bookDetails":
                target.bookDetails = value;
                break;
            case "bookTag":
                target.bookTag = value;
                break;
            case "forSale":
                target.forSale = value;
                break;
            case "bookAuthor":
                target.bookAuthor = value;
                break;
            case "bookCover":
                target.bookCover = value;
                break;
            case "bookType":
                target.bookType = value;
                break;
            default:
                break;
        }
    };

    onSelectState = (book, value) => {
        book.forSale = value === '是';
    };

    renderTableInFormMode = () => {
        const columns = [
            {
                title: '书名',
                dataIndex: 'bookTitle',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookTitle")}
                    />
                }
            },
            {
                title: '书籍封面',
                dataIndex: 'bookCover',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookCover")}/>
                }
            },
            {
                title: '作者',
                dataIndex: 'bookAuthor',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookAuthor")}
                    />
                }
            },
            {
                title: '价格',
                dataIndex: 'bookPrice',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookPrice")}
                    />
                }
            },
            {
                title: '库存',
                dataIndex: 'bookStock',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookStock")}
                    />
                }
            },
            {
                title: '概述',
                dataIndex: 'bookDescription',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookDescription")}
                    />
                }
            },
            {
                title: '详情',
                dataIndex: 'bookDetails',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookDetails")}
                    />
                }
            },
            {
                title: '标签',
                dataIndex: 'bookTag',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookTag")}
                    />
                }
            },
            {
                title: '书籍类型',
                dataIndex: 'bookType',
                render: (text, book) => {
                    return <EditableTableItem
                        setter={this.setter.bind(this, book, "bookType")}
                    />
                }
            },
            {
                title: '是否在销售中',
                dataIndex: 'forSale',
                render: (text, book) => {
                    return <Select onSelect={this.onSelectState.bind(this, book)} defaultValue="是" style={{width: 120}}>
                        <Option value="是">是</Option>
                        <Option value="否">否</Option>
                    </Select>
                }
            }
        ];
        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        return (
            <div>
                <Table columns={columns}
                       rowSelection={rowSelection}
                       dataSource={this.state.addedBooks}
                       scroll={{y: 390}}
                       footer={
                           () =>
                               <Row justify="end" gutter={20} align={"middle"}>
                                   <Col>
                                       <Button onClick={this.toggleMode}>
                                           切换到总览模式
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.formCsv}>
                                           生成.csv文件
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.addOne} type="primary">
                                           添加
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.handleDeleteFormBooks} type="primary" danger>
                                           删除
                                       </Button>
                                   </Col>
                               </Row>}
                />
            </div>
        );
    };

    showModifyModal = (bookToModify, e) => {
        e.preventDefault();
        this.setState({
            showModal: true,
            bookToModify: bookToModify,
        })
    };

    closeModal = () => {
        this.setState({
            showModal: false,
        })
    };

    renderModal = (bookToModify) => {
        if (this.state.showModal === false) return null;
        else return <BookModifyModal closeFunc={this.closeModal} bookToModify={bookToModify}/>
    };

    renderTableInViewMode = () => {
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
            },
            {
                title: '修改信息',
                dataIndex: 'modify',
                render: (_, book) => (
                    <a onClick={this.showModifyModal.bind(this, book)}>修改信息</a>
                ),
            }
        ];
        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        return (
            <div>
                {this.renderSearchBar()}
                <Table columns={columns}
                       rowSelection={rowSelection}
                       dataSource={this.state.books}
                       scroll={{y: 390}}
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
                               <Row justify="end" gutter={20} align={"middle"}>
                                   <Col>
                                       <Button onClick={this.toggleMode}>
                                           切换到在线生成模式
                                       </Button>
                                   </Col>
                                   <CsvUpload/>
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
                {this.renderModal(this.state.bookToModify)}
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