import { Layout, Menu, Pagination } from "antd";
import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import {
  categoriesEndpoint,
  getCategories,
} from "../../../services/categoryService";
import useSWR from "swr";
import Title from "antd/es/typography/Title";
import { getProducts, productsEndpoint } from "../../../services/productService";
import { StatusType } from "../../../utils/constant";
import ProductCard from "../../../components/ProductCard";

const initPageSize = 6;

const CustomerProductList = () => {
  const location = useLocation();
  const {categoryId} = location.state;
  
  console.log("filter by categoryId: ", categoryId);
  const [filter, setFilter] = useState({
    categoryId: categoryId,
    status: StatusType.ACTIVE,
    page: 0,
    size: initPageSize,
  });
  console.log("filter: ", filter);


  const {
    data: categories,
    isLoading,
    isError,
  } = useSWR(categoriesEndpoint, getCategories, {
    onSuccess: (data) =>
      data.sort((a, b) =>
        a.categoryName.localeCompare(b.categoryName.toLowerCase())
      ),
  });

  const {
    data: productList,
    isLoading: productLoading,
    isError: productError,
  } = useSWR(
    { url: productsEndpoint,
      params: {
        filter: filter
      }
    },
    getProducts
  );

  if (isLoading || productLoading) return <div>Loading...</div>;

  const handleClickCategory = (e) => {
    if (e === null) {
      setFilter({ ...filter, categoryId: null });
      return;
    }
    setFilter({ ...filter, categoryId: e.key });
  };

  const handleOnChangePage = (page, pageSize) => {
    setFilter({...filter, page: page - 1, size: pageSize });
  };

  return (
    <div className="grid grid-cols-6 gap-2">
      <div className="col-span-1 bg-white border rounded-lg">
        <Title level={3} className="p-2 border-b-2">
          Categories
        </Title>
        <Menu
          onClick={handleClickCategory}
          className="w-full p-0"
          mode="inline"
          selectedKeys={[filter.categoryId.toString()]}
          items={categories?.map((category) => ({
            key: category.id,
            label: category.categoryName,
          }))}
        />
      </div>

      <div className="col-start-2 col-span-5 bg-white mx-10">
        <div className="grid grid-cols-3 gap-5">
          {productList.content.map((product) => (
            <ProductCard product={product} key={product.id} />
          ))}
        </div>
        <Pagination
          className="flex justify-center my-5"
          defaultCurrent={filter.page + 1}
          total={productList.totalElements}
          defaultPageSize={initPageSize}
          onChange={handleOnChangePage}
        />
      </div>
    </div>
  );
};

export default CustomerProductList;
