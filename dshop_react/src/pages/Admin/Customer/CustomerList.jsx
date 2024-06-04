import { Button, Space, Table, Tag, message } from "antd";
import React, { useState } from "react";
import useSWR from "swr";
import { getUsers, updateUser, userEndPoint } from "../../../services/userService";
import { Role } from "../../../utils/constant";



const initPageSize = 5;

const CustomerList = () => {
  const [filter, setFilter] = useState({
    role: Role.CUSTOMER,
    page: 0,
    size: initPageSize,
    sort: "id"
  });
  const {
    data: customers,
    isLoading,
    isError,
    mutate,
  } = useSWR({ url: userEndPoint, params: filter }, getUsers);

  if (isLoading) return <div>Loading...</div>;

  const setUserEnable = async (user, enableStatus) => {
    try{
      await updateUser(user.id, { enableStatus: enableStatus });
      mutate();
      message.success(`Set user enable status to "${enableStatus}" successfully!`);
    }
    catch(error){
      message.error(error.message);
    }
  }

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Username",
      dataIndex: "username",
      key: "username",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Action",
      key: "key",
      render: (record) =>
        record.enableStatus ? (
          <Button danger onClick={() => setUserEnable(record, false)}>Disable</Button>
        ) : (
          <Button type="primary" onClick={() => setUserEnable(record, true)}>Enable</Button>
        ),
    },
  ];

  return (
    <Table
      columns={columns}
      dataSource={customers.content}
      pagination={{
        total: customers.totalElements,
        pageSize: filter.size,
        current: filter.page + 1,
        onChange: (page, pageSize) => {
          setFilter({ ...filter, page: page - 1 });
        },
      }}
    />
  );
};

export default CustomerList;
