import React from 'react'
import {Col, Button, Row, Table, DatePicker, Switch, Image} from "antd";
import ExpandedConsumptionItem from "../components/ExpandedConsumptionItem";
import {getConsumption, searchConsumptions} from "../service/consumptionService";
import Avatar from "antd/es/avatar";

const {RangePicker} = DatePicker;

class ConsumptionManagementList extends React.Component {
    static tableMode = 0;
    static graphMode = 1;

    constructor(props) {
        super(props);
        this.state = {
            consumptions: [],
            startNEndDates: [],
            mode: ConsumptionManagementList.tableMode,
        }
    }

    toggleMode = () => {
        this.setState({
            mode: this.state.mode === ConsumptionManagementList.tableMode ? ConsumptionManagementList.graphMode : ConsumptionManagementList.tableMode,
        })
    };


    preHandleConsumptions = consumptions => {
        consumptions.map((consumption, index) => {
            consumption.key = index;
        })
    };

    handleSearchConsumptions = consumptions => {
        this.preHandleConsumptions(consumptions);
        console.log("consumptions", consumptions);
        this.setState({
            consumptions: consumptions,
        })
    };

    onSearch = () => {
        searchConsumptions(this.state.startNEndDates, this.handleSearchConsumptions);
    };

    onRangeChange = (date, startNEndDates) => {
        this.setState({
            startNEndDates: startNEndDates,
        });
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
    };

    renderRankGraph = () => (
        <Row align={"middle"} justify={"center"}>
            <Image
                width={750}
                src="http://localhost:8080/image?target=consumption"
            />
        </Row>
    );

    render() {
        return (
            <>
                {
                    this.state.mode === ConsumptionManagementList.tableMode ?
                        this.renderTable() :
                        this.renderRankGraph()
                }
                <Row justify={"end"} gutter={10}>
                    <Col>
                        <b>切换视图
                        </b>
                    </Col>
                    <Col>
                        <Switch onChange={this.toggleMode}/>
                    </Col>
                </Row>
            </>
        )
    }
}

export default ConsumptionManagementList;