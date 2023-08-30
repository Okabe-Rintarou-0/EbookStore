import React from "react";
import Navigator from "../layout/Navigator";
import SiderBar from "../layout/Sider";
import BookDetails from "../components/BookDetails";
import BookComments from "../components/BookComments";
import {Layout} from "antd";

const {Content, Sider} = Layout;

class BookView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            bookId: props.match.params.bookId,
        }
    }

    render() {
        return (
            <>
                <div id={"book-detail-background"}>
                </div>
                <Layout className="layout">
                    <Navigator/>
                    <Layout>
                        <Sider width={200} className="site-layout-background">
                            <SiderBar/>
                        </Sider>
                        <Content>
                            <BookDetails bookId={this.state.bookId}/>
                            <BookComments bookId={this.state.bookId}/>
                        </Content>
                    </Layout>
                </Layout>
            </>
        );
    }
}

export default BookView;