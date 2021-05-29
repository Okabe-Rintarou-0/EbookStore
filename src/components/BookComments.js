import React from "react";
import {List, Pagination} from 'antd';
import BookComment from "./Comment";
import '../css/bookComments.css'
import {getCommentsById} from "../service/bookService";

class BookComments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: [],
        }
    }

    handleCommentsInfo = data => {
        this.setState({
            comments: data,
        })
    };

    componentDidMount() {
        getCommentsById(this.props.bookId, this.handleCommentsInfo);
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
                <List
                    style={{boxShadow: '0 0 5px #888888'}}
                    className="comment-list"
                    // header={`Hot comments`}
                    itemLayout="horizontal"
                    dataSource={this.state.comments}
                    renderItem={item => (
                        <li>
                            <BookComment
                                comment_id={item.comment_id}
                                username={item.username}
                                imgSrc={item.user_icon}
                                userComment={item.comment_content}
                                likes={item.likes}
                                dislikes={item.dislikes}
                            />
                        </li>
                    )}
                />
                <Pagination style={{
                    margin: "10px",
                    float: "right"
                }} simple defaultCurrent={2} total={50}/>
            </div>
        );
    }
}

export default BookComments;