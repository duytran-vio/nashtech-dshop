import { Button, Card, Checkbox, Form, Input, Select } from "antd";
import { Typography } from "antd";
import React, { useState } from "react";
import ProductImageUpload from "./ProductImageUpLoad";

const { Title } = Typography;

const onChange = (value) => {
  console.log(`selected ${value}`);
};
const onSearch = (value) => {
  console.log("search:", value);
};

// Filter `option.label` match the user type `input`
const filterOption = (input, option) =>
  (option?.label ?? "").toLowerCase().includes(input.toLowerCase());

const categories = [
  {
    value: "Shirt",
    label: "Shirt",
  },
  {
    value: "Pants",
    label: "Pants",
  },
  {
    value: "Shoes",
    label: "Shoes",
  },
];

const status = [
  {
    value: "Active",
    label: "Active",
  },
  {
    value: "Inactive",
    label: "Inactive",
  },
];

const initialProduct = {
  title: "",
  category: "",
  description: "",
  price: 0,
  stock: 0,
  status: "active",
  isFeatured: false,
};

const NewProductCreate = () => {
  const [form] = Form.useForm();

  const [product, setProduct] = useState(initialProduct);

  return (
    <div className="grid grid-cols-12 gap-2 m-5">
      <Card className="col-span-9">
        <Title level={3}>General Infomation</Title>
        <Form layout="vertical" form={form}>
          <Form.Item label="Product Title">
            <Input
              placeholder="Product Title"
              onChange={(event) =>
                setProduct({
                  ...product,
                  title: event.target.value,
                })
              }
            />
          </Form.Item>
          <Form.Item label="Category">
            <Select
              showSearch
              placeholder="Select Category"
              optionFilterProp="children"
              onChange={(value) => setProduct({ ...product, category: value })}
              onSearch={onSearch}
              filterOption={filterOption}
              options={categories}
            />
          </Form.Item>
          <Form.Item label="Description">
            <Input.TextArea
              autoSize={{ minRows: 6, maxRows: 6 }}
              placeholder="Write something to describe your product..."
              onChange={(event) =>
                setProduct({
                  ...product,
                  description: event.target.value,
                })
              }
            />
          </Form.Item>
          <div className="grid gap-4 grid-cols-4">
            <Form.Item label="Price">
              <Input
                placeholder="Price"
                onChange={(event) =>
                  setProduct({
                    ...product,
                    price: event.target.value,
                  })
                }
              />
            </Form.Item>
            <Form.Item label="Stock">
              <Input
                placeholder="Stock"
                onChange={(event) =>
                  setProduct({
                    ...product,
                    stock: event.target.value,
                  })
                }
              />
            </Form.Item>
          </div>
          <ProductImageUpload/>
        </Form>
      </Card>
      <Card className="col-start-10 col-span-3 h-fit">
        <Title level={3}>Product Status</Title>
        <div>
          <Select
            options={status}
            defaultValue={status[0]}
            className="w-full"
          ></Select>
        </div>

        <Checkbox
          className="my-2"
          onChange={(event) => {
            setProduct({ ...product, isFeatured: event.target.checked });
          }}
        >
          Is Featured
        </Checkbox>

        <Button
          className="w-full"
          type="primary"
          onClick={() => {
            console.log(product);
          }}
        >
          Submit
        </Button>
      </Card>
    </div>
  );
};

export default NewProductCreate;
