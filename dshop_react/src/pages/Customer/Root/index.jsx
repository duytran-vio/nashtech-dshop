import { Button, Layout } from "antd";
import { Content, Header } from "antd/es/layout/layout";
import React from "react";
import { Outlet } from "react-router-dom";
import shopLogo from "../../../assets/shopLogo.png";
import Search from "antd/es/transfer/search";

const CustomerRoot = () => {
  return (
    <Layout className="min-h-screen">
      <Header className="grid grid-cols-12 items-center bg-white drop-shadow">
        {/* <Button type="primary" className="logout-button" onClick={()=>handleLogOut()}>
          Logout
        </Button> */}
        <div className="col-span-1 w-8">
          <img src={shopLogo} alt="shopLogo" />
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
          <img src={shopLogo} alt="shopLogo" />
        </div>
      </Header>
      <Layout>
        <div className="grid grid-cols-12 ">
        <Content className="bg-white col-start-3 col-span-8 py-10 ">
          <Outlet />
        </Content>
        </div>
      </Layout>
    </Layout>
  );
};

export default CustomerRoot;
