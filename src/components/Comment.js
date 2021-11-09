import React, {createElement} from "react";
import {Avatar, Comment, Tooltip} from 'antd';
import PropTypes from 'prop-types'

import '../css/bookComments.css'
import {DislikeFilled, DislikeOutlined, LikeFilled, LikeOutlined} from '@ant-design/icons';
import {commentConstants} from "../constant/CommentConstants";
import {updateAction} from "../service/commentService";

class BookComment extends React.Component {
    static defaultProps = {
        likes: 0,
        dislikes: 0,
        content: '',
        username: 'user',
        userIcon: '',
        subComments: []
    };

    static propTypes = {
        commentId: PropTypes.string.isRequired,
        likes: PropTypes.number.isRequired,
        dislikes: PropTypes.number.isRequired,
        username: PropTypes.string.isRequired,
        userIcon: PropTypes.string.isRequired,
        content: PropTypes.string.isRequired,
        action: PropTypes.number.isRequired,
        subComments: PropTypes.arrayOf(PropTypes.object).isRequired
    };

    constructor(props) {
        super(props);
        this.state = {
            likes: props.likes,
            dislikes: props.dislikes,
            action: props.action
        };
    };

    handleCommentAction = msg => {
        if (msg.status < 0) return;
        let lastAction = Number(this.state.action);
        let curLikes = this.state.likes;
        let curDislikes = this.state.dislikes;
        // console.log("org: ", lastAction);
        let curAction = Number(msg.message);
        // console.log("cur: ", curAction);
        if (curAction !== lastAction) {
            if (lastAction === commentConstants.Action_Like) {
                curLikes -= 1;
            } else if (lastAction === commentConstants.Action_Dislike) {
                curDislikes -= 1;
            }

            if (curAction === commentConstants.Action_Like) {
                curLikes += 1;
            } else if (curAction === commentConstants.Action_Dislike) {
                curDislikes += 1;
            }

            this.setState({
                likes: curLikes,
                dislikes: curDislikes,
                action: curAction
            });
        }
    };

    updateAction = newAction => {
        updateAction(this.props.commentId, newAction, this.handleCommentAction);
    };

    like = () => {
        this.updateAction(commentConstants.Action_Like);
    };

    dislike = () => {
        this.updateAction(commentConstants.Action_Dislike);
    };

    render() {
        const actions = [
            <Tooltip key="comment-basic-like" title="Like">
      <span onClick={this.like}>
        {createElement(this.state.action === commentConstants.Action_Like ? LikeFilled : LikeOutlined)}
          <span className="comment-action">{this.state.likes}</span>
      </span>
            </Tooltip>,
            <Tooltip key="comment-basic-dislike" title="Dislike">
      <span onClick={this.dislike}>
        {React.createElement(this.state.action === commentConstants.Action_Dislike ? DislikeFilled : DislikeOutlined)}
          <span className="comment-action">{this.state.dislikes}</span>
      </span>
            </Tooltip>,
            <span key="comment-basic-reply-to">回复</span>,
        ];
        return (
            <Comment
                actions={actions}
                author={<a>{this.props.username}</a>}
                avatar={
                    <Avatar
                        src={this.props.userIcon}
                        alt={this.props.username}
                    />
                }
                content={
                    <p>
                        {this.props.content}
                    </p>
                }
                style={{
                    padding: '20px',
                    backgroundColor: 'rgba(255, 255, 255, 0.5)',
                    borderBottom: 'none',
                }}
            >
            </Comment>
        )
    }
}

export default BookComment;
