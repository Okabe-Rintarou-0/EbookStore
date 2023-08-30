import React from 'react'
import {Modal, Row, Select} from "antd";
import Input from "antd/es/input";
import PropTypes from 'prop-types'
import HighlightOutlined from "@ant-design/icons/lib/icons/HighlightOutlined";
import Form from "antd/es/form";
import {placeOrder} from "../service/orderService";
import {Redirect} from "react-router-dom";

const {Option} = Select;

class EditableInfoLine extends React.Component {
    static state = {
        value: null,
        edit: false,
    };

    static propTypes = {
        title: PropTypes.string.isRequired,
        defaultValue: PropTypes.string.isRequired,
    };

    startEdit = () => {
        this.setState({
            edit: true,
        })
    };

    constructor(props) {
        super(props);
        this.state = {
            value: props.defaultValue,
        }
    }

    postEdit = e => {
        this.setState({
            edit: false,
            value: e.target.value,
        })
    };


    render() {
        return (
            <Row style={{margin: '10px 0px'}}>
                <b>{this.props.title} :
                    {this.state.edit ?
                        <Input style={{width: 160}} placeholder="Please input" onPressEnter={this.postEdit}/> :
                        (<>
                            {this.state.value}
                            <HighlightOutlined onClick={this.startEdit}/>
                        </>)}
                </b>
            </Row>
        )
    }
}

class PaymentModal extends React.Component {
    static state = {
        editReceiver: false,
        editAddress: false,
        editTel: false,
    };
    static propTypes = {
        defaultTel: PropTypes.string.isRequired,
        defaultAddress: PropTypes.string.isRequired,
        defaultReceiver: PropTypes.string.isRequired,
        userProperty: PropTypes.number.isRequired,
        selectedItems: PropTypes.arrayOf(PropTypes.object).isRequired,
    };

    constructor(props) {
        super(props);
        this.state = {
            tel: props.defaultTel,
            address: props.defaultAddress,
            receiver: props.defaultReceiver,
            property: props.userProperty,
            visible: true,
            redirect_to_success: false,
            redirect_to_fail: false,
        };
    }

    monitorNSave = (index, e) => {
        switch (index) {
            case 0:
                this.setState({
                    receiver: e.target.value,
                });
                break;
            case 1:
                this.setState({
                    address: e.target.value,
                });
                break;
            case 2:
                this.setState({
                    tel: e.target.value,
                });
                break;
            default:
                break;
        }
    };

    renderRedirect = () => {
        if (this.state.redirect_to_success) {
            return (
                <Redirect to={"/status/success"}/>
            );
        } else if (this.state.redirect_to_fail) {
            return (
                <Redirect to={"/status/fail"}/>
            );
        } else return null;
    };

    onPaymentOk = () => { ///todo modify the post method
        let postData = {
            receiver: this.state.receiver,
            tel: this.state.tel,
            address: this.state.address,
            items: []
        };
        console.log(this.props.selectedItems);
        this.props.selectedItems.map(selectedItem => {
            postData.items.push({
                cartId: selectedItem.cartId,
                bookId: selectedItem.bookId,
                purchaseNumber: selectedItem.purchaseNumber,
                bookPrice: selectedItem.bookPrice,
            });
            return selectedItem;
        });
        console.log(postData);
        placeOrder(postData, data => {
            let status = data.status;
            if (status > 0)
                this.setState({redirect_to_success: true});
            else
                this.setState({redirect_to_fail: true});
        })
    };

    onPaymentCancel = () => {
        this.setState({
            visible: false,
        });
    };

    render() {
        return (
            <Modal title="提示" visible={this.state.visible} onOk={this.onPaymentOk} onCancel={this.onPaymentCancel}
                   okText={"确认支付"}
                   cancelText={"取消"}>
                <b>请选择支付方式:&nbsp;</b>
                <Select defaultValue="微信" style={{width: 120}}>
                    <Option value="wechat">微信</Option>
                    <Option value="alipay">支付宝</Option>
                    <Option value="credit-card">信用卡</Option>
                    <Option value="change">零钱</Option>
                </Select>
                <Form style={{marginTop: '10px'}}>
                    <Form.Item name="receiver" label="收货人" rules={[{required: true}]}>
                        <Input defaultValue={this.props.defaultReceiver}
                               onChange={this.monitorNSave.bind(this, 0)}/>
                    </Form.Item>
                    <Form.Item name="address" label="收货地址" rules={[{required: true}]}>
                        <Input defaultValue={this.props.defaultAddress}
                               onChange={this.monitorNSave.bind(this, 1)}/>
                    </Form.Item>
                    <Form.Item name="tel" label="联系方式" rules={[{required: true}]}>
                        <Input defaultValue={this.props.defaultTel}
                               onChange={this.monitorNSave.bind(this, 2)}/>
                    </Form.Item>
                </Form>
                <b> 账户余额 : {this.state.property}元</b>
                {this.renderRedirect()}
            </Modal>
        );
    }
}

export default PaymentModal;