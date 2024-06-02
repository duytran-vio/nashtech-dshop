import { Button, Menu, message } from "antd";
import React, { useState } from "react";
import { PlusOutlined } from "@ant-design/icons";
import Title from "antd/es/typography/Title";
import useSWR from "swr";
import {
  addCategory,
  categoriesEndpoint,
  getCategories,
  updateCategory,
} from "../../../services/categoryService";
import Loading from "../../../components/Loading";
import CategoryUpdate from "./CategoryUpdate";

const initCategory = {
  id: null,
  categoryName: "",
  image: null,
};

const AdminCategory = () => {
  const [selectedCategory, setSelectedCategory] = useState({
    key: null,
    category: initCategory,
  });

  const {
    data: categories,
    isLoading,
    isError,
    mutate,
  } = useSWR(categoriesEndpoint, getCategories, {
    onSuccess: (data) =>
      data.sort((a, b) => a.categoryName.localeCompare(b.categoryName.toLowerCase())),
  });

  const handleAddCategory = async (newCategory) => {
    try {
      await addCategory(newCategory);
      mutate();
      message.success("Category added successfully");
    } catch (error) {
      message.error(error.message);
    }
  };

  const handleUpdateCategory = async (id, updatedCategory) => {
    try {
      await updateCategory(id, updatedCategory);
      mutate();
      message.success("Category updated successfully");
    } catch (error) {
      message.error(error.message);
    }
  };

  if (isLoading) return <Loading />;

  const handleClickCategory = (e) => {
    if (e === null) {
      setSelectedCategory({ key: null, category: initCategory });
      return;
    }
    const category = categories.find(
      (category) => category.id === parseInt(e.key, 10)
    );
    setSelectedCategory({ key: e.key, category: category });
  };

  return (
    <div className="m-5">
      <div>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => handleClickCategory(null)}
        >
          Add Category
        </Button>
      </div>
      <div className="grid grid-cols-6 gap-2 my-5">
        <div className="col-span-1 bg-white">
          <Title level={3} className="p-2 border-b-2">
            Categories
          </Title>
          <Menu
            onClick={handleClickCategory}
            className="w-full p-0"
            mode="inline"
            selectedKeys={selectedCategory.key ? [selectedCategory.key] : []}
            items={categories?.map((category) => ({
              key: category.id,
              label: category.categoryName,
            }))}
          />
        </div>

        <div className="col-start-2 col-span-5 bg-white">
          <CategoryUpdate
            currentCategory={selectedCategory.category}
            addCategory={handleAddCategory}
            updateCategory={handleUpdateCategory}
          />
        </div>
      </div>
    </div>
  );
};

export default AdminCategory;
