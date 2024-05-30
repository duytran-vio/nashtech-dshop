import React from 'react';
import { TeamOutlined, ProductOutlined } from '@ant-design/icons';
import { Menu } from 'antd';
import { useNavigate } from 'react-router-dom';

const items = [
  {
    key: '1',
    icon: <ProductOutlined />,
    label: 'Manage Product',
    children: [
      {
        key: '/admin/products',
        label: 'Product List',
        
      },
      {
        key: '/admin/new-product',
        label: 'Add New Product',
      },
      {
        key: '/admin/categories',
        label: 'Categories',
      },
    ],
  },
  {
    key: '/admin/customers',
    icon: <TeamOutlined />,
    label: 'Manage Customers',
  },
];

const SideMenu = () => {

  const navigate = useNavigate();

  const handleClick = (e) => {
    navigate(e.key);
  }

  return (
    <Menu
      onClick={handleClick}
      className="h-full"
      defaultSelectedKeys={['/admin/products']}
      defaultOpenKeys={['1']}
      mode="inline"
      items={items}
    />
  );
};
export default SideMenu;