import React from 'react'
import Proptypes from 'prop-types'
import {Input, message} from "antd";

class EditableTableItem extends React.Component {

    static propTypes = {
        setter: Proptypes.func.isRequired,
    };

    constructor(props) {
        super(props);
        this.state = {
            value: '',
            edit: true,
        }
    }

    startEdit = () => {
        this.setState({
            edit: true,
        });
    };

    saveEdit = e => {
        if (e.target.value !== '')
            this.setState({
                value: e.target.value,
                edit: false,
            }, () => {
                this.props.setter(e.target.value);
            });
        else {
            message.warn("内容不能为空!");
        }
    };

    render() {
        return (
            this.state.edit ?
                <Input defaultValue={this.state.value} onBlur={this.saveEdit} onPressEnter={this.saveEdit}/> :
                <p onClick={this.startEdit}>{this.state.value}</p>
        )
    }
}

export default EditableTableItem;