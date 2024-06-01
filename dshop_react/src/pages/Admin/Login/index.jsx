import React from "react";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Checkbox, Form, Input, message } from "antd";
import shopLogo from "../../../assets/shopLogo.png"
import { sendLogin } from "../../../services/auth";
import { useNavigate } from "react-router-dom";
import { Path } from "../../../utils/constant";

const AdminLogin = () => {
  const navigate = useNavigate();
  const handleLogin = async (values) => {
    try{
      await sendLogin(values.username, values.password, "ADMIN")
      message.success("Login Success")
      navigate(Path.ADMIN_PRODUCTS)
    }
    catch(e){
      message.error(e.message);
    }
  };
  return (
    <div className="flex justify-center items-center min-h-screen">
      <div className="p-10 shadow-lg w-3/12">
        <div>
            <img src = {shopLogo} alt="logo" className="w-20 h-20 mx-auto my-2" />
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
            <Button
              type="primary"
              htmlType="submit"
              className="w-full"
            >
              Log in
            </Button>
            
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default AdminLogin;
