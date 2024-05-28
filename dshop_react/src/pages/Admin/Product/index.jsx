import { Button, Card, Menu } from "antd";
import React, { useState } from "react";
import { PlusOutlined } from "@ant-design/icons";
import ProductList from "./ProductList";

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

const AdminProduct = () => {
  const [current, setCurrent] = useState("all");
  const onClick = (e) => {
    console.log("click ", e);
    setCurrent(e.key);
  };
  return (
    <div className="m-5">
      <div className="flex justify-end">
        <Button type="primary" icon={<PlusOutlined />}>Create Product</Button>
      </div>
      <div>
        <Card className="my-5">
          <div>
            <Menu
              onClick={onClick}
              selectedKeys={[current]}
              mode="horizontal"
              items={items}
            />
          </div>
          <div  className="m-3">
            <ProductList></ProductList>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default AdminProduct;
