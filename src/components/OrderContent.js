import React from 'react'
import {Col, Image, Row, Table, Timeline} from 'antd';
import {deleteFavouriteBook, getFavouriteBooks, moveToCart} from "../service/favouriteService";
import {Typography, Divider, Tag} from 'antd';
import {history} from "../utils/history";
import {ClockCircleOutlined} from "@ant-design/icons";
import {getAllOrders} from "../service/orderService";

const {Title, Paragraph, Text, Link} = Typography;

class OrderContent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            orders: [],
        }
    }

    handleAllOrders = data => {
        console.log(data)
        let itemList = [];
        data.map((dataItem, index) => {
            dataItem.orders.map(order => {
                let book = order.book;
                let item = {
                    orderReceiver: dataItem.orderReceiver,
                    orderTel: dataItem.orderTel,
                    orderAddress: dataItem.orderAddress,
                    bookTitle: '',
                    bookPrice: '',
                    purchaseNumber: '',
                    bookCover: '',
                    key: itemList.length
                };
                console.log("book", book);
                item.bookPrice = book.bookPrice;
                item.bookTitle = book.bookTitle;
                item.bookCover = book.bookCover;
                item.purchaseNumber = order.purchaseNumber;
                itemList.push(item);
            });
        });
        console.log("itemList", itemList);
        this.setState({
            orders: itemList,
        });
    };

    componentDidMount() {
        getAllOrders(this.handleAllOrders);
    }

    renderTable = () => {
        const columns = [
            {
                title: '商品名称',
                dataIndex: 'bookTitle',
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
                title: '收货人',
                dataIndex: 'orderReceiver',
            },
            {
                title: '联系方式',
                dataIndex: 'orderTel',
            },
            {
                title: '地址',
                dataIndex: 'orderAddress',
            },
            {
                title: '数量',
                dataIndex: 'purchaseNumber',
            },
        ];
        return (
            <Table columns={columns}
                   dataSource={this.state.orders}
                   scroll={{y: 535}}
                   expandable={{
                       expandedRowRender: (record) => {
                           return (
                               <Row align={"center"}>
                                   <Col span={4}>
                                       <Image style={{height: '150px', width: '120px'}} src={record.bookCover}>
                                       </Image>
                                   </Col>
                                   <Col span={8}>
                                       <Timeline>
                                           <Timeline.Item color="green">用户下单 2021/3/17 19:40</Timeline.Item>
                                           <Timeline.Item color="green">商家发货 2021/3/17 20:40</Timeline.Item>
                                           <Timeline.Item dot={<ClockCircleOutlined className="timeline-clock-icon"/>}>
                                               快递员派送中 2021/3/18 15:20
                                           </Timeline.Item>
                                           <Timeline.Item color="red">确认收货</Timeline.Item>
                                       </Timeline>
                                   </Col>
                               </Row>
                           );
                       },
                       rowExpandable: record => record.name !== 'Not Expandable',
                   }}
            />
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

export default OrderContent;