import React from 'react'
import {Result, Button, Typography, Row, Col} from 'antd';
import {CloseCircleOutlined} from '@ant-design/icons';
import {Link, Redirect} from "react-router-dom";

const {Paragraph, Text} = Typography;

class PaymentSuccess extends React.Component {

    state = {
        redirect: false
    };

    Redirect = () => {
        this.setState({
            redirect: true,
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return (<Redirect to={"/cart"}/>);
        } else return null;
    };

    componentDidMount() {
        setTimeout(
            this.Redirect, 5000
        );
    }


    renderSuccess = () => {
        return (
            <Result
                status="success"
                title="购买商品成功！"
                subTitle="页面将在5s后自动跳转。"
                extra={[
                    <Button type="primary" key="console">
                        <Link to={"/cart"}>返回购物车</Link>
                    </Button>,
                    <Button key="buy">再买一本！</Button>,
                ]}
            />
        );
    };

    render() {
        return (
            <>
                {this.renderSuccess()}
                {this.renderRedirect()}
            </>
        );
    }
}

export default PaymentSuccess;