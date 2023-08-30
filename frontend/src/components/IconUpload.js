import React from 'react'
import {Upload} from 'antd';
import Button from "antd/es/button";
import UploadOutlined from "@ant-design/icons/lib/icons/UploadOutlined";

class IconUpload extends React.Component {
    render() {
        return (

            <Upload
                action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                listType="picture"
            >
                <Button icon={<UploadOutlined/>}>上传/修改头像</Button>
            </Upload>

        );
    }
}

export default IconUpload;