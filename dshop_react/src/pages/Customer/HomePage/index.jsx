import { Avatar, Card, List } from "antd";
import React, { useState } from "react";
import shopLogo from "../../../assets/shopLogo.png";
import { UserOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { Path } from "../../../utils/constant";
import useSWR from "swr";
import {
  categoriesEndpoint,
  getCategories,
} from "../../../services/categoryService";
import CategoryRow from "./CategoryRow";

const data = [];

for (let i = 0; i < 16; i++) {
  data.push({
    title: `Title ${i}`,
    url: "http://localhost:8080/uploads/5e94a29e-cef5-4086-aff0-87f8727d3836-images.png",
  });
}

const CustomerHomePage = () => {
  const [categories, setCategories] = useState([]);
  const{data, isLoading} = useSWR(categoriesEndpoint, getCategories, {
    onSuccess: (data) => {
      data.sort((a, b) =>
        a.categoryName.localeCompare(b.categoryName.toLowerCase())
      );
      setCategories(data);
    },
  });

  if (isLoading) return <div>Loading...</div>;
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
        {categories.map((category) => {
            return <CategoryRow category={category} />;
        
        })}
      
    </div>
  );
};

export default CustomerHomePage;
