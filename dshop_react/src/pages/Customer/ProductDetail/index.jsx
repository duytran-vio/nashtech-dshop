import React, { useState } from "react";
import { useParams } from "react-router-dom";
import useSWR from "swr";
import {
  buyProduct,
  getProductById,
  productsEndpoint,
} from "../../../services/productService";
import Title from "antd/es/typography/Title";
import { StarFilled } from "@ant-design/icons";
import {
  Avatar,
  Button,
  Card,
  Form,
  Image,
  Input,
  InputNumber,
  List,
  Rate,
  message,
  Modal
} from "antd";
import { useForm } from "antd/es/form/Form";
import { PlusOutlined, MinusOutlined } from "@ant-design/icons";
import { Default } from "../../../utils/constant";
import {
  createReview,
  getReviews,
  reviewsEndpoint,
} from "../../../services/reviewService";
// import confirm from "antd/es/modal/confirm";
const { confirm } = Modal;

const initReviewPageSize = 3;

const CustomerProductDetail = () => {
  const params = useParams();
  const { id } = params;

  const { form } = useForm();
  const [quantity, setQuantity] = useState(1);

  const [reviewFilter, setReviewFilter] = useState({
    productId: id,
    page: 0,
    size: initReviewPageSize,
  });

  const {
    data: product,
    isLoading,
    isError,
    mutate: mutateProduct,
  } = useSWR(`${productsEndpoint}/${id}`, getProductById);

  const {
    data: productReviews,
    isLoading: reviewLoading,
    isError: reviewError,
    mutate: mutateReview,
  } = useSWR(
    {
      url: reviewsEndpoint,
      params: reviewFilter,
    },
    getReviews
  );

  const handleSubmitReview = async (review) => {
    try {
      await createReview(review);
      message.success("Review submitted successfully");
      mutateReview();
    } catch (error) {
      message.error("Failed to submit review");
    }
  };

  if (isLoading) return <div>Loading...</div>;

  const renderSubImages = (images) => {
    return Array.from({ length: 4 }, (_, index) => {
      const source = images[index + 2]?.url || Default.PRODUCT_IMG;
      return (
        <div className=" m-3">
          <img
            key={index + 2}
            src={source}
            alt="thumbnail"
            className="h-20 w-fit border"
          />
        </div>
      );
    });
  };

  const confirmBuy = () => {
    confirm({
      title: `Do you want to buy ${quantity} ${product.productName}?`,
      okText: "Yes",
      okType: "primary",
      cancelText: "No",
      onOk: async () => {
        try {
          await buyProduct(id, quantity);
          message.success("Buy successfully");
          mutateProduct();
        } catch (error) {
          message.error(error.message);
        }
      },
    })
  }

  const submitReview = (values) => {
    const customer = JSON.parse(localStorage.getItem("user"));
    const review = {
      userId: customer.id,
      productId: id,
      rating: values.rating,
      content: values.review,
    };
    console.log(review);
    handleSubmitReview(review);
  };

  return (
    <div>
      <div className="grid grid-cols-2 gap-5 mx-5">
        <Card>
          <div className="flex justify-center">
            <img
              src={
                product.images[0] ? product.images[0]?.url : Default.PRODUCT_IMG
              }
              alt="thumbnail"
              className="w-fit h-60 border"
              key={product.images[0]?.id}
            />
          </div>
          <div className="flex justify-center">
            {renderSubImages(product.images)}
          </div>
        </Card>
        <Card>
          <Title level={4}>{product.productName}</Title>
          <div className="grid grid-cols-5 gap-5">
            <div className="">
              <StarFilled
                style={{
                  color: "#fadb14",
                }}
              />{" "}
              <strong>{product.avgRating}</strong>
            </div>
            <div>
              {" "}
              <strong>{product.reviewNum}</strong>{" "}
              <span className="text-gray-500 ">reviews</span>
            </div>
            <div>
              {" "}
              <strong>{product.soldNum}</strong>{" "}
              <span className="text-gray-500 ">sold</span>
            </div>
          </div>
          <Title level={1}>${product.price}</Title>
          <div>
            <Title level={5}>Quantity</Title>
            <div className="flex items-center ">
              <div>
                <Button
                  icon={<MinusOutlined />}
                  onClick={() => setQuantity((value) => value - 1)}
                />
              </div>
              <InputNumber
                min={1}
                max={product.stock}
                defaultValue={1}
                value={quantity}
                className="w-12 text-center align-middle"
                controls={false}
              />
              <div>
                <Button
                  icon={<PlusOutlined />}
                  onClick={() => setQuantity((value) => value + 1)}
                />
              </div>
            </div>
          </div>
          <div className="my-5">
            <Button
              type="primary"
              className="w-1/2"
              onClick={() => confirmBuy()}
              disabled={product.stock === 0}
            >
              {product.stock === 0 ? "Out of Stock" : "Buy"}
            </Button>
          </div>
        </Card>
      </div>
      <div className="grid grid-cols-2 gap-5 m-5 ">
        <Card className="h-fit">
          <Title level={2}>Description</Title>
          <div>
            {" "}
            aksjdfk jsldkfj alksdjflask jdflask djfasldk jasd.f sdlkfja sldkfj
            asldfk asjdfas faskl jasdda fasd asd kflasjd flksadj flskdj f;asldf
            <p>
              a dfa sdjlkf asjdlfk jasldk jalsk a dflals jdflak sdjf;as aslkdfj
              lskdj false asdjl kasjfajsh dflkjash dkfha skdjfhajsk hdflajshd
              fliuahs dfasdf askdjfh kjasdhkf hsadkjfh ksajdh flksadj a sdjfh
              kjasdhkf
            </p>
          </div>
        </Card>
        {reviewLoading ? (
          <div>Loading...</div>
        ) : (
          <Card className="">
            <Title level={2}>Reviews</Title>
            <div className="">
              <div className="">
                <Form form={form} layout="vertical" onFinish={submitReview}>
                  <Form.Item
                    label="Your Review"
                    name="review"
                    rules={[
                      {
                        max: 255,
                        message: "Review must be less than 255 characters",
                      },
                      {
                        required: true,
                        message: "Please input your review",
                      },
                    ]}
                  >
                    <Input.TextArea />
                  </Form.Item>
                  <div className="flex justify-between">
                    <Form.Item
                      // label="Rating"
                      name="rating"
                      initialValue={4}
                    >
                      <Rate defaultValue={4} />
                    </Form.Item>
                    <Form.Item>
                      <Button type="primary" htmlType="submit">
                        Submit
                      </Button>
                    </Form.Item>
                  </div>
                </Form>

                <List
                  itemLayout="horizontal"
                  size="large"
                  dataSource={productReviews?.content}
                  pagination={{
                    pageSize: initReviewPageSize,
                    total: productReviews?.totalElements,
                    onChange: (page, pageSize) => {
                      setReviewFilter({ ...reviewFilter, page: page - 1 });
                    },
                    defaultCurrent: reviewFilter.page + 1,
                  }}
                  renderItem={(item, index) => (
                    <List.Item
                      key={item.id}
                      actions={[<Rate disabled defaultValue={item.rating} />]}
                    >
                      <List.Item.Meta
                        avatar={
                          <Avatar
                            src={`https://api.dicebear.com/7.x/miniavs/svg?seed=${index}`}
                          />
                        }
                        title={item.userId}
                      />
                      {item.content}
                    </List.Item>
                  )}
                />
              </div>
            </div>
          </Card>
        )}
      </div>
    </div>
  );
};

export default CustomerProductDetail;
