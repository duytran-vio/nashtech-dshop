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
        key: '11',
        label: 'Product List',
        
      },
      {
        key: '12',
        label: 'Add New Product',
      },
      {
        key: '13',
        label: 'Categories',
      },
    ],
  },
  {
    key: '2',
    icon: <TeamOutlined />,
    label: 'Manage Customers',
  },
];

const SideMenu = () => {

  const navigate = useNavigate();

  const handleClick = (e) => {
    switch (e.key) {
      case '11':
        navigate('/admin/products');
        break;
      case '12':
          navigate('/admin/new-product');
          break;
      case '13':
        navigate('/admin/categories');
        break;
      case '2':
          navigate('/admin/customers');
          break;
      default:
        break;
    }
  }

  return (
    <Menu
      onClick={handleClick}
      className="h-full"
      defaultSelectedKeys={['1']}
      defaultOpenKeys={['sub1']}
      mode="inline"
      items={items}
    />
  );
};
export default SideMenu;