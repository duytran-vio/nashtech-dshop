import { Button, Card, Checkbox, Form, Input, Select, message } from "antd";
import { Typography } from "antd";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useSWR, { mutate } from "swr";
import {
  createProduct,
  getProductById,
  productsEndpoint,
} from "../../../services/productService";
import { Path, StatusType } from "../../../utils/constant";
import {
  categoriesEndpoint,
  getCategories,
} from "../../../services/categoryService";
import ImageUploadComponent from "../../../components/ImageUploadComponent";

const { Title } = Typography;

// Filter `option.label` match the user type `input`
const filterOption = (input, option) =>
  (option?.label ?? "").toLowerCase().includes(input.toLowerCase());

const status = [
  {
    value: StatusType.ACTIVE,
    label: StatusType.ACTIVE,
  },
  {
    value: StatusType.INACTIVE,
    label: StatusType.INACTIVE,
  },
];

const initialProduct = {
  productName: "",
  categoryId: null,
  description: "",
  price: 0,
  stock: 0,
  status: StatusType.ACTIVE,
  isFeatured: false,
};

const AdminProductDetail = () => {
  const params = useParams();
  const navigate = useNavigate();
  const isNewProduct = !params.id;

  const [form] = Form.useForm();
  const [product, setProduct] = useState(initialProduct);
  const [categories, setCategories] = useState([]);
  const [imageList, setImageList] = useState([]);

  const { mutate: thisProductMutate } = useSWR(
    isNewProduct ? null : `${productsEndpoint}/${params.id}`,
    getProductById,
    {
      onSuccess: (data) => {
        setProduct(data);
      },
    }
  );

  useSWR(categoriesEndpoint, getCategories, {
    onSuccess: (data) => {
      setCategories(
        data.map((category) => ({
          value: category.id,
          label: category.categoryName,
        }))
      );
    },
  });

  const handleAddProduct = async (product) => {
    try {
      const adminInfo = JSON.parse(localStorage.getItem("user"));
      setProduct({ ...product, createUserId: adminInfo.id });
      console.log(product);
      await createProduct(product);
      message.success("Product created successfully");
      thisProductMutate();
      mutate(productsEndpoint);
      navigate(Path.ADMIN_PRODUCTS);
    } catch (error) {
      message.error(error.message);
    }
  };

  const handleOnFinish = () => {
    console.log(product);
    product.imageIds = imageList.map((image) => image.response.id);
    handleAddProduct(product);
  };

  return (
    <div className="grid grid-cols-12 gap-2 m-5">
      <Card className="col-span-9">
        <Title level={3}>General Infomation</Title>
        <Form layout="vertical" form={form}>
          <Form.Item label="Product Title" name="productName">
            <Input
              placeholder="Product Title"
              onChange={(event) =>
                setProduct({
                  ...product,
                  productName: event.target.value,
                })
              }
            />
          </Form.Item>
          <Form.Item label="Category" name="productCategory">
            <Select
              showSearch
              placeholder="Select Category"
              optionFilterProp="children"
              onChange={(value) =>
                setProduct({ ...product, categoryId: value })
              }
              filterOption={filterOption}
              options={categories}
            />
          </Form.Item>
          <Form.Item label="Description" name="description">
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
            <Form.Item label="Price" name="price">
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
            <Form.Item label="Stock" name="stock">
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
          <ImageUploadComponent
            fileList={imageList}
            setFileList={setImageList}
            maxImageCount={5}
          />
        </Form>
      </Card>
      <Card className="col-start-10 col-span-3 h-fit">
        <Title level={3}>Product Status</Title>
        <div>
          <Select
            options={status}
            defaultValue={status.find((item) => item.value === product.status)}
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
            handleOnFinish();
          }}
        >
          Submit
        </Button>
      </Card>
    </div>
  );
};

export default AdminProductDetail;
