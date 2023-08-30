import React from 'react'
import {Col, Image, Row, Typography, List} from "antd";
import PropTypes from 'prop-types'

const {Title, Paragraph} = Typography;

class BookListItem extends React.Component {
    static propTypes = {
        bookTitle: PropTypes.string.isRequired,
        bookCover: PropTypes.string.isRequired,
        bookDetails: PropTypes.string.isRequired,
        bookPrice: PropTypes.string.isRequired,
        purchaseNumber: PropTypes.string.isRequired,
    };

    render() {
        return (
            <Row align={"center"}>
                <Col span={4}>
                    <Image style={{height: '200px', width: '160px'}} src={this.props.bookCover}>
                    </Image>
                </Col>
                <Col span={8}>
                    <Typography>
                        <Title level={4}>
                            书籍名称: {this.props.bookTitle}
                        </Title>
                        <Title level={4}>
                            书籍价格: {this.props.bookPrice} 购买数量: {this.props.purchaseNumber}
                        </Title>
                        <Title level={4}>
                            书籍详情:
                        </Title>
                        <Paragraph>
                            {this.props.bookDetails}
                        </Paragraph>
                    </Typography>
                </Col>
            </Row>
        );
    }
}

class ExpandedOrderItems extends React.Component {

    static propTypes = {
        orderItems: PropTypes.arrayOf(PropTypes.object).isRequired,
    };

    componentDidMount() {
    }

    render() {
        return (
            <List
                itemLayout="horizontal"
                dataSource={this.props.orderItems}
                renderItem={item => {
                    return (
                        <BookListItem bookDetails={item.book.bookDetails}
                                      purchaseNumber={item.purchaseNumber}
                                      bookCover={item.book.bookCover}
                                      bookTitle={item.book.bookTitle}
                                      bookPrice={item.book.bookPrice}
                        />
                    )
                }}
            />
        )
    }
}

export default ExpandedOrderItems;