import React from 'react'
import {Col, Image, Row, Typography} from "antd";
import PropTypes from 'prop-types'

const {Title, Paragraph} = Typography;

class ExpandedUserInfo extends React.Component {

    static propTypes = {
        userIcon: PropTypes.string.isRequired,
        userSignature: PropTypes.string.isRequired,
    };

    render() {
        return (
            <Row align={"center"}>
                <Col span={4}>
                    <Image style={{height: '150px', width: '120px'}} src={this.props.userIcon}>
                    </Image>
                </Col>
                <Col span={8}>
                    <Typography>
                        <Title level={3}>
                            个性签名
                        </Title>
                        <Paragraph>
                            {this.props.userSignature}
                        </Paragraph>
                    </Typography>
                </Col>
            </Row>
        );
    }
}

export default ExpandedUserInfo;