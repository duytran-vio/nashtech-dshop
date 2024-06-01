import React from 'react';
import { TeamOutlined, ProductOutlined } from '@ant-design/icons';
import { Menu } from 'antd';
import { useNavigate } from 'react-router-dom';
import { Path } from '../../../utils/constant';

const items = [
  {
    key: '1',
    icon: <ProductOutlined />,
    label: 'Manage Product',
    children: [
      {
        key: Path.ADMIN_PRODUCTS,
        label: 'Product List',
        
      },
      {
        key: Path.ADMIN_NEW_PRODUCT,
        label: 'Add New Product',
      },
      {
        key: Path.ADMIN_CATEGORIES,
        label: 'Categories',
      },
    ],
  },
  {
    key: Path.ADMIN_CUSTOMERS,
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
      defaultSelectedKeys={[Path.ADMIN_PRODUCTS]}
      defaultOpenKeys={['1']}
      mode="inline"
      items={items}
    />
  );
};
export default SideMenu;