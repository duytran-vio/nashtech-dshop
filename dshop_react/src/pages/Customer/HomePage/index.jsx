import { Avatar, List, Pagination } from "antd";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Path } from "../../../utils/constant";
import useSWR from "swr";
import {
  categoriesEndpoint,
  getCategories,
} from "../../../services/categoryService";
import {
  getProducts,
  productsEndpoint,
} from "../../../services/productService";
import ProductCard from "../../../components/ProductCard";
import Title from "antd/es/typography/Title";

const initPageSize = 8;

const CustomerHomePage = () => {
  const [pageFilter, setPageFilter] = useState({
    page: 0,
    size: initPageSize,
    sort: "soldNum,desc",
  });
  const { data: categories, isLoading } = useSWR(
    categoriesEndpoint,
    getCategories,
    {
      onSuccess: (data) => {
        data.map((category) => ({ ...category, key: category.id }));
        data.sort((a, b) =>
          a.categoryName.localeCompare(b.categoryName.toLowerCase())
        );
      },
    }
  );

  const { data: featuredProducts, isLoading: isLoadingProducts } = useSWR(
    {
      url: productsEndpoint,
      params: {
        filter: {
          isFeatured: true,
          ...pageFilter,
        },
      },
    },
    getProducts,
  );

  const handleOnChangePage = async (page, pageSize) => {
    setPageFilter({...pageFilter, page: page - 1, size: pageSize });
  };

  if (isLoading || isLoadingProducts) return <div>Loading...</div>;
  return (
    <div className="px-10">
      {/* <div className="flex justify-center"> */}
        <List
          grid={{
            column: 8,
          }}
          itemLayout="vertical"
          pagination={{
            pageSize: 8,
            position: "bottom",
            align: "center",
          }}
          dataSource={categories}
          renderItem={(item) => (
            <Link
              to={Path.CUSTOMER_PRODUCTS}
              state={{ categoryId: item.id }}
              className="flex flex-col items-center px-5"
            >
              <Avatar size={64} src={item.image?.url} alt="category" />
              <div className="text-center">{item.categoryName}</div>
            </Link>
          )}
        />
      {/* </div> */}
      <div>
        <Title level={2}> Featured Products</Title>
        </div>
      <div className="grid grid-cols-4 gap-5">
        {featuredProducts.content.map((product) => (
          <ProductCard product={product} key={product.id} />
        ))}
      </div>
      <Pagination
        className="flex justify-center my-5"
        defaultCurrent={pageFilter.page + 1}
        total={featuredProducts?.totalElements}
        defaultPageSize={initPageSize}
        onChange={handleOnChangePage}
      />
    </div>
  );
};

export default CustomerHomePage;
