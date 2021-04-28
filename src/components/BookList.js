import React from "react";
import BookPreview from "./BookPreview";
import {Col, Pagination} from "antd";
import 'antd/dist/antd.css'
import {getBooks} from "../service/bookService";

class BookList extends React.Component {

    state = {
        data: []
    };

    handleBooks = data => {
        console.log(data);
        this.setState({
            data: data
        })
    };

    componentDidMount() {
        getBooks(this.handleBooks);
    }

    renderBooks = () => {
        let renderContent = [];
        let data = this.state.data;
        let bookNumber = data.length >= 9 ? 9 : data.length;
        for (let i = 0; i < bookNumber; ++i)
            renderContent.push(
                <Col span={8}>
                    <BookPreview data={data[i]}/>
                </Col>
            );
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