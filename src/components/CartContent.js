import React from 'react'
import {Table, Image, Row, Col, Timeline, Input, Statistic, Button, Layout, Modal, Form, Drawer, Select} from "antd";
import BookSrc from '../assets/books/CSAPP.jpg'
import {ClockCircleOutlined} from "@ant-design/icons";
import '../css/searchBar.css'
import {Redirect} from "react-router-dom";

const {Option} = Select;
const {Footer} = Layout;
const {Search} = Input;
const data = [
    {
        key: '1',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "有货",
        type: '科学'
    },
    {
        key: '2',
        name: '数据结构',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '3',
        name: '数据结构',
        receiver: "孤独の観測者",
        price: 134,
        phoneNumber: '10086',
        address: "复旦大学",
        state: "有货",
        type: '科学'
    },
    {
        key: '4',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '5',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '6',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '7',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '8',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '9',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '10',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学徐汇校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '11',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学徐汇校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '12',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "复旦大学",
        state: "有货",
        type: '科学'
    },
    {
        key: '13',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '14',
        name: 'CSAPP',
        receiver: "孤独の観測者",
        price: 124,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "已购",
        type: '科学'
    },
    {
        key: '15',
        name: '虫师',
        receiver: "孤独の観測者",
        price: 30,
        phoneNumber: '10086',
        address: "上海交通大学闵行校区",
        state: "有货",
        type: '漫画'
    },
];

class CartContent extends React.Component {

    static originalData;

    constructor(props) {
        super(props);
        this.state = {
            selectedRowKeys: [], // Check here to configure the default column
            loading: false,
            data: data,
            totalPrice: 0,
            searchValue: "",
            showDeleteModal: false,
            showPaymentModal: false,
            editRowKey: -1,
            redirect: false,
        };
        CartContent.originalData = data;
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

    start = () => {
        this.setState({loading: true});
        // ajax request after empty completing
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
        }, 1000);
    };

    filter = (value) => {
        value = value.toLowerCase();
        let data = CartContent.originalData;
        this.setState({
            data: data.filter((element, index) => {
                return (element.name.toLowerCase().indexOf(value) !== -1 || element.type.indexOf(value) !== -1 ||
                    element.address.toLowerCase().indexOf(value) !== -1 || element.receiver.toLowerCase().indexOf(value) !== -1 ||
                    element.state.indexOf(value) !== -1) || this.state.selectedRowKeys.indexOf((index + 1).toString()) !== -1;
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
        for (let i = 0; i < this.state.data.length; ++i) {
            let state = this.state.data[i].state;
            totalPrice += state === '有货' ? Number(this.state.data[i].price) : 0;
        }
        return totalPrice;
    };

    onSelectAll = (selected) => {
        selected ? this.setState({totalPrice: this.calcTotalPrice()}) : this.setState({totalPrice: 0});
    };

    deleteSelections = () => {
        for (let i = 0; i < CartContent.originalData.length; ++i) {
            if (this.state.selectedRowKeys.indexOf(CartContent.originalData[i].key) !== -1)
                CartContent.originalData.splice(i, 1);
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

    onPaymentClick = () => {
        if (this.state.selectedRowKeys.length)
            this.setState(
                {
                    showPaymentModal: true,
                });
        else {
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
        if (this.state.showPaymentModal)
            this.setState({
                showPaymentModal: false,
                redirect: true,
            });
    };

    onDeleteOk = () => {
        this.setState({
            showDeleteModal: false,
        });
        this.deleteSelections();
    };

    onClose = () => {
        this.setState({
            editRowKey: -1,
        });
    };

    onActionClick = (key) => {
        console.log(key);
        this.setState({
            editRowKey: key
        })
    };

    adjustTotalPrice(delta) {
        this.setState({totalPrice: this.state.totalPrice + delta});
    }

    onSelect = (record, selected) => {
        let delta = selected ? Number(record['price']) : -Number(record['price']);
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
                    footer={
                        <div
                            style={{
                                textAlign: 'right',
                            }}
                        >
                            <Button onClick={this.onClose} style={{marginRight: 8}}>
                                取消
                            </Button>
                            <Button onClick={this.onClose} type="primary">
                                确定
                            </Button>
                        </div>
                    }
                >
                    <Form layout="vertical" hideRequiredMark>
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
                                            required: true,
                                            message: 'please enter url description',
                                        },
                                    ]}
                                >
                                    <Input.TextArea rows={4} placeholder="如有特殊需求，请填写备注。"/>
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
                dataIndex: 'name',
            },
            {
                title: '商品类型',
                dataIndex: 'type',
            },
            {
                title: '价格',
                sorter: {
                    compare: (a, b) => a.price - b.price,
                    multiple: 2,
                },
                dataIndex: 'price',

            },
            {
                title: '收货人',
                dataIndex: 'receiver',
            },
            {
                title: '联系方式',
                dataIndex: 'phoneNumber',
            },
            {
                title: '地址',
                dataIndex: 'address',
            },
            {
                title: '商品状态',
                defaultSortOrder: 'descend',
                sorter: {
                    multiple: 1,
                    compare: (a, b) => {
                        return a.state.localeCompare(b.state);
                    },
                },
                dataIndex: 'state',
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

        const MoreInfo = (
            <Row align={"center"}>
                <Col span={4}>
                    <Image style={{height: '150px', width: '120px'}} src={BookSrc}>
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

        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
            onSelect: this.onSelect,
            onSelectAll: this.onSelectAll,
            getCheckboxProps: (record) => ({
                disabled: record.state === '已购' || record.state === '无货', // Column configuration not to be checked
            }),
        };
        return (
            <Table columns={columns}
                   dataSource={this.state.data}
                   scroll={{y: 350}}
                   rowSelection={rowSelection}
                   expandable={{
                       expandedRowRender: record => (MoreInfo),
                       rowExpandable: record => record.name !== 'Not Expandable',
                   }}
            />
        );
    };
}

export default CartContent;