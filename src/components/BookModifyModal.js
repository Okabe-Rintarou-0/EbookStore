import React from 'react'
import {Modal, Form, Avatar} from "antd";
import PropTypes from 'prop-types'
import Input from "antd/es/input";
import {postModifiedBook} from "../service/bookService";
import {history} from "../utils/history";

const {TextArea} = Input;

class BookModifyModal extends React.Component {
    static propTypes = {
        bookToModify: PropTypes.object.isRequired,
        closeFunc: PropTypes.func.isRequired,
    };

    constructor(props) {
        super(props);
        let bookToModify = props.bookToModify;
        console.log(bookToModify);
        this.state = {
            bookToModify: bookToModify,
        }
    }

    monitorNSave = (target, e) => {
        let value = e.target.value;
        switch (target) {
            case "bookTitle":
                this.state.bookToModify.bookTitle = value;
                break;
            case "bookPrice":
                this.state.bookToModify.bookPrice = value;
                break;
            case "bookCover":
                let tmp = {...this.state.bookToModify};
                tmp.bookCover = value;
                this.setState({
                    bookToModify: tmp,
                });
                break;
            case "bookAuthor":
                this.state.bookToModify.bookAuthor = value;
                break;
            case "bookDescription":
                this.state.bookToModify.bookDescription = value;
                break;
            case "bookDetails":
                this.state.bookToModify.bookDetails = value;
                break;
            case "bookTag":
                this.state.bookToModify.bookTag = value;
                break;
            case "bookType":
                this.state.bookToModify.bookType = value;
                break;
            case "bookStock":
                this.state.bookToModify.bookStock = value;
                break;
            default:
                break;
        }
    };

    onSubmit = () => {
        this.state.bookToModify.forSale = this.state.bookToModify.state === '销售中';
        postModifiedBook(this.state.bookToModify);
        setTimeout(() => {
            history.go(0);
        }, 300);
    };

    render() {
        return (
            <Modal title="Basic Modal" visible={true} okText={'提交修改'} cancelText={'取消'}
                   onCancel={this.props.closeFunc} onOk={this.onSubmit}>
                <Form style={{marginTop: '10px'}}>
                    <Form.Item name="bookCover" label="封面" rules={[{required: false}]}>
                        <Avatar shape={"square"} size={"large"} src={this.state.bookToModify.bookCover}/>
                        <Input defaultValue={this.state.bookToModify.bookCover}
                               onChange={this.monitorNSave.bind(this, "bookCover")}/>
                    </Form.Item>
                    <Form.Item name="bookTitle" label="书名" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookTitle}
                               onChange={this.monitorNSave.bind(this, "bookTitle")}/>
                    </Form.Item>
                    <Form.Item name="bookStock" label="库存" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookStock}
                               onChange={this.monitorNSave.bind(this, "bookStock")}/>
                    </Form.Item>
                    <Form.Item name="bookAuthor" label="作者" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookAuthor}
                               onChange={this.monitorNSave.bind(this, "bookAuthor")}/>
                    </Form.Item>
                    <Form.Item name="bookPrice" label="价格" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookPrice}
                               onChange={this.monitorNSave.bind(this, "bookPrice")}/>
                    </Form.Item>
                    <Form.Item name="bookDescription" label="概述" rules={[{required: false}]}>
                        <TextArea maxLength={100} showCount autoSize={true}
                                  defaultValue={this.state.bookToModify.bookDescription}
                                  onChange={this.monitorNSave.bind(this, "bookDescription")}/>
                    </Form.Item>
                    <Form.Item name="bookDetails" label="详情" rules={[{required: false}]}>
                        <TextArea maxLength={200} showCount autoSize={true}
                                  defaultValue={this.state.bookToModify.bookDetails}
                                  onChange={this.monitorNSave.bind(this, "bookDetails")}/>
                    </Form.Item>
                    <Form.Item name="bookTag" label="标签" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookTag}
                               onChange={this.monitorNSave.bind(this, "bookTag")}/>
                    </Form.Item>
                    <Form.Item name="bookType" label="类型" rules={[{required: false}]}>
                        <Input defaultValue={this.state.bookToModify.bookType}
                               onChange={this.monitorNSave.bind(this, "bookType")}/>
                    </Form.Item>
                </Form>
            </Modal>
        )
    }
}

export default BookModifyModal;