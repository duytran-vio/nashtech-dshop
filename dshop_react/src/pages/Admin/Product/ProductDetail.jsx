import {
  Button,
  Card,
  Checkbox,
  Form,
  Input,
  InputNumber,
  Modal,
  Select,
  message,
} from "antd";
import { Typography } from "antd";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useSWR, { mutate } from "swr";
import {
  createProduct,
  getProductById,
  productsEndpoint,
  updateProduct,
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
  const adminInfo = JSON.parse(localStorage.getItem("user"));

  const [form] = Form.useForm();
  const [product, setProduct] = useState({
    ...initialProduct,
    createUserId: adminInfo.id,
  });
  const [categories, setCategories] = useState([]);
  const [imageList, setImageList] = useState([]);
  const [stockDb, setStockDb] = useState(0);

  const { mutate: thisProductMutate } = useSWR(
    isNewProduct ? null : `${productsEndpoint}/${params.id}`,
    getProductById,
    {
      onSuccess: (data) => {
        setProduct(data);
        setStockDb(data.stock);
        form.setFieldsValue(data);
        setImageList(
          data.images.map((image) => ({
            ...image,
            response: { id: image.id },
          }))
        );
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
      await createProduct(product);
      message.success("Product created successfully");
      thisProductMutate();
      mutate(productsEndpoint);
      navigate(Path.ADMIN_PRODUCTS);
    } catch (error) {
      message.error(error.message);
    }
  };

  const handleUpdateProduct = async (id, product) => {
    try {
      await updateProduct(id, product);
      message.success("Product updated successfully");
      thisProductMutate();
      mutate(productsEndpoint);
    } catch (error) {
      message.error(error.message);
    }
  };

  const handleOnFinish = () => {
    product.imageIds = imageList.map((image) => image.response.id);
    if (isNewProduct) {
      handleAddProduct(product);
    } else {
      handleUpdateProduct(params.id, product);
    }
  };

  const handleOnValuesChange = (changedValues, allValues) => {
    setProduct({ ...product, ...changedValues });
  };

  const confirmOnFisnish = () => {
    form
      .validateFields()
      .then(() => {
        Modal.confirm({
          title: "Are you sure you want to submit?",
          onOk: () => {
            handleOnFinish();
          },
        });
      })
      .catch((error) => {
        // console.log("validate failed", error);
        message.error("Please fill in all required fields");
        // return;
      });
  };

  return (
    <div className="grid grid-cols-12 gap-2 m-5">
      <Card className="col-span-9">
        <Title level={3}>General Infomation</Title>
        <Form
          layout="vertical"
          form={form}
          onValuesChange={handleOnValuesChange}
          validateMessages={{ required: "${label} is required!" }}
        >
          <Form.Item
            label="Product Title"
            name="productName"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input placeholder="Product Title" />
          </Form.Item>
          <Form.Item
            label="Category"
            name="categoryId"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Select
              showSearch
              placeholder="Select Category"
              optionFilterProp="children"
              filterOption={filterOption}
              options={categories}
            />
          </Form.Item>
          <Form.Item label="Description" name="description">
            <Input.TextArea
              autoSize={{ minRows: 6, maxRows: 6 }}
              placeholder="Write something to describe your product..."
            />
          </Form.Item>
          <div className="grid gap-4 grid-cols-4">
            <Form.Item
              label="Price"
              name="price"
              rules={[
                {
                  required: true,
                },
              ]}
            >
              {/* <Input placeholder="Price" /> */}
              <InputNumber
                min={0}
                placeholder="Price"
                controls={false}
                className="w-full"
              />
            </Form.Item>
            <Form.Item
              label="Stock"
              name="stock"
              rules={[
                {
                  required: true,
                },
              ]}
            >
              {/* <Input placeholder="Stock" /> */}
              <InputNumber
                min={!isNewProduct ? stockDb : 0}
                placeholder="Stock"
                controls={false}
                className="w-full"
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
            // defaultValue={product.status}
            value={product.status}
            className="w-full"
            onChange={(value) => {
              setProduct({ ...product, status: value });
            }}
          ></Select>
        </div>

        <Checkbox
          className="my-2"
          checked={product.isFeatured}
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
            confirmOnFisnish();
          }}
        >
          Submit
        </Button>
      </Card>
    </div>
  );
};

export default AdminProductDetail;
