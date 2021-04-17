import React from 'react'
import {Row, Col, Pagination, Input} from "antd";
import BookPreview from "./BookPreview";
import CSAPP from '../assets/books/CSAPP.jpg'
import mushishi from '../assets/books/mushishi.jpg'
import http from '../assets/books/http.jpg'
import dataStruct from '../assets/books/datastruct.jpg'
import beastars from '../assets/books/beastars.jpg'
import ajin from '../assets/books/ajin.jpg'

const {Search} = Input;
const data = [
    {
        bookSrc: mushishi,
        bookTitle: '虫师',
        bookDescription: '以虫师银古的视角叙述一个个感人的故事。'
    },
    {
        bookSrc: CSAPP,
        bookTitle: 'CSAPP',
        bookDescription: 'Computer Systems: A Programmer\'s Perspective (3rd Edition)'
    },
    {
        bookSrc: http,
        bookTitle: '图解http',
        bookDescription: '一本很不错的http协议入门书。'
    },
    {
        bookSrc: dataStruct,
        bookTitle: '数据结构',
        bookDescription: '软工必备书籍。'
    },
    {
        bookSrc: ajin,
        bookTitle: '亚人',
        bookDescription: '僕はもう、人間じゃない。'
    },
    {
        bookSrc: beastars,
        bookTitle: '动物狂想曲',
        bookDescription: 'ある日突然現れたその眼差し。'
    }
];


class BookBrowser extends React.Component {

    state = {
        data: data,
        originalData: data,
        filter: "",
    };

    filter = () => {
        let filteredData = this.state.originalData.filter((data) => {
            return data.bookTitle.toLowerCase().indexOf(this.state.filter) !== -1;
        });
        this.setState({
            data: filteredData,
        });
    };

    onChange = (e) => {
        this.setState({
            filter: e.target.value.toLowerCase(),
        }, this.filter)
    };

    onSearch = (value) => {
        this.setState({
            filter: value.toLowerCase(),
        }, this.filter);
    };

    renderSearchBar = () => {
        return (
            <Row align={"center"}>
                <Col span={12} style={{margin: '20px'}}>
                    <Search
                        placeholder="请输入搜索关键词"
                        allowClear
                        enterButton="Search"
                        size="large"
                        onSearch={this.onSearch}
                        onChange={this.onChange}
                    />
                </Col>
            </Row>
        );
    };

    renderBooks = () => {
        let renderContent = [];
        this.state.data.map((element) => {
            renderContent.push(
                <Col span={8}>
                    <BookPreview data={element}/>
                </Col>
            )
        });
        return renderContent;
    };

    render() {
        return (
            <div>
                {this.renderSearchBar()}
                <Row>
                    {this.renderBooks()}
                </Row>
                <Row justify={"end"}>
                    <Col span={4}>
                        <Pagination simple defaultCurrent={1} total={15} pageSize={15} style={{marginBottom: '20px'}}/>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default BookBrowser;