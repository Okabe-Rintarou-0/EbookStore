import React from "react";
import {List, Button, Row, Col, Input, message} from 'antd';
import BookComment from "./Comment";
import '../css/bookComments.css'
import {addComment, getComments} from "../service/commentService";

class BookComments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: [],
        };
    }

    handleCommentsInfo = data => {
        console.log(data);
        this.setState({
            comments: data,
        });
    };

    addComment = () => {
        let commentInput = this.refs.commentInput;
        let content = commentInput.input.value;
        if (content.length === 0) return;
        commentInput.state.value = '';
        commentInput.focus();
        addComment(this.props.bookId, content, msg => {
            if (msg.status > 0) {
                message.success(msg.message).then(null);
                getComments(this.props.bookId, this.handleCommentsInfo);
            } else {
                message.warn(msg.message).then(null);
            }
        });
    };

    componentDidMount() {
        getComments(this.props.bookId, this.handleCommentsInfo);
    }

    render() {
        return (
            <div id={"comments-container"}>
                <b style={{
                    color: '#666666',
                    fontSize: '20px',
                }}>
                    Hot comments
                </b>
                <Row style={{marginBottom: '5px'}}>
                    <Col span={6}>
                        <Input ref={"commentInput"} focus={true} placeholder="在此输入"/>
                    </Col>
                    <Col>
                        <Button type="primary" onClick={this.addComment}>评论</Button>
                    </Col>
                </Row>
                <List
                    style={{boxShadow: '0 0 5px #888888'}}
                    className="comment-list"
                    itemLayout="horizontal"
                    dataSource={this.state.comments}
                    renderItem={item => (
                        <li>
                            <BookComment
                                commentId={item.commentId}
                                username={item.username}
                                userIcon={item.userIcon}
                                content={item.content}
                                likes={item.likes}
                                dislikes={item.dislikes}
                                action={item.action}
                            />
                        </li>
                    )}
                />
            </div>
        );
    }
}

export default BookComments;