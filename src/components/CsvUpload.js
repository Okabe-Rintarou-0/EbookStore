import React from 'react'
import {message, Row, Upload} from "antd";
import Button from "antd/es/button";
import {UploadOutlined} from "@ant-design/icons";

class CsvUpload extends React.Component {
    handleChange = info => {
        let fileList = [...info.fileList];
        fileList = fileList.slice(-1);
        fileList = fileList.map(file => {
            console.log("file", file);
            if (file.response) {
                let response = file.response;
                if (response.status > 0)
                    message.success(response.message);
                else {
                    file.status = 'error';
                    message.warn(response.message);
                }
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
                <Button icon={<UploadOutlined/>} type="primary">上传csv文件以导入书籍</Button>
            </Upload>
        )
    }
}

export default CsvUpload;