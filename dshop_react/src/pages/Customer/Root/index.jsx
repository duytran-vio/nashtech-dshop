import { Avatar, Layout } from "antd";
import { Content, Header } from "antd/es/layout/layout";
import React from "react";
import { Link, Outlet } from "react-router-dom";
import shopLogo from "../../../assets/shopLogo.png";
import Search from "antd/es/transfer/search";
import { Path } from "../../../utils/constant";
import { UserOutlined } from "@ant-design/icons";

const CustomerRoot = () => {
  return (
    <Layout className="min-h-screen">
      <Header className="grid grid-cols-12 items-center bg-white drop-shadow">
        {/* <Button type="primary" className="logout-button" onClick={()=>handleLogOut()}>
          Logout
        </Button> */}
        <div className="col-span-1 w-8">
          <Link to={Path.CUSTOMER_HOMEPAGE}>
            <img src={shopLogo} alt="shopLogo" />
          </Link>
        </div>
        <div className="col-start-5 col-span-5">
          <Search
            placeholder="input search text"
            allowClear
            enterButton="Search"
            size="large"
            // onSearch={onSearch}
          />
        </div>
        <div className="w-8 col-end-13 justify-self-end">
          <Avatar icon={<UserOutlined />}/> 
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
