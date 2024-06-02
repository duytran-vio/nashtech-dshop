import { Button, Form, Input, message } from "antd";
import React from "react";
import { useNavigate } from "react-router-dom";
import { sendLogin, sendRegister } from "../../../services/auth";
import shopLogo from "../../../assets/shopLogo.png";
import Title from "antd/es/typography/Title";
import { Path } from "../../../utils/constant";

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

const layout = {
  labelCol: {
    span: 7,
  },
  // wrapperCol: {
  //   span: 16,
  // },
};

const CustomerRegister = () => {
  const navigate = useNavigate();

  const handleRegister = async (values) => {
    try {
      await sendRegister(values);
      message.success("Register Success");
      navigate(Path.CUSTOMER_LOGIN);
    } catch (e) {
      message.error(e.message);
    }
  };
  return (
    <div className="flex justify-center items-center min-h-screen">
      <div className="p-8 shadow-lg w-5/12">
        <div>
          <img src={shopLogo} alt="logo" className="w-20 h-20 mx-auto my-2" />
          <Title level={3} className="text-center ">
            <span className="text-sky-500">Register</span>
          </Title>
        </div>
        <Form
          {...layout}
          name="nest-messages"
          onFinish={handleRegister}
          style={{
            maxWidth: 600,
          }}
          validateMessages={validateMessages}
        >
          <Form.Item
            name="username"
            label="Username"
            rules={[
              {
                required: true,
                min: 3,
                max: 20,
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[
              {
                required: true,
                type: "email",
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            name="password"
            label="Password"
            rules={[
              {
                required: true,
                message: "Please input your password!",
              },
            ]}
            // hasFeedback
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            name="confirm"
            label="Confirm Password"
            dependencies={["password"]}
            // hasFeedback
            rules={[
              {
                required: true,
                message: "Please confirm your password!",
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("password") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("The new password that you entered do not match!")
                  );
                },
              }),
            ]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item
            wrapperCol={{
              ...layout.wrapperCol,
              offset: 7,
            }}
          >
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default CustomerRegister;
