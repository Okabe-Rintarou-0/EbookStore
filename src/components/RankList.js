import React from 'react'
import {Image, Switch, Row, Table, Col} from "antd";
import ExpandedBookDetails from "../components/ExpandedBookDetails";
import {getRankedBooks} from "../service/bookService";

class RankList extends React.Component {

    static tableMode = 0;
    static graphMode = 1;

    constructor(props) {
        super(props);
        this.state = {
            books: [],
            mode: RankList.tableMode,
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

    renderRankGraph = () => (
        <Row align={"middle"} justify={"center"}>
            <Image
                width={750}
                src="http://localhost:8080/getImage?target=book"
            />
        </Row>
    );

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

    toggleMode = () => {
        this.setState({
            mode: this.state.mode === RankList.tableMode ? RankList.graphMode : RankList.tableMode,
        })
    };

    render() {
        return (
            <div>
                {
                    this.state.mode === RankList.tableMode ?
                        this.renderTable() :
                        this.renderRankGraph()
                }
                <Row justify={"end"} gutter={10}>
                    <Col>
                        <b>切换视图
                        </b>
                    </Col>
                    <Col>
                        <Switch onChange={this.toggleMode}/>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default RankList;