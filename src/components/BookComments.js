import React from "react";
import {List, Pagination} from 'antd';
import BookComment from "./Comment";
import '../css/bookComments.css'
import imgSrc from '../assets/icon.gif'

import icon_1_url from '../assets/display/icon_1.jpg'
import icon_2_url from '../assets/display/icon_2.jpg'

const data = [
    {
        username: '孤独の観測者',
        imgSrc: imgSrc,
        userComment: '読んでしまって、嬉しいやら悲しいやらで複雑な気持ちがしました。',
        likes: '950',
        dislikes: '5'
    },
    {
        username: '淡幽',
        imgSrc: icon_1_url,
        userComment: '我想和你一起去旅行，看看那些故事里的虫。 哈哈，也许那个时候，我已经是个老太婆了。',
        likes: '800',
        dislikes: '0'
    },
    {
        username: '銀古',
        imgSrc: icon_2_url,
        userComment: '可以啊，如果在那之前我还活着。。。的话',
        likes: '750',
        dislikes: '0'
    },
];

class BookComments extends React.Component {
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
                    dataSource={data}
                    renderItem={item => (
                        <li>
                            <BookComment
                                username={item.username}
                                imgSrc={item.imgSrc}
                                userComment={item.userComment}
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