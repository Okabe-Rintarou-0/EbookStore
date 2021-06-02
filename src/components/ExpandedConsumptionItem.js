import React from 'react'
import {List} from "antd";
import PropTypes from 'prop-types'
import Timeline from "antd/es/timeline";

class ConsumptionListItem extends React.Component {
    static propTypes = {
        consumptionTime: PropTypes.string.isRequired,
        consumptionNumber: PropTypes.string.isRequired,
    };

    render() {
        return (
            <Timeline.Item
                color="green">{`${this.props.consumptionTime}: 消费金额${this.props.consumptionNumber}元`}
            </Timeline.Item>
        );
    }
}

class ExpandedConsumptionItem extends React.Component {

    static propTypes = {
        consumptionItems: PropTypes.arrayOf(PropTypes.object).isRequired,
    };

    render() {
        return (
            <List
                itemLayout="horizontal"
                dataSource={this.props.consumptionItems}
                renderItem={item => {
                    return (
                        <ConsumptionListItem consumptionNumber={item.consumptionNumber}
                                             consumptionTime={item.consumptionTime}/>
                    )
                }}
            />
        )
    }
}

export default ExpandedConsumptionItem;