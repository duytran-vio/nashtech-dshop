import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AdminLogin from "./pages/Admin/Login";
import AdminProduct from "./pages/Admin/Product";
import AdminHome from "./pages/Admin/Home";
import AdminCategory from "./pages/Admin/Category";
import NewProductCreate from "./pages/Admin/Product/NewProduct";
import AdminCustomer from "./pages/Admin/Customer";

function App() {
  return (
    <Router>
      <Routes>
        {/* admin routing */}
        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/" element={<AdminHome />}>
          <Route path="products" element={<AdminProduct />} />
          <Route path="new-product" element={<NewProductCreate />} />
          <Route path="categories" element={<AdminCategory />} />
          <Route path="customers" element={<AdminCustomer />} />
        </Route>

        {/* user routing */}
      </Routes>
    </Router>
  );
}

export default App;
