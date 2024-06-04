import { Button, Card } from "antd";
import React from "react";
import { PlusOutlined } from "@ant-design/icons";
import ProductList from "./ProductList";
import { useNavigate } from "react-router-dom";
import { Path } from "../../../utils/constant";

const AdminProduct = () => {
  const navigate = useNavigate();
  return (
    <div className="m-5">
      <div className="flex justify-end">
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => {
            navigate(Path.ADMIN_NEW_PRODUCT);
          }}
        >
          Create Product
        </Button>
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
