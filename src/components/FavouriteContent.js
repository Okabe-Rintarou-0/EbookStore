import React from 'react'
import {Col, Image, Row, Table} from 'antd';
import {deleteFavouriteBook, getFavouriteBooks, moveToCart} from "../service/favouriteService";
import {Typography, Tag} from 'antd';
import {history} from "../utils/history";

const {Title, Paragraph} = Typography;

class FavouriteContent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            favourites: [],
        }
    }

    onMoveToCart = (key) => {
        console.log("key:", this.state.favourites[key].book_id);
        moveToCart(this.state.favourites[key].book_id);
        history.go(0);
    };

    onDeleteFavouriteBook = key => {
        console.log("key:", this.state.favourites[key].book_id);
        deleteFavouriteBook(this.state.favourites[key].book_id, () => {
        });
        history.go(0);
    };

    handleFavouriteBooks = data => {
        data.map(
            (element, index) => {
                element.key = String(index);
            }
        );
        this.setState({
            favourites: data,
        })
    };

    componentDidMount() {
        getFavouriteBooks(this.handleFavouriteBooks);
    }

    renderTable = () => {
        const columns = [
            {
                title: '书籍名称',
                dataIndex: 'book_title',
                key: 'book_title',
            },
            {
                title: '书籍价格',
                dataIndex: 'book_price',
                key: 'book_price',
            },
            {
                title: '书籍标签',
                dataIndex: 'book_tag',
                key: 'book_tag',
                render: (data) => {
                    let tags = data.split(' ');
                    let tagList = [];
                    tags.map(tag => {
                        tagList.push((
                            <Tag color={"green"}>
                                {tag}
                            </Tag>
                        ))
                    });
                    return tagList;
                }
            },
            {
                title: '',
                render: (data) => {
                    return (
                        <span>
                              <a href='javascript:void(0)' onClick={this.onMoveToCart.bind(this, data.key)}>加入购物车</a>
                         </span>
                    );
                },
            },
            {
                title: '',
                render: (data) => {
                    return (
                        <span>
                              <a href='javascript:void(0)'
                                 onClick={this.onDeleteFavouriteBook.bind(this, data.key)}>删除</a>
                         </span>
                    );
                },
            },
        ];
        return (
            <Table dataSource={this.state.favourites}
                   columns={columns}
                   scroll={{y: 535}}
                   expandable={{
                       expandedRowRender: (record) => {
                           return (
                               <Row align={"center"}>
                                   <Col span={4}>
                                       <Image style={{height: '150px', width: '120px'}} src={record.book_cover}>
                                       </Image>
                                   </Col>
                                   <Col span={8}>
                                       <Typography>
                                           <Title level={3}>
                                               书籍详情
                                           </Title>
                                           <Paragraph>
                                               {record.book_details.length === 0 ? record.book_description : record.book_details}
                                           </Paragraph>
                                       </Typography>
                                   </Col>
                               </Row>
                           );
                       },
                       rowExpandable: record => record.name !== 'Not Expandable',
                   }}
            />
        )
    };

    render() {
        return (
            <>
                {this.renderTable()}
            </>
        )
    }
}

export default FavouriteContent;