import React from 'react'
import {List} from "antd";
import PropTypes from 'prop-types'
import Timeline from "antd/es/timeline";

class ConsumptionListItem extends React.Component {
    static propTypes = {
        orderTime: PropTypes.string.isRequired,
        purchaseNumber: PropTypes.string.isRequired,
    };

    render() {
        return (
            <Timeline.Item
                color="green">{`${this.props.orderTime}: 购买${this.props.purchaseNumber}本`}
            </Timeline.Item>
        );
    }
}

class ExpandedBookConsumptionInfo extends React.Component {

    static propTypes = {
        consumptionItems: PropTypes.arrayOf(PropTypes.object).isRequired,
    };

    render() {
        return (
            <List
                itemLayout="horizontal"
                dataSource={this.props.consumptionItems}
                renderItem={item => {
                    console.log(item);
                    return (
                        <ConsumptionListItem purchaseNumber={item.purchaseNumber}
                                             orderTime={item.orderTime}/>
                    )
                }}
            />
        )
    }
}

export default ExpandedBookConsumptionInfo;