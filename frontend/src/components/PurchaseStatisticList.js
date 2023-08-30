import React from 'react'
import {Button, Col, DatePicker, Image, Row, Table} from "antd";
import {getConsumptionsGroupByBooks} from "../service/consumptionService";
import ExpandedBookConsumptionInfo from "./ExpandedBookConsumptionInfo";


const {RangePicker} = DatePicker;

class PurchaseStatisticList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            consumptions: [],
            startNEndDates: [],
        }
    }


    handleSearchConsumptions = consumptions => {
        this.handleConsumption(consumptions);
    };

    onSearch = () => {
        getConsumptionsGroupByBooks(this.state.startNEndDates, this.handleSearchConsumptions);
    };

    onRangeChange = (date, startNEndDates) => {
        this.setState({
            startNEndDates: startNEndDates,
        });
    };

    handleConsumption = consumptions => {
        console.log(consumptions);
        consumptions.map((row, index) => {
            row.key = index;
            row.sum = row.totalConsumption = 0;
            row.consumptions.map(orderInfo => {
                row.sum += orderInfo.purchaseNumber;
            });
            row.totalConsumption = Number(row.sum) * Number(row.bookPrice);
            return row;
        });
        this.setState({
            consumptions: consumptions,
        });
    };

    componentDidMount() {
        getConsumptionsGroupByBooks([], this.handleConsumption);
    }

    renderTable = () => {
        const columns = [
            {
                title: '书籍封面',
                dataIndex: 'bookCover',
                render: (bookCover) => {
                    return <Image width={100} src={bookCover}/>
                }
            },
            {
                title: '书名',
                dataIndex: 'bookTitle',
            },
            {
                title: '价格',
                dataIndex: 'bookPrice',
            },
            {
                title: '购买总数',
                dataIndex: 'sum',
                sorter: (a, b) => a.sum - b.sum,
                showSorterTooltip: false,
                defaultSortOrder: 'descend',
                sortDirections: ['ascend', 'descend', 'ascend'],
            },
            {
                title: '消费总额',
                dataIndex: 'totalConsumption',
                sorter: (a, b) => a.totalConsumption - b.totalConsumption,
                showSorterTooltip: false,
                defaultSortOrder: 'descend',
                sortDirections: ['ascend', 'descend', 'ascend'],
            },
        ];
        return (
            <div>
                <Row style={{margin: '30px'}}>
                    <Col>
                        <RangePicker onChange={this.onRangeChange} showTime showToday/>
                    </Col>
                    <Col>
                        <Button onClick={this.onSearch} type={"primary"}>查询</Button>
                    </Col>
                </Row>
                <Table columns={columns}
                       dataSource={this.state.consumptions}
                       scroll={{y: 485}}
                       expandable={{
                           expandedRowRender: (item) => {
                               return (
                                   <ExpandedBookConsumptionInfo consumptionItems={item.consumptions}/>
                               );
                           },
                           rowExpandable: record => record.name !== 'Not Expandable',
                       }}
                />
            </div>
        );
    }
    ;

    render() {
        return (
            <>
                {this.renderTable()}
            </>
        )
    }
}

export default PurchaseStatisticList;