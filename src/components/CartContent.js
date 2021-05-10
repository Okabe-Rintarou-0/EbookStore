import React from 'react'
import {Button, Col, Drawer, Form, Image, Input, Layout, Modal, Row, Select, Statistic, Table, Timeline} from "antd";
import {ClockCircleOutlined} from "@ant-design/icons";
import '../css/searchBar.css'
import {Redirect} from "react-router-dom";
import {deleteOrder, getAllOrders, modifyOrder, placeOrder} from "../service/orderService";
import {history} from "../utils/history";
import {getUserProperty} from "../service/userService";

const {Option} = Select;
const {Footer} = Layout;
const {Search} = Input;

class CartContent extends React.Component {

    static originalOrders;

    handleOrdersInfo = data => {
        console.log("orders: ", data);
        data.map((order, index) => {
            order.key = String(index);
        });
        this.setState({
            orders: data,
        }, () => {
            CartContent.originalOrders = [...data];
        });
    };

    componentDidMount() {
        getAllOrders(this.handleOrdersInfo);
    }


    constructor(props) {
        super(props);
        this.state = {
            selectedRowKeys: [], // Check here to configure the default column
            loading: false,
            totalPrice: 0,
            searchValue: "",
            showDeleteModal: false,
            showPaymentModal: false,
            editRowKey: -1,
            redirect: false,
            orders: [],
        };
    }

    render() {
        return (
            <>
                {this.renderSearchBar()}
                {this.renderTable()}
                {this.renderDrawer()}
                {this.renderDeleteModal()}
                {this.renderPaymentModal()}
                {this.renderRedirect()}
                {this.renderFooter()}
            </>
        );
    }

    filter = (value) => {
        value = value.toLowerCase();
        let orders = [...CartContent.originalOrders];
        this.setState({
            orders: orders.filter((element, index) => {
                return (element.book_title.toLowerCase().indexOf(value) !== -1 || element.order_address.toLowerCase().indexOf(value) !== -1 ||
                    element.order_receiver.toLowerCase().indexOf(value) !== -1 ||
                    element.order_state.indexOf(value) !== -1) || this.state.selectedRowKeys.indexOf((index + 1).toString()) !== -1;
            }),
        });
    };

    onSearch = (value) => {
        this.filter(value);
        this.setState({
            searchValue: value,
        })
    };

    onSelectChange = (selectedRowKeys, selectedRows) => {
        console.log(selectedRowKeys);
        this.setState({selectedRowKeys});
    };

    calcTotalPrice = () => {
        let totalPrice = 0;
        for (let i = 0; i < this.state.orders.length; ++i) {
            let state = this.state.orders[i].order_state;
            totalPrice += state === '未购买' ? Number(this.state.orders[i].book_price) : 0;
        }
        return totalPrice;
    };

    onSelectAll = (selected) => {
        selected ? this.setState({totalPrice: this.calcTotalPrice()}) : this.setState({totalPrice: 0});
    };

    deleteSelections = () => {
        for (let i = 0; i < CartContent.originalOrders.length; ++i) {
            if (this.state.selectedRowKeys.indexOf(CartContent.originalOrders[i].key) !== -1)
                CartContent.originalOrders.splice(i, 1);
        }

        this.setState(
            {
                totalPrice: 0,
                selectedRowKeys: [],
            });
        this.filter(this.state.searchValue);
    };

    onDeleteClick = () => {
        if (this.state.selectedRowKeys.length)
            this.setState(
                {
                    showDeleteModal: true,
                });
    };

    handleUserProperty = data => {
        this.setState({
            property: data,
            showPaymentModal: true,
        })
    };

    onPaymentClick = () => {
        if (this.state.selectedRowKeys.length) {
            getUserProperty(this.handleUserProperty)
        } else {
            Modal.error({
                title: '支付失败',
                content: '请选中书籍再点击支付按键。',
            });
        }
    };

    onPaymentCancel = () => {
        this.setState({
            showPaymentModal: false,
        });
    };
    onDeleteCancel = () => {
        this.setState({
            showDeleteModal: false,
        });
    };

    onPaymentOk = () => {
        this.state.selectedRowKeys.map(key => {
            console.log("place order: ", this.state.orders[key].order_id);
            placeOrder(this.state.orders[key].order_id)
        });
        this.setState({
            showPaymentModal: false,
            redirect: true,
        });
    };

    onDeleteOk = () => {
        // this.deleteSelections();
        this.state.selectedRowKeys.map(key => {
            console.log("key: ", this.state.orders[key].order_id);
            deleteOrder(this.state.orders[key].order_id, () => {
            });
        });
        setTimeout(() => {
            history.go(0)
        }, 300);
        this.setState({
            showDeleteModal: false,
        });
    };

    onModifyOrder = (order, values) => {
        console.log(this.state.editRowKey);
        console.log(this.state.orders[this.state.editRowKey]);
        console.log("order: ", order);
        modifyOrder(order.order_id, values.receiver, values.address, values.phoneNumber, () => {
        });
        this.setState({
            editRowKey: -1,
        }, () => {
            history.go(0);
        });
    };

    onClose = () => {
        this.setState({
            editRowKey: -1,
        });
    };

    onActionClick = (key) => {
        console.log("key:", key);
        this.setState({
            editRowKey: key
        })
    };

    adjustTotalPrice(delta) {
        this.setState({totalPrice: this.state.totalPrice + delta});
    }

    onSelect = (record, selected) => {
        let delta = selected ? Number(record['book_price']) : -Number(record['book_price']);
        this.adjustTotalPrice(delta);
    };

    renderSearchBar = () => {
        return (
            <Row align={"center"} style={{margin: '20px'}}>
                <Col span={8}>
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

    renderDeleteModal = () => {
        if (this.state.showDeleteModal)
            return (
                <Modal title="提示" visible={true} onOk={this.onDeleteOk} onCancel={this.onDeleteCancel} okText={"是"}
                       cancelText={"否"}>
                    <p>是否删除选中的商品？</p>
                </Modal>);
        else return null;
    };

    renderPaymentModal = () => {
        if (this.state.showPaymentModal) {
            return (
                <Modal title="提示" visible={true} onOk={this.onPaymentOk} onCancel={this.onPaymentCancel}
                       okText={"确认支付"}
                       cancelText={"取消"}>
                    <b>请选择支付方式:&nbsp;</b>
                    <Select defaultValue="微信" style={{width: 120}}>
                        <Option value="wechat">微信</Option>
                        <Option value="alipay">支付宝</Option>
                        <Option value="credit-card">信用卡</Option>
                        <Option value="change">零钱</Option>
                    </Select>
                    <br/>
                    <b>账户余额:{this.state.property}元</b>
                </Modal>);
        } else return null;
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            this.setState({
                redirect: false,
            });
            return (
                <Redirect to={"/status"}/>
            );
        } else return null;
    };

    renderDrawer = () => {
        if (this.state.editRowKey !== -1) {
            return (
                <Drawer
                    title="修改信息"
                    width={720}
                    onClose={this.onClose}
                    visible={this.state.editRowKey !== -1}
                    bodyStyle={{paddingBottom: 80}}
                >
                    <Form layout="vertical" hideRequiredMark
                          onFinish={this.onModifyOrder.bind(this, this.state.orders[this.state.editRowKey])}>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    name="receiver"
                                    label="收货人"
                                    rules={[{required: true, message: 'Please enter user name'}]}
                                >
                                    <Input placeholder="请输入收货人姓名。"/>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item
                                    name="address"
                                    label="收货人地址"
                                    rules={[{required: true, message: 'Please enter user name'}]}
                                >
                                    <Input placeholder="请输入收货人地址。"/>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    name="phoneNumber"
                                    label="联系方式"
                                    rules={[{required: true, message: 'Please enter user name'}]}
                                >
                                    <Input placeholder="请输入收货人联系方式。"/>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={24}>
                                <Form.Item
                                    name="description"
                                    label="备注"
                                    rules={[
                                        {
                                            required: false,
                                            message: 'please enter url description',
                                        },
                                    ]}
                                >
                                    <Input.TextArea rows={4} placeholder="如有特殊需求，请填写备注。"/>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row justify={'space-between'}>
                            <Col span={4}>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit">
                                        确认修改
                                    </Button>
                                </Form.Item>
                            </Col>
                            <Col span={4}>
                                <Form.Item>
                                    <Button type="default" onClick={this.onClose}>
                                        取消
                                    </Button>
                                </Form.Item>
                            </Col>
                        </Row>
                    </Form>
                </Drawer>
            );
        }
    };

    renderFooter = () => {
        return (
            <Footer style={{backgroundColor: 'antiquewhite'}}>
                <Row justify={"end"}>
                    <Col span={2}>
                        <Statistic title="总价" value={this.state.totalPrice} precision={2}/>
                    </Col>
                    <Col span={2}>
                        <Button type="primary" htmlType="submit"
                                onClick={this.onDeleteClick}
                                style={{
                                    backgroundColor: '#fb7d38',
                                    border: '#FB6825 1px solid',
                                    height: '50px',
                                    width: '100%'
                                }}>
                            删除
                        </Button>
                    </Col>
                    <Col span={2}>
                        <Button type="primary" htmlType="submit"
                                onClick={this.onPaymentClick}
                                style={{
                                    backgroundColor: '#fbb36b',
                                    border: 'orange 1px solid',
                                    height: '50px',
                                    width: '100%',
                                    margin: "0 20px"
                                }}>
                            支付
                        </Button>
                    </Col>
                </Row>
            </Footer>
        );
    };

    renderTable = () => {
        const columns = [
            {
                title: '商品名称',
                dataIndex: 'book_title',
            },
            {
                title: '价格',
                sorter: {
                    compare: (a, b) => a.bookPrice - b.bookPrice,
                    multiple: 2,
                },
                dataIndex: 'book_price',

            },
            {
                title: '收货人',
                dataIndex: 'order_receiver',
            },
            {
                title: '联系方式',
                dataIndex: 'order_tel',
            },
            {
                title: '地址',
                dataIndex: 'order_address',
            },
            {
                title: '商品状态',
                defaultSortOrder: 'descend',
                sorter: {
                    multiple: 1,
                    compare: (a, b) => {
                        return a.order_state.localeCompare(b.order_state);
                    },
                },
                dataIndex: 'order_state',
            },
            {
                title: '',
                render: (data) => {
                    return (
                        <span>
                            {/* eslint-disable-next-line no-script-url */}
                            <a href='javascript:void(0)' onClick={this.onActionClick.bind(this, data.key)}>修改信息</a>
                        </span>
                    );
                },
            }
        ];

        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
            onSelect: this.onSelect,
            onSelectAll: this.onSelectAll,
            getCheckboxProps: (record) => ({
                disabled: record.order_state === '已购买', // Column configuration not to be checked
            }),
        };
        return (
            <Table columns={columns}
                   dataSource={this.state.orders}
                   scroll={{y: 350}}
                   rowSelection={rowSelection}
                   expandable={{
                       expandedRowRender: (record) => {
                           return (
                               <Row align={"center"}>
                                   <Col span={4}>
                                       <Image style={{height: '150px', width: '120px'}} src={record.book_cover}>
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
}

export default CartContent;