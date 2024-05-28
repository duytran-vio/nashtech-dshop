import { Button, Form, Input, Menu } from "antd";
import React from "react";
import { PlusOutlined } from "@ant-design/icons";
import Title from "antd/es/typography/Title";
import CategoryImageUpload from "./CategoryImageUpload";

const categories = [
  {
    key: "1",
    label: "Category 1",
  },
  {
    key: "2",
    label: "Category 2",
  },
  {
    key: "3",
    label: "Category 3",
  },
];

const AdminCategory = () => {
  return (
    <div className="m-5">
      <div>
        <Button type="primary" icon={<PlusOutlined />}>
          Add Category
        </Button>
      </div>
      <div className="grid grid-cols-6 gap-2 my-5">
        <div className="col-span-1 bg-white">
          <Title level={3} className="p-2 border-b-2">
            Categories
          </Title>
          <Menu
            // onClick={handleClick}
            className="w-full p-0"
            defaultSelectedKeys={["1"]}
            defaultOpenKeys={["sub1"]}
            mode="inline"
            items={categories}
          />
        </div>

        <div className="col-start-2 col-span-5 bg-white">
          <div className="">
            <Title level={3} className="p-2 border-b-2">
              New Category
            </Title>
          </div>
          <Form layout="vertical" className="p-5">
            <Form.Item label={<b>Category Name</b>}>
              <Input
                type="text"
                className="w-3/12"
                placeholder="Category Name"
              />
            </Form.Item>
            <Form.Item label={<b>Category Image</b>}>
              <CategoryImageUpload />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Save
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
    </div>
  );
};

export default AdminCategory;
