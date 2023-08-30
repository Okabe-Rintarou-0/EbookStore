import React from 'react'
import {Result, Button, Typography, Row, Col} from 'antd';
import {CloseCircleOutlined} from '@ant-design/icons';
import {Link, Redirect} from "react-router-dom";

const {Paragraph, Text} = Typography;

class PaymentFail extends React.Component {

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

    renderFail = () => {
        return (
            <Row align={"middle"} justify={"center"}>
                <Col span={12}>
                    <Result
                        status="error"
                        title="购买商品失败！"
                        subTitle="页面将在5s后自动跳转。"
                        extra={[
                            <Button type="primary" key="console">
                                <Link to={"/cart"}>返回购物车</Link>
                            </Button>,
                            <Button key="buy">获取帮助</Button>,
                        ]}
                    >
                        <div className="desc">
                            <Paragraph>
                                <Text
                                    strong
                                    style={{
                                        fontSize: 16,
                                    }}
                                >
                                    您购买失败的原因可能有以下几点：
                                </Text>
                            </Paragraph>
                            <Paragraph>
                                <CloseCircleOutlined className="site-result-demo-error-icon"/>您的账户已被冻结。<a>申诉&gt;</a>
                            </Paragraph>
                            <Paragraph>
                                <CloseCircleOutlined className="site-result-demo-error-icon"/>您的余额已不足。<a>立即充值 &gt;</a>
                            </Paragraph>
                        </div>
                    </Result>
                </Col>
            </Row>
        );
    };

    render() {
        return (
            <>
                {this.renderFail()}
                {this.renderRedirect()}
            </>
        );
    }
}

export default PaymentFail;