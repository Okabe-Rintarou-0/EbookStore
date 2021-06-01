import React from 'react'
import {Row, Upload} from "antd";
import Button from "antd/es/button";
import {UploadOutlined} from "@ant-design/icons";

class CsvUpload extends React.Component {
    handleChange = info => {
        let fileList = [...info.fileList];
        fileList = fileList.slice(-1);
        fileList = fileList.map(file => {
            console.log("file", file);
            if (file.response) {
                file.url = file.response.url;
            }
            return file;
        });
        this.setState({fileList});
    };

    render() {
        return (
            <Upload
                maxCount={1}
                withCredentials={true}
                onChange={this.handleChange}
                action='http://localhost:8080/upload'
                multiple={false}
            >
                <Button icon={<UploadOutlined/>} type="primary">上传csv文件</Button>
            </Upload>
        )
    }
}

export default CsvUpload;