import { createBrowserRouter } from "react-router-dom";
import DashBoardLayout from "../Layout/DashBoardLayout/DashBoardLayout";
import MainMenu from "../pages/Dashboard/MainMenu/MainMenu";
import PreviousOrder from "../pages/Dashboard/PreviousOrder/PreviousOrder";
import Customer from "../pages/Dashboard/Customer/Customer";
import Setting from "../pages/Dashboard/Setting/Setting";
import Login from "../pages/Login/Login";
import PrivetRoute from "../pages/PrivetRoute/PrivetRoute";
import AddCustomer from "../pages/Dashboard/Customer/AddCustomer";
import EditCustomer from "../pages/Dashboard/Customer/EditCustomer";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <PrivetRoute> <DashBoardLayout /></PrivetRoute>,
    children: [
      {
        path: "/",
        element: <MainMenu></MainMenu>,
      },
      {
        path: "mainmenu",
        element: <MainMenu></MainMenu>,
      },
      {
        path: "previousOrder",
        element: <PreviousOrder></PreviousOrder>,
      },
      {
        path: "customer",
        element: <Customer />,
      },
      {
        path: "setting",
        element: <Setting />,
      },
      {
        path: "addCustomer",
        element: <AddCustomer />
      },
      {
        path: "editCustomer/:id",
        element: <EditCustomer />
      }
    ],
  },
  {
    path: "login",
    element: <Login />,
  },
  
]);
