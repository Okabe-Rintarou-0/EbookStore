import React from 'react'
import {Col, Row, Table,} from "antd";
import Button from "antd/es/button";
import {history} from "../utils/history";
import ExpandedUserInfo from "../components/ExpandedUserInfo";
import {banUsers, getAllUsers, unbanUsers} from "../service/userService";

class UserManagementList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
        }
    }

    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys});
    };

    handleUsers = data => {
        let users = data.arrayData;
        console.log(users);
        users.map((user, index) => {
            user.key = index;
        });
        this.setState({
            users: users,
        })
    };

    componentDidMount() {
        getAllUsers(this.handleUsers)
    }

    handleUnbanUser = () => {
        let userIdList = [];
        this.state.selectedRowKeys.map(key => {
            userIdList.push(this.state.users[key].userId)
        });
        // console.log(userIdList);
        unbanUsers(userIdList, () => {
        });
        setTimeout(() => history.go(0), 500);
    };

    handleBanUser = () => {
        let userIdList = [];
        this.state.selectedRowKeys.map(key => {
            userIdList.push(this.state.users[key].userId)
        });
        // console.log(userIdList);
        banUsers(userIdList, () => {
            history.go(0);
        });
        // setTimeout(() => history.go(0), 500);
    };

    renderTable = () => {
        const columns = [
            {
                title: '用户名',
                dataIndex: 'username',
            },
            {
                title: '用户权限',
                dataIndex: 'userIdentity',
                render: (text) => {
                    switch (text) {
                        case 0:
                            return <p>普通用户</p>;
                        case 2:
                            return <p>封禁中</p>;
                    }
                }
            },
            {
                title: '地址',
                dataIndex: 'userAddress',
            },
            {
                title: '联系方式',
                dataIndex: 'userTel'
            }
        ];
        const {selectedRowKeys} = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
            onSelect: this.onSelect,
            onSelectAll: this.onSelectAll,
        };
        return (
            <div>
                <Table columns={columns}
                       rowSelection={rowSelection}
                       dataSource={this.state.users}
                       scroll={{y: 473}}
                       expandable={{
                           expandedRowRender: (user) => {
                               return (
                                   <ExpandedUserInfo userSignature={user.userSignature} userIcon={user.userIcon}/>
                               );
                           },
                           rowExpandable: record => record.name !== 'Not Expandable',
                       }}
                       footer={
                           () =>
                               <Row justify="end" gutter={20}>
                                   <Col>
                                       <Button onClick={this.handleUnbanUser} type="primary">
                                           解封
                                       </Button>
                                   </Col>
                                   <Col>
                                       <Button onClick={this.handleBanUser} type="primary"
                                               danger>
                                           封禁
                                       </Button>
                                   </Col>
                               </Row>}
                />
            </div>
        );
    };

    render() {
        return (
            <>
                {this.renderTable()}
            </>
        )
    }
}

export default UserManagementList;