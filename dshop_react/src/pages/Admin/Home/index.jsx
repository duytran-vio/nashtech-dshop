import React from "react";
import { Layout, Button, message } from "antd";
import SideMenu from "./SideMenu";
import { Navigate, Outlet, useNavigate } from "react-router-dom";
import { sendLogout } from "../../../services/auth";
import { Path, Role } from "../../../utils/constant";


const { Header, Content, Sider } = Layout;

const AdminHome = () => {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));

  if (user){
    if (user.role !== Role.ADMIN){
      message.error("You don't have permission to access this page");
      return <Navigate to={Path.CUSTOMER_LOGIN} />;
    }

    // handle old admin user with invalid token
  }
  else {
    message.error("Please login first");
    return <Navigate to={Path.ADMIN_LOGIN} />;
  }

  const handleLogOut = () => {
    sendLogout();
    navigate(Path.ADMIN_LOGIN);
  }


  return (
    <Layout className="min-h-screen">
      <Header className="flex justify-end items-center bg-white">
        <Button type="primary" className="logout-button" onClick={()=>handleLogOut()}>
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
