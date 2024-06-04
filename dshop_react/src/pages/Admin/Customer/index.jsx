import { Button, Card, Menu } from "antd";
import React, { useState } from "react";
import { PlusOutlined } from "@ant-design/icons";
import ProductList from "./CustomerList";
import CustomerList from "./CustomerList";
import Search from "antd/es/transfer/search";

const items = [
  {
    label: "All Products",
    key: "all",
  },
  {
    label: "Active",
    key: "active",
  },
  {
    label: "Inactive",
    key: "inactive",
  },
];

const onSearch = (value, _e, info) => console.log(info?.source, value);

const AdminCustomer = () => {
  const [current, setCurrent] = useState("all");
  const onClick = (e) => {
    console.log("click ", e);
    setCurrent(e.key);
  };
  return (
    <div className="m-5">
      <div className="flex justify-end">
        {/* <div className="w-1/6">
          <Search
            placeholder="Search"
            allowClear
            onSearch={onSearch}
          />
        </div> */}
      </div>
      <div>
        <Card className="my-5">
          {/* <div>
            <Menu
              onClick={onClick}
              selectedKeys={[current]}
              mode="horizontal"
              items={items}
            />
          </div> */}
          <div className="m-3">
            <CustomerList></CustomerList>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default AdminCustomer;
