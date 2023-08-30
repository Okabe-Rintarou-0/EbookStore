import React from 'react'
import {Col, Button, Row, Table, DatePicker} from "antd";
import {getAllOrdersForManager, searchOrdersForManager} from "../service/orderService";
import ExpandedOrderItems from "../components/ExpandedOrderItems";

const {RangePicker} = DatePicker;

class OrderManagementList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            orders: [],
        }
    }

    preHandleOrders = orders => {
        orders.map((order, index) => {
            order.key = index;
        });
    };

    handleOrders = orders => {
        this.preHandleOrders(orders);
        this.setState({
            orders: orders,
            selectedRowKeys: [], // Check here to configure the default column
            editRowKey: -1,
        })
    };

    handleSearchOrders = orders => {
        this.preHandleOrders(orders);
        this.setState({
            orders: orders,
        })
    };

    onRangeChange = (date, startNEndDates) => {
        this.setState({
            startNEndDates: startNEndDates,
        });
    };

    onSearch = () => {
        searchOrdersForManager(this.state.startNEndDates, this.handleSearchOrders);
    };

    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys});
    };

    componentDidMount() {
        getAllOrdersForManager(this.handleOrders)
    }

    renderTable = () => {
        const columns = [
            {
                title: '订单号',
                dataIndex: 'orderId',
            },
            {
                title: '收货人',
                dataIndex: 'orderReceiver',
            },
            {
                title: '收货地址',
                dataIndex: 'orderAddress',
            },
            {
                title: '下单时间',
                dataIndex: 'orderTime',
            },
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
                <Row style={{margin: '30px'}}>
                    <Col>
                        <RangePicker onChange={this.onRangeChange} showTime showToday/>
                    </Col>
                    <Col>
                        <Button onClick={this.onSearch} type={"primary"}>查询</Button>
                    </Col>
                </Row>
                <Table columns={columns}
                       rowSelection={rowSelection}
                       dataSource={this.state.orders}
                       scroll={{y: 485}}
                       expandable={{
                           expandedRowRender: (order) => {
                               return (
                                   <ExpandedOrderItems orderItems={order.orders}/>
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

export default OrderManagementList;