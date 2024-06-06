import { Avatar, Button, Layout, message } from "antd";
import { Content, Header } from "antd/es/layout/layout";
import React, { useState } from "react";
import { Link, Navigate, Outlet, useNavigate } from "react-router-dom";
import shopLogo from "../../../assets/shopLogo.png";
import Search from "antd/es/transfer/search";
import { Path, Role } from "../../../utils/constant";
import { UserOutlined } from "@ant-design/icons";
import { sendLogout } from "../../../services/auth";

const CustomerRoot = () => {
  const navigate = useNavigate();
  const handleLogOut = () => {
    sendLogout();
    navigate(Path.CUSTOMER_LOGIN);
  };

  const user = JSON.parse(localStorage.getItem("user"));
  if (user) {
    if (user.role !== Role.CUSTOMER) {
      // message.error("You don't have permission to access this page");
      localStorage.removeItem("user");
      return <Navigate to={Path.CUSTOMER_ROOT} />;
    }
  }

  return (
    <Layout className="min-h-screen">
      <Header className="grid grid-cols-12 items-center bg-white drop-shadow">
        <div className="col-span-1 w-8">
          <Link to={Path.CUSTOMER_ROOT}>
            <img src={shopLogo} alt="shopLogo" />
          </Link>
        </div>
        <div className="col-start-5 col-span-5">
          {/* <Search
            placeholder="input search text"
            allowClear
            enterButton="Search"
            size="large"
            // onSearch={onSearch}
          /> */}
        </div>
        <div className="w-8 col-end-13 justify-self-end">
          <Button
            type="primary"
            className="logout-button"
            onClick={() => handleLogOut()}
          >
            {user ? "Logout" : "Login"}
          </Button>
        </div>
      </Header>
      <div className=" flex justify-center">
        <div className=" w-3/4">
          <Content className="bg-white py-10  ">
            <Outlet />
          </Content>
        </div>
      </div>
    </Layout>
  );
};

export default CustomerRoot;
