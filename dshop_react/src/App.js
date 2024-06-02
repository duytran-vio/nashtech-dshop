import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AdminLogin from "./pages/Admin/Login";
import AdminProduct from "./pages/Admin/Product";
import AdminHome from "./pages/Admin/Home";
import AdminCategory from "./pages/Admin/Category";
import AdminCustomer from "./pages/Admin/Customer";
import { Path } from "./utils/constant";
import AdminProductDetail from "./pages/Admin/Product/ProductDetail";
import CustomerRoot from "./pages/Customer/Root";
import CustomerLogin from "./pages/Customer/Login";
import CustomerRegister from "./pages/Customer/Register";
import CustomerHomePage from "./pages/Customer/HomePage";
import CustomerProductList from "./pages/Customer/Products";
import CustomerProductDetail from "./pages/Customer/ProductDetail";

function App() {
  return (
    <Router>
      <Routes>
        {/* admin routing */}
        <Route path={Path.ADMIN_LOGIN} element={<AdminLogin />} />
        <Route path={Path.ADMIN_ROOT} element={<AdminHome />}>
          <Route path="products" element={<AdminProduct />} />
          <Route path="new-product" element={<AdminProductDetail />} />
          <Route path="products/:id" element={<AdminProductDetail />} />
          <Route path="categories" element={<AdminCategory />} />
          <Route path="customers" element={<AdminCustomer />} />
        </Route>

        {/* user routing */}
        <Route path={Path.CUSTOMER_LOGIN} element={<CustomerLogin />} />
        <Route path={Path.CUSTOMER_REGISTER} element={<CustomerRegister />} />
        <Route path={Path.CUSTOMER_ROOT} element={<CustomerRoot />} >
          <Route path={Path.CUSTOMER_HOMEPAGE} element={<CustomerHomePage />} />
          <Route path={Path.CUSTOMER_PRODUCTS} element={<CustomerProductList />} >
            <Route path=":id" element={<CustomerProductDetail />} />
          </Route>
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
