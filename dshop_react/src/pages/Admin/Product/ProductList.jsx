import { Button, Menu, Modal, Space, Table, Tag, message } from "antd";
import React, { useEffect, useState } from "react";
import shopLogo from "../../../assets/shopLogo.png";
import {
  deleteProduct,
  getProducts,
  productsEndpoint,
} from "../../../services/productService";
import useSWR from "swr";
import { useNavigate } from "react-router-dom";
import { Default, Path } from "../../../utils/constant";

const items = [
  {
    label: "All Products",
    key: "ALL",
  },
  {
    label: "Active",
    key: "ACTIVE",
  },
  {
    label: "Inactive",
    key: "INACTIVE",
  },
];

// const rowSelection = {
//   onChange: (selectedRowKeys, selectedRows) => {
//     console.log(
//       `selectedRowKeys: ${selectedRowKeys}`,
//       "selectedRows: ",
//       selectedRows
//     );
//   },
//   getCheckboxProps: (record) => ({
//     disabled: record.name === "Disabled User",
//     // Column configuration not to be checked
//     name: record.name,
//   }),
// };

const initPageSize = 3;

const ProductList = () => {
  // const [response, setResponse] = useState();
  const [filter, setFilter] = useState({
    status: undefined,
    page: 0,
    size: initPageSize,
  });

  const navigate = useNavigate();

  const {
    data: response,
    error,
    isLoading,
    isFetching,
    mutate,
  } = useSWR(
    { url: productsEndpoint, params: { filter: filter } },
    getProducts,
    {
      onSuccess: (data) => {
        data.content = data.content.map((product) => {
          return {
            ...product,
            key: product.id,
          };
        });
      },
    }
  );

  const handleDeleteProduct = (id, productName) => {
    Modal.confirm({
      title: `Are you sure you want to delete product "${productName}"?`,
      onOk: async () => {
        try {
          await deleteProduct(id);
          // mutate({ url: productsEndpoint, params: { filter: filter } });
          mutate();
          message.success("Delete product success");
        } catch (e) {
          message.error(e.message);
        }
      }
    });
  }
  
  

  if (error) {
    message.error("Failed to fetch products");
  }

  const handleEditProduct = (id) => {
    navigate(`${Path.ADMIN_PRODUCTS}/${id}`);
  };

  const columns = [
    {
      title: "Product",
      dataIndex: "thumbnailUrl",
      key: "img_url",
      render: (url) => <img src={url ? url : Default.PRODUCT_IMG} alt="productImg" className="size-14" />,
    },
    {
      title: "Name",
      dataIndex: "productName",
      key: "name",
    },
    {
      title: "Price",
      dataIndex: "price",
      key: "price",
    },
    {
      title: "Stock",
      key: "stock",
      dataIndex: "stock",
    },
    {
      title: "Sale",
      key: "sale",
      dataIndex: "soldNum",
    },
    {
      title: "Status",
      key: "status",
      dataIndex: "status",
      render: (status) => {
        let color = status === "ACTIVE" ? "green" : "red";
        return (
          <Tag color={color} key={status}>
            {status}
          </Tag>
        );
      },
    },
    {
      title: "Action",
      key: "key",
      render: (record) => (
        <Space size="middle">
          <Button type="primary" onClick={() => handleEditProduct(record.id)}>
            Edit
          </Button>
          <Button danger onClick={() => handleDeleteProduct(record.id, record.productName)}>
            Delete
          </Button>
        </Space>
      ),
    },
  ];

  const onChangeStatus = (e) => {
    setFilter({
      ...filter,
      status: e.key === "ALL" ? undefined : e.key,
      page: 0,
    });
  };

  const handleOnChangePage = async (page, pageSize) => {
    setFilter({ ...filter, page: page - 1, size: pageSize });
  };

  return (
    <>
      <div>
        <Menu
          onClick={onChangeStatus}
          selectedKeys={[filter.status ?? "ALL"]}
          mode="horizontal"
          items={items}
        />
      </div>
      <div className="m-3">
        <Table
          rowSelection={{
            type: "checkbox",
            //  ...rowSelection
          }}
          columns={columns}
          dataSource={response?.content}
          pagination={{
            showSizeChanger: true,
            pageSizeOptions: ["3", "5", "10", "15", "20"],
            total: response?.totalElements,
            defaultPageSize: initPageSize,
            defaultCurrent: 1,
            onChange: handleOnChangePage,
          }}
        />
      </div>
    </>
  );
};

export default ProductList;
