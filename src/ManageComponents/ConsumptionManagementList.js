import React from 'react'
import {Col, Button, Row, Table, DatePicker} from "antd";
import ExpandedOrderItems from "../components/ExpandedOrderItems";
import ExpandedConsumptionItem from "../components/ExpandedConsumptionItem";
import {getConsumption} from "../service/consumptionService";
import Avatar from "antd/es/avatar";
import Timeline from "antd/es/timeline";

const {RangePicker} = DatePicker;

class ConsumptionManagementList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            consumptions: [],
        }
    }

    onSearch = () => {
    };

    handleConsumption = consumptions => {
        consumptions.map((consumption, index) => {
            consumption.key = index;
            return consumption;
        });
        this.setState({
            consumptions: consumptions,
        });
    };

    componentDidMount() {
        getConsumption(this.handleConsumption);
    }

    renderTable = () => {
        const columns = [
            {
                title: '头像',
                dataIndex: 'userIcon',
                render: (userIcon) => {
                    return <Avatar src={userIcon}/>
                }
            },
            {
                title: '用户编号',
                dataIndex: 'userId',
            },
            {
                title: '用户名',
                dataIndex: 'username',
            },
            {
                title: '总消费',
                dataIndex: 'sum',
                sorter: (a, b) => a.sum - b.sum,
                showSorterTooltip: false,
                defaultSortOrder: 'descend',
                sortDirections: ['ascend', 'descend', 'ascend']
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
                                   <ExpandedConsumptionItem consumptionItems={item.consumptions}/>
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

export default ConsumptionManagementList;