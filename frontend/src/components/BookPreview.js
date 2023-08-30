import React from "react";
import "../css/book.css"
import PropTypes from 'prop-types'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faCartArrowDown, faShare, faStar} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import {message} from "antd";
import {addToCart} from "../service/cartSerivce";
import {addFavouriteBook} from "../service/favouriteService";

class BookPreview extends React.Component {
    static propTypes = {
        data: PropTypes.object.isRequired,
    };

    onAddToCart = e => {
        e.preventDefault();
        message
            .loading('加入购物车中...', 1)
            .then(
                () => {
                    addToCart(this.props.data.bookId);
                }
            );
    };

    onAddToFavourite = e => {
        e.preventDefault();
        message
            .loading('加入收藏夹中...', 1)
            .then(
                () => {
                    addFavouriteBook(this.props.data.bookId);
                }
            );
    };

    render() {
        return (
            <div id="book-container" className="round-corner">
                <div className="preview-book-background">
                </div>
                <div className="preview-book-img">
                    <Link to={`/book/${this.props.data.bookId}`}> <img src={this.props.data.bookCover} alt=""/></Link>
                </div>
                <div className="preview-book-info">
                    <div className="preview-book-title">
                        {this.props.data.bookTitle.length > 6 ? this.props.data.bookTitle.slice(0, 5) + "..." : this.props.data.bookTitle}
                    </div>
                    <div className="preview-book-description">
                        {this.props.data.bookDescription.length > 50 ? this.props.data.bookDescription.slice(0, 50) + "..." : this.props.data.bookDescription}
                    </div>
                </div>
                <div className="preview-book-option">
                    <a href="">
                        <FontAwesomeIcon icon={faShare} className={"preview-book-icon"}/>
                    </a>
                    <a href="" onClick={this.onAddToCart}>
                        <FontAwesomeIcon icon={faCartArrowDown} className={"preview-book-icon"}/>
                    </a>
                    <a href="" onClick={this.onAddToFavourite}>
                        <FontAwesomeIcon icon={faStar} className={"preview-book-icon"}/>
                    </a>
                </div>
            </div>
        );
    }
}

export default BookPreview;
