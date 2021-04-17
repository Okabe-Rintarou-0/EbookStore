import React, {createElement} from "react";
import {Comment, Tooltip, Avatar, List} from 'antd';
import PropTypes from 'prop-types'
// import moment from 'moment';
import '../css/bookComments.css'
import {DislikeOutlined, LikeOutlined, DislikeFilled, LikeFilled} from '@ant-design/icons';

class BookComment extends React.Component {
    static defaultProps = {
        likes: 0,
        dislikes: 0,
        action: 'none',
        username: 'user',
        userComment: 'No comment.',
        imgSrc: ''
    };

    static propTypes = {
        likes: PropTypes.number.isRequired,
        dislikes: PropTypes.number.isRequired,
        action: PropTypes.string,
        username: PropTypes.string.isRequired,
        userComment: PropTypes.string.isRequired,
        imgSrc: PropTypes.string.isRequired
    };

    constructor(props) {
        super(props);
        this.state = {
            likes: props.likes,
            dislikes: props.dislikes,
            action: "none"
        };
        this.like = this.like.bind(this);
        this.dislike = this.dislike.bind(this);
    };


    setLikes(value) {
        this.setState({likes: value})
    }

    setDislikes(value) {
        this.setState({dislikes: value})
    }

    setAction(action) {
        this.setState({action: action})
    }

    like() {
        this.setLikes(Number(this.props.likes) + 1);
        this.setDislikes(this.props.dislikes);
        this.setAction('liked');
    }

    dislike() {
        this.setLikes(this.props.likes);
        this.setDislikes(Number(this.props.dislikes) + 1);
        this.setAction('disliked');
    }

    render() {
        const actions = [
            <Tooltip key="comment-basic-like" title="Like">
      <span onClick={this.like}>
        {createElement(this.state.action === 'liked' ? LikeFilled : LikeOutlined)}
          <span className="comment-action">{this.state.likes}</span>
      </span>
            </Tooltip>,
            <Tooltip key="comment-basic-dislike" title="Dislike">
      <span onClick={this.dislike}>
        {React.createElement(this.state.action === 'disliked' ? DislikeFilled : DislikeOutlined)}
          <span className="comment-action">{this.state.dislikes}</span>
      </span>
            </Tooltip>,
            <span key="comment-basic-reply-to">Reply to</span>,
        ];
        return (
            <Comment
                actions={actions}
                author={<a>{this.props.username}</a>}
                avatar={
                    <Avatar
                        src={this.props.imgSrc}
                        alt={this.props.username}
                    />
                }
                content={
                    <p>
                        {this.props.userComment}
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
