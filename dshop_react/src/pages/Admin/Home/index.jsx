import React from "react";
import { Layout, Button } from "antd";
import SideMenu from "./SideMenu";
import { Outlet } from "react-router-dom";


const { Header, Content, Sider } = Layout;

const AdminHome = () => {
  return (
    <Layout className="min-h-screen">
      <Header className="flex justify-end items-center bg-white">
        <Button type="primary" className="logout-button">
          Logout
        </Button>
      </Header>
      <Layout>
        <Sider>
          <SideMenu />
        </Sider>
        <Content>
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
};

export default AdminHome;
