import React from "react";
import BookPreview from "./BookPreview";
import {Col, Pagination} from "antd";
import 'antd/dist/antd.css'

import bookUrl_0 from '../assets/books/mushishi.jpg'
import bookUrl_1 from '../assets/books/aot.jpg'
import bookUrl_2 from '../assets/books/beastars.jpg'
import bookUrl_3 from '../assets/books/chainsawman.jpg'
import bookUrl_4 from '../assets/books/hyouka.jpg'
import bookUrl_5 from '../assets/books/ajin.jpg'
import bookUrl_6 from '../assets/books/kanojyo.jpg'
import bookUrl_7 from '../assets/books/hunter.jpg'
import bookUrl_8 from '../assets/books/onepiece.jpg'

const data = [
    {
        bookSrc: bookUrl_0,
        bookTitle: "虫师",
        bookDescription: "借助虫师银古的视角描述了人与“虫”共处的奇幻世界；讲述了一个个有关爱情、亲情、友情、生命与自然的故事。",
    },
    {
        bookSrc: bookUrl_1,
        bookTitle: "进击的巨人",
        bookDescription: "庞大的世界观，精彩的故事剧情，被众多粉丝视为神作，当然也包括我。",
    },
    {
        bookSrc: bookUrl_2,
        bookTitle: "动物狂想曲",
        bookDescription: "在疯狂动物城上映以前就连载的漫画。狼与兔的爱情故事，尔虞我诈的动物世界，狮子与红鹿的强强联手，看点十足。",
    },
    {
        bookSrc: bookUrl_3,
        bookTitle: "电锯人",
        bookDescription: "还能说啥呢，好耶！\n另外，玛奇玛，坏女人！",
    },
    {
        bookSrc: bookUrl_4,
        bookTitle: "冰果",
        bookDescription: "青春、校园、推理三大有趣要素，我很好奇！",
    },
    {
        bookSrc: bookUrl_5,
        bookTitle: "亚人",
        bookDescription: "亚人是几乎拥有不死自身的“亚种人类”。看似平和的社会背后，正在酝酿着一场巨大的阴谋...",
    }, {
        bookSrc: bookUrl_6,
        bookTitle: "租借女友",
        bookDescription: "作者画女角色的水平是真的高。女主是真的好看，男主是真的不行。",
    },
    {
        bookSrc: bookUrl_7,
        bookTitle: "全职猎人",
        bookDescription: "作者实在太喜欢打麻将，20年都还没完结的漫画。",
    },
    {
        bookSrc: bookUrl_8,
        bookTitle: "海贼王",
        bookDescription: "经久不衰的漫画，销量排行榜首的无冕之王。",
    },
];

class BookList extends React.Component {
    state = {
        data: data
    };

    renderBooks = () => {
        let renderContent = [];
        this.state.data.map((data) => {
            console.log(data);
            renderContent.push(
                <Col span={8}>
                    <BookPreview data={data}/>
                </Col>
            )
        });
        return renderContent;
    };

    render() {
        return (
            <div className="books-container">
                {this.renderBooks()}
                <div className="pagination">
                    <Pagination simple defaultCurrent={1} total={50}/>
                </div>
            </div>
        );
    }
}

export default BookList;