import Title from "antd/es/typography/Title";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Path } from "../../../utils/constant";
import ProductCard from "../../../components/ProductCard";
import useSWR from "swr";
import { getProducts, productsEndpoint } from "../../../services/productService";
import { List } from "antd";

const CategoryRow = (props) => {
  const [pageData, setPageData] = useState([]);
  const {isLoading} = useSWR({
    url: productsEndpoint,
    params: {
      filter: {
        status: "ACTIVE",
        categoryId: props.category.id,
        isFeatured: true,
        size: 4
      },
    },
  }, getProducts,
  {
    onSuccess: (data) => {
      data.content.map((item) => ({...item, key: item.id}))
      setPageData(data)
    },
  });

  if (isLoading) return <div>Loading...</div>;

  if (pageData.totalElements === 0) return <></>;
  
  return (
    <div>
      <div className="flex justify-between items-center">
        <Title level={3}>{props.category.categoryName}</Title>
        <Link to={Path.CUSTOMER_PRODUCTS}>See more... </Link>
      </div>
      <div>
      <List
          grid={{
            gutter: 8,
            column: 4,
          }}
          itemLayout="vertical"
          pagination={{
            pageSize: 4,
            position: "bottom",
            align: "center",
          }}
          dataSource={pageData.content}
          renderItem={(item) => (
            <ProductCard product={item} />
          )}
        />
        {/* <ProductCard
          product={{
            id: 17,
            productName: "Product with images",
            price: 1.3,
            avgRating: 0.0,
            soldNum: 0,
            categoryId: 2,
            status: "ACTIVE",
            isFeatured: true,
            stock: 5,
            thumbnailUrl:
              "http://localhost:8080/uploads/c51e661b-e280-4061-aa98-08fd8747576f-Pareto.png",
          }}
        /> */}
      </div>
    </div>
  );
};

export default CategoryRow;
