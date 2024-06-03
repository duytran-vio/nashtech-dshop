import Title from "antd/es/typography/Title";
import React from "react";
import { StarFilled } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { Default, Path } from "../utils/constant";

const ProductCard = (props) => {
  return (
    <Link to={Path.CUSTOMER_PRODUCTS + "/" + props.product.id}>
      <div className="border rounded-lg w-60">
        <img
          className="w-full rounded-lg border"
          style={{ height: "200px" }}
          src={
            props.product.thumbnailUrl
              ? props.product.thumbnailUrl
              : Default.PRODUCT_IMG
          }
          alt="productImg"
        />
        <div className="p-3">
          <Title style={{ margin: 0 }} level={4}>
            {props.product.productName}
          </Title>
          <div className="text-sky-600 font-medium text-lg">
            ${props.product.price}
          </div>
          <div className="flex justify-between items-center  ">
            <div className="text-lg space-x-5">
              <StarFilled
                style={{
                  color: "#fadb14",
                }}
              />{" "}
              {props.product.avgRating}
            </div>
            <div>{props.product.soldNum} items sold</div>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default ProductCard;
