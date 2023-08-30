import React from 'react'
import {
    Button,
    Col,
    Input,
    Layout,
    Modal,
    Row,
    Statistic,
    Table,
    InputNumber
} from "antd";
import '../css/searchBar.css'
import {history} from "../utils/history";
import {getUser} from "../service/userService";
import {deleteCart, getAllCartItems, searchCartItems} from "../service/cartSerivce";
import PaymentModal from "./PaymentModal";
import ExpandedBookDetails from "./ExpandedBookDetails";

const {Footer} = Layout;
const {Search} = Input;

class CartContent extends React.Component {

    handleCartItemsInfo = data => {
        data.map((cartItem, index) => {
            let book = cartItem.book;
            cartItem.bookId = book.bookId;
            cartItem.forSale = book.forSale;
            cartItem.key = index;
            cartItem.state = cartItem.book.forSale ? '销售中' : '已下架';
            cartItem.bookTitle = book.bookTitle;
            cartItem.bookCover = book.bookCover;
            cartItem.bookDetails = book.bookDetails;
            cartItem.bookDescription = book.bookDescription;
            cartItem.bookAuthor = book.bookAuthor;
            cartItem.bookPrice = book.bookPrice;
            cartItem.book = null;
        });
        this.setState({
            cartItems: data
        });
    };

    componentDidMount() {
        getAllCartItems(this.handleCartItemsInfo);
    }


    constructor(props) {
        super(props);
        this.state = {
            selectedRowKeys: [], // Check here to configure the default column
            loading: false,
            totalPrice: 0,
            showDeleteModal: false,
            showPaymentModal: false,
            editRowKey: -1,
            orders: [],
            userAddress: '',
            username: '',
            userTel: '',
            userProperty: 0,
        };
    }

    render() {
        return (
            <>
                {this.renderSearchBar()}
                {this.renderTable()}
                {this.renderDeleteModal()}
                {this.renderPaymentModal()}
                {this.renderFooter()}
            </>
        );
    }

    onSearch = (keyword) => {
        searchCartItems(keyword, this.handleCartItemsInfo);
    };

    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys}, () => {
            this.setState({
                totalPrice: this.calcTotalPrice()
            })
        });
    };

    calcTotalPrice = () => {
        let totalPrice = 0;
        this.state.cartItems.map((cartItem, index) => {
            if (this.state.selectedRowKeys.indexOf(index) !== -1) {
                totalPrice += Number(cartItem.bookPrice) * Number(cartItem.purchaseNumber);
            }
        });
        return totalPrice;
    };

    onDeleteClick = () => {
        if (this.state.selectedRowKeys.length)
            this.setState(
                {
                    showDeleteModal: true,
                });
    };

    handleUserInfo = data => {
        this.setState({
            userProperty: data.userProperty,
            username: data.username,
            userAddress: data.userAddress,
            userTel: data.userTel,
            showPaymentModal: true,
        })
    };

    checkValidity = () => {
        let isValid = true;
        this.state.selectedRowKeys.map(key => {
            if (this.state.cartItems[key].forSale !== true) {
                isValid = false;
            }
        });
        return isValid;
    };

    onPaymentClick = () => {
        if (this.state.selectedRowKeys.length) {
            if (this.checkValidity())
                getUser(this.handleUserInfo);
            else {
                this.setState({
                    showPaymentModal: false,
                });
                Modal.error({
                    title: '支付失败',
                    content: '选中的书籍中包含已下架的书籍。',
                });
            }
        } else {
            Modal.error({
                title: '支付失败',
                content: '请选中书籍再点击支付按键。',
            });
        }
    };

    onDeleteCancel = () => {
        this.setState({
            showDeleteModal: false,
        });
    };

    onDeleteOk = () => {
        this.state.selectedRowKeys.map(key => {
            deleteCart(this.state.cartItems[key].cart_id, () => {
            });
        });
        setTimeout(() => {
            history.go(0)
        }, 300);
        this.setState({
            showDeleteModal: false,
        });
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
            let selectedItems = [];
            this.state.selectedRowKeys.map((itemIndex) => {
                selectedItems.push(this.state.cartItems[itemIndex]);
            });
            return (
                <PaymentModal
                    defaultAddress={this.state.userAddress}
                    defaultReceiver={this.state.username}
                    defaultTel={this.state.userTel}
                    userProperty={this.state.userProperty}
                    selectedItems={selectedItems}
                />
            )
        } else return null;
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
                title: '数量',
                dataIndex: 'purchaseNumber',
                render: (text, book) => {
                    let onChange = newNum => {
                        book.purchaseNumber = newNum;
                        if (this.state.selectedRowKeys.indexOf(book.key) !== -1) {
                            this.setState({
                                totalPrice: this.calcTotalPrice()
                            });
                        }
                    };
                    return <InputNumber min={1} defaultValue={text} onChange={onChange}/>
                }
            },
            {
                title: '状态',
                dataIndex: 'state',
            }
        ];

        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        return (
            <Table columns={columns}
                   dataSource={this.state.cartItems}
                   scroll={{y: 350}}
                   rowSelection={rowSelection}
                   expandable={{
                       expandedRowRender: (item) => {
                           return (
                               <ExpandedBookDetails
                                   bookCover={item.bookCover}
                                   bookDescription={item.bookDescription}
                                   bookDetails={item.bookDetails}
                               />
                           );
                       },
                       rowExpandable: record => record.name !== 'Not Expandable',
                   }}
            />
        );
    };
}

export default CartContent;