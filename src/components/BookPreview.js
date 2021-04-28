import React from "react";
import "../css/book.css"
import PropTypes from 'prop-types'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faCartArrowDown, faShare, faStar} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";

class BookPreview extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: this.props.data,
        };
    }

    static propTypes = {
        data: PropTypes.object.isRequired,
    };

    render() {
        return (
            <div id="book-container" className="round-corner">
                <div className="preview-book-background">
                </div>
                <div className="preview-book-img">
                    <Link to={`/book/${this.state.data.bookId}`}> <img src={this.state.data.bookCover} alt=""/></Link>
                </div>
                <div className="preview-book-info">
                    <div className="preview-book-title">
                        {this.props.data.bookTitle}
                    </div>
                    <div className="preview-book-description">
                        {this.props.data.bookDescription}
                    </div>
                </div>
                <div className="preview-book-option">
                    <a href="">
                        <FontAwesomeIcon icon={faShare} className={"preview-book-icon"}/>
                    </a>
                    <a href="">
                        <FontAwesomeIcon icon={faCartArrowDown} className={"preview-book-icon"}/>
                    </a>
                    <a href="">
                        <FontAwesomeIcon icon={faStar} className={"preview-book-icon"}/>
                    </a>
                </div>
            </div>
        );
    }
}

export default BookPreview;
