import { Button, Form, Input, message } from "antd";
import React from "react";
import shopLogo from "../../../assets/shopLogo.png";
import { Path, Role } from "../../../utils/constant";
import { sendLogin } from "../../../services/auth";
import { Link, useNavigate } from "react-router-dom";
import { LockOutlined, UserOutlined } from "@ant-design/icons";

const CustomerLogin = () => {
  const navigate = useNavigate();

  const handleLogin = async (values) => {
    try {
      await sendLogin(values.username, values.password, Role.CUSTOMER);
      message.success("Login Success");
      navigate(Path.CUSTOMER_HOMEPAGE);
    } catch (e) {
      message.error(e.message);
    }
  };
  return (
    <div className="flex justify-center items-center min-h-screen">
      <div className="p-10 shadow-lg w-3/12">
        <div>
          <img src={shopLogo} alt="logo" className="w-20 h-20 mx-auto my-2" />
        </div>
        <Form
          name="normal_login"
          className="login-form"
          initialValues={{
            remember: true,
          }}
          onFinish={handleLogin}
        >
          <Form.Item
            name="username"
            rules={[
              {
                required: true,
                message: "Please input your Username!",
              },
            ]}
          >
            <Input
              prefix={<UserOutlined className="site-form-item-icon" />}
              placeholder="Username"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: "Please input your Password!",
              },
            ]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="Password"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" className="w-full">
              Log in
            </Button>
          </Form.Item>
        </Form>
        <div>
          Don't have an account?{" "}
          <Link to={Path.CUSTOMER_REGISTER} className="font-medium text-sky-500">Sign up</Link>
        </div>
      </div>
    </div>
  );
};

export default CustomerLogin;
