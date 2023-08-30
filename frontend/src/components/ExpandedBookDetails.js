import React from 'react'
import {Col, Image, Row, Typography} from "antd";
import PropTypes from 'prop-types'

const {Title, Paragraph} = Typography;

class ExpandedBookDetails extends React.Component {

    static propTypes = {
        bookDetails: PropTypes.string.isRequired,
        bookCover: PropTypes.string.isRequired,
        bookDescription: PropTypes.string.isRequired,
    };

    render() {
        return (
            <Row align={"center"}>
                <Col span={4}>
                    <Image style={{height: '150px', width: '120px'}} src={this.props.bookCover}>
                    </Image>
                </Col>
                <Col span={8}>
                    <Typography>
                        <Title level={3}>
                            书籍详情
                        </Title>
                        <Paragraph>
                            {this.props.bookDetails.length === 0 ? this.props.bookDescription : this.props.bookDetails}
                        </Paragraph>
                    </Typography>
                </Col>
            </Row>
        );
    }
}

export default ExpandedBookDetails;