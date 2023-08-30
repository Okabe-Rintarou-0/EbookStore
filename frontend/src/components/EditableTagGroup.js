import React from 'react';
import {Tag, Input, message} from 'antd';
import {TweenOneGroup} from 'rc-tween-one';
import {PlusOutlined} from '@ant-design/icons';
import PropTypes from 'prop-types'

class EditableTagGroup extends React.Component {
    static propTypes = {
        maxNum: PropTypes.number.isRequired,
    };

    constructor(props) {
        super(props);
        this.state = {
            tags: [],
            inputVisible: false,
            inputValue: '',
            maxNum: props.maxNum,
        }
    }

    handleClose = removedTag => {
        const tags = this.state.tags.filter(tag => tag !== removedTag);
        console.log(tags);
        this.setState({tags});
    };

    showInput = () => {
        this.setState({inputVisible: true}, () => this.input.focus());
    };

    handleInputChange = e => {
        this.setState({inputValue: e.target.value});
    };

    handleInputConfirm = () => {
        const {inputValue} = this.state;
        let {tags} = this.state;
        if (tags.length < this.props.maxNum) {
            if (inputValue && tags.indexOf(inputValue) === -1) {
                tags = [...tags, inputValue];
            }
        } else {
            message.warn(`最多不能超过${this.props.maxNum}个标签`).then(null);
        }
        this.setState({
            tags,
            inputVisible: false,
            inputValue: '',
        });
    };

    saveInputRef = input => {
        this.input = input;
    };

    forMap = tag => {
        const tagElem = (
            <Tag
                closable
                onClose={e => {
                    e.preventDefault();
                    this.handleClose(tag);
                }}
            >
                {tag}
            </Tag>
        );
        return (
            <span key={tag} style={{display: 'inline-block'}}>
        {tagElem}
      </span>
        );
    };

    render() {
        const {tags, inputVisible, inputValue} = this.state;
        const tagChild = tags.map(this.forMap);
        return (
            <>
                <div style={{marginBottom: 16}}>
                    <TweenOneGroup
                        enter={{
                            scale: 0.8,
                            opacity: 0,
                            type: 'from',
                            duration: 100,
                        }}
                        leave={{opacity: 0, width: 0, scale: 0, duration: 200}}
                        appear={false}
                    >
                        {tagChild}
                    </TweenOneGroup>
                </div>
                {inputVisible && (
                    <Input
                        ref={this.saveInputRef}
                        type="text"
                        size="small"
                        style={{width: 78}}
                        value={inputValue}
                        onChange={this.handleInputChange}
                        onBlur={this.handleInputConfirm}
                        onPressEnter={this.handleInputConfirm}
                    />
                )}
                {!inputVisible && (
                    <Tag onClick={this.showInput} className="site-tag-plus">
                        <PlusOutlined/> 添加标签
                    </Tag>
                )}
            </>
        );
    }
}

export default EditableTagGroup;