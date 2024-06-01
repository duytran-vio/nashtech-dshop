import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AdminLogin from "./pages/Admin/Login";
import AdminProduct from "./pages/Admin/Product";
import AdminHome from "./pages/Admin/Home";
import AdminCategory from "./pages/Admin/Category";
import AdminCustomer from "./pages/Admin/Customer";
import { Path } from "./utils/constant";
import AdminProductDetail from "./pages/Admin/Product/ProductDetail";

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
      </Routes>
    </Router>
  );
}

export default App;
