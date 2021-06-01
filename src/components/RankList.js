import React from 'react'
import {Table} from "antd";
import ExpandedBookDetails from "../components/ExpandedBookDetails";
import {getRankedBooks} from "../service/bookService";

class RankList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            books: [],
        }
    }

    preHandleBooks = books => {
        books.map((book, index) => {
            book.key = book.rank = index + 1;
            book.state = book.forSale ? '销售中' : '已下架';
        });
    };

    handleBooks = books => {
        this.preHandleBooks(books);
        this.setState({
            books: books,
        })
    };

    componentDidMount() {
        getRankedBooks(this.handleBooks);
    }

    renderTable = () => {
        const columns = [
            {
                title: '排名',
                dataIndex: 'rank',
                sorter: (a, b) => a.rank - b.rank,
                sortDirections: ['descend']
            },
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
                dataIndex: 'bookPrice',
            },
            {
                title: '库存',
                dataIndex: 'bookStock',
            },
            {
                title: '销量',
                dataIndex: 'sales',
            },
            {
                title: '状态',
                dataIndex: 'state'
            }
        ];
        return (
            <div>
                <Table columns={columns}
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

export default RankList;