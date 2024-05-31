import { Button, Form, Input } from "antd";
import React, { useEffect, useState } from "react";
import CategoryImageUpload from "./CategoryImageUpload";
import { useForm } from "antd/es/form/Form";
import Title from "antd/es/typography/Title";

/* eslint-disable no-template-curly-in-string */
const validateMessages = {
  required: "${label} is required!",
  types: {
    email: "${label} is not a valid email!",
    number: "${label} is not a valid number!",
  },
  number: {
    range: "${label} must be between ${min} and ${max}",
  },
};
/* eslint-enable no-template-curly-in-string */

const CategoryUpdate = (props) => {
  const [form] = useForm();
  const [image, setImage] = useState(props.currentCategory.image);
  const [fileList, setFileList] = useState([]);

  useEffect(() => {
    form.setFieldsValue({
      categoryName: props.currentCategory.categoryName,
    });
    setImage(props.currentCategory.image);
    if (props.currentCategory.image) {
      setFileList([props.currentCategory.image]);
    } else {
      setFileList([]);
    }
  }, [form, props.currentCategory]);

  var isNewCategory = props.currentCategory.id === null;

  const handleOnFinish = (values) => {
    values.imageId = image ? image.id : null;
    if (isNewCategory) {
      props.addCategory(values);
    } else {
      props.updateCategory(props.currentCategory.id, values);
    }
  };

  return (
    <>
      <div className="">
        <Title level={3} className="p-2 border-b-2">
          {isNewCategory ? (
            "New Category"
          ) : (
            <>
              Edit Category{" "}
              <span className="text-blue-800">
                "{props.currentCategory.categoryName}"
              </span>
            </>
          )}
        </Title>
      </div>
      <Form
        layout="vertical"
        className="p-5"
        form={form}
        onFinish={handleOnFinish}
        validateMessages={validateMessages}
      >
        <Form.Item
          label={<b>Category Name</b>}
          name="categoryName"
          rules={[
            {
              required: true,
              message: "Please input category name!",
            },
            {
              min: 3,
              message: "Category name must be at least 3 characters!",
            },
          ]}
        >
          <Input
            type="text"
            className="w-3/12"
            placeholder="Category Name"
            value={props.currentCategory.categoryName}
          />
        </Form.Item>
        <Form.Item label={<b>Category Image</b>}>
          <CategoryImageUpload
            setImage={setImage}
            image={image}
            currentCategory={props.currentCategory}
            fileList={fileList}
            setFileList={setFileList}
          />
          {/* {(image?.url) ? (<img className="w-1/4" src={image?.url} alt="category" />) : (<>No Image</>)} */}
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Save
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default CategoryUpdate;
