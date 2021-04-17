import {Carousel, Row, Col} from "antd";
import React from "react";
import '../css/carousel.css'
import 'antd/dist/antd.css'

import bookurl_1 from '../assets/carousel/book1.jpg'
import bookurl_2 from '../assets/carousel/book2.jpg'
import bookurl_3 from '../assets/carousel/book3.jpg'

class BookCarousel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const bookStyle = {
            width: "100%",
        };
        return (// <div id={"book-carousel"}>
            <Row align={"center"}>
                <Col span={16}>
                    <Carousel autoplay>
                        <Row>
                            <img src={bookurl_1} style={bookStyle}/>
                        </Row>
                        <div>
                            <img src={bookurl_2} style={bookStyle}/>
                        </div>
                        <div>
                            <img src={bookurl_3} style={bookStyle}/>
                        </div>
                    </Carousel>
                </Col>
            </Row>
        )
            ;
    }
}

export default BookCarousel;