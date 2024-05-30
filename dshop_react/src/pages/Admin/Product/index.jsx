import { Button, Card } from "antd";
import React from "react";
import { PlusOutlined } from "@ant-design/icons";
import ProductList from "./ProductList";

const AdminProduct = () => {
  return (
    <div className="m-5">
      <div className="flex justify-end">
        <Button type="primary" icon={<PlusOutlined />}>Create Product</Button>
      </div>
      <div>
        <Card className="my-5">
            <ProductList></ProductList>
        </Card>
      </div>
    </div>
  );
};

export default AdminProduct;
