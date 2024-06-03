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

const data = [];

for (let i = 0; i < 16; i++) {
  data.push({
    title: `Title ${i}`,
    url: "http://localhost:8080/uploads/5e94a29e-cef5-4086-aff0-87f8727d3836-images.png",
  });
}

const initPageSize = 8;

const CustomerHomePage = () => {
  const [page, setPage] = useState({
    page: 0,
    size: initPageSize,
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
          ...page,
        },
      },
    },
    getProducts,
  );

  const handleOnChangePage = async (page, pageSize) => {
    setPage({ page: page - 1, size: pageSize });
  };

  if (isLoading || isLoadingProducts) return <div>Loading...</div>;
  return (
    <div className="px-10">
      <div className="flex justify-center">
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
              className="flex flex-col items-center px-5"
            >
              <Avatar size={64} src={item.image?.url} alt="category" />
              <div className="text-center">{item.categoryName}</div>
            </Link>
          )}
        />
      </div>
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
        defaultCurrent={page.page + 1}
        total={featuredProducts?.totalElements}
        defaultPageSize={initPageSize}
        onChange={handleOnChangePage}
      />
    </div>
  );
};

export default CustomerHomePage;
