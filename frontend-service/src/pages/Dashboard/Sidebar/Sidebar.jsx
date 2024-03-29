import React, { useEffect, useState } from "react";
import logo from "../../../assets/Images/logo.png";
import { NavLink, useNavigate } from "react-router-dom";
import axiosInstance from "../../../axios/axios-instance";

const Sidebar = ({ openSidebarToggle, OpenSidebar }) => {
  const navigate = useNavigate();
  const logOut = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("user")
    navigate("/login");
  };
  const [users, setUsers] = useState([]);
  const [loginUser, setLoginUser] = useState([]);
  useEffect(() => {
    async function getUsers() {
      try {
        const response = await axiosInstance.get("/USER-SERVICE/users");
        // console.log(response.data);
        setUsers(response.data);
      } catch (error) {
        console.error(error);
      }
    }
    getUsers();
  }, []);
  useEffect(() => {
    const loginUser = localStorage.getItem("accessToken");
    const adminUser = users.find((user) => user.username == loginUser);
    // console.log("hh", adminUser, loginUser);
    setLoginUser(adminUser);
  }, [users]);

  return (
    <aside
      id='sidebar'
      className={openSidebarToggle ? "sidebar-responsive" : ""}
    >
      <div className='sidebar-title'>
        <div className='sidebar-brand'>
          <img className='ml-[27px]' src={logo} alt='' />
        </div>
        <span
          className='  close-btn mr-5 flex justify-center items-center pl-2 pt-[3px]'
          onClick={OpenSidebar}
        >
          ✕
        </span>
      </div>

      <ul className='sidebar-list'>
        <NavLink
          to='/mainmenu'
          className={({ isActive }) =>
            isActive
              ? "sidebar-list-item sidebar-list-item-active"
              : "sidebar-list-item"
          }
        >
          <svg
            xmlns='http://www.w3.org/2000/svg'
            width='21'
            height='21'
            viewBox='0 0 21 21'
            fill='none'
          >
            <path
              d='M17.9604 2.06653H11.7902C11.3673 2.06653 11.0203 2.41353 11.0203 2.83645V9.00664C11.0203 9.43317 11.3673 9.77656 11.7902 9.77656H17.9604C18.3869 9.77656 18.7303 9.42955 18.7303 9.00664V2.83645C18.7303 2.41353 18.3869 2.06653 17.9604 2.06653Z'
              fill='black'
            />
            <path
              d='M14.8771 11.3199C12.7481 11.3199 11.0203 13.0477 11.0203 15.1768C11.0203 17.3058 12.7481 19.0336 14.8771 19.0336C17.0061 19.03 18.7303 17.3058 18.7339 15.1768C18.7303 13.0477 17.0061 11.3199 14.8771 11.3199Z'
              fill='black'
            />
            <path
              d='M8.70695 11.3199H2.53676C2.11385 11.3199 1.76685 11.667 1.76685 12.0899V18.2601C1.76685 18.6866 2.11385 19.03 2.53676 19.03H8.70695C9.13348 19.03 9.47687 18.683 9.47687 18.2601V12.0899C9.47687 11.667 9.13348 11.3199 8.70695 11.3199Z'
              fill='black'
            />
            <path
              d='M8.70695 2.06653H2.53676C2.11385 2.06653 1.76685 2.41353 1.76685 2.83645V9.00664C1.76685 9.43317 2.11385 9.77656 2.53676 9.77656H8.70695C9.13348 9.77656 9.47687 9.42955 9.47687 9.00664V2.83645C9.47687 2.41353 9.13348 2.06653 8.70695 2.06653Z'
              fill='black'
            />
          </svg>{" "}
          Main Menu
        </NavLink>
        <NavLink
          to='/previousOrder'
          className={({ isActive }) =>
            isActive
              ? "sidebar-list-item sidebar-list-item-active"
              : "sidebar-list-item"
          }
        >
          <svg
            xmlns='http://www.w3.org/2000/svg'
            width='20'
            height='20'
            viewBox='0 0 20 20'
            fill='none'
          >
            <path
              fill-rule='evenodd'
              clip-rule='evenodd'
              d='M6.89923 3.74868C6.73841 3.74145 6.58099 3.79654 6.45978 3.90249L1.45978 8.27749C1.39288 8.33615 1.33927 8.40844 1.30255 8.48949C1.26582 8.57054 1.24683 8.65848 1.24683 8.74746C1.24683 8.83644 1.26582 8.9244 1.30255 9.00545C1.33927 9.08649 1.39288 9.15876 1.45978 9.21743L6.45978 13.5924C6.55013 13.6714 6.6613 13.7227 6.78002 13.7402C6.89873 13.7576 7.01996 13.7406 7.12924 13.691C7.23852 13.6415 7.33124 13.5615 7.3963 13.4607C7.46136 13.3599 7.49603 13.2424 7.49616 13.1225V11.2475H8.12116C11.8594 11.2475 15.3777 13.0066 17.6207 15.9972C17.7033 16.1077 17.8202 16.1877 17.9531 16.2248C18.086 16.2619 18.2274 16.254 18.3554 16.2023C18.4833 16.1506 18.5905 16.058 18.6603 15.939C18.7301 15.82 18.7586 15.6812 18.7413 15.5443C18.1006 10.4186 13.9184 6.58818 8.95856 6.20107C8.4777 6.16351 7.98921 6.1609 7.49616 6.19008V4.37246C7.49614 4.2525 7.4616 4.13508 7.39666 4.03421C7.33173 3.93334 7.23914 3.85329 7.12995 3.80361C7.05725 3.77074 6.97894 3.7521 6.89923 3.74868Z'
              fill='black'
            />
          </svg>{" "}
          Previous Order
        </NavLink>
        
        {loginUser?.role === "ADMIN" ? (
          <>
            <NavLink
              to='/customer'
              className={({ isActive }) =>
                isActive
                  ? "sidebar-list-item sidebar-list-item-active"
                  : "sidebar-list-item"
              }
            >
              <svg
                xmlns='http://www.w3.org/2000/svg'
                width='20'
                height='20'
                viewBox='0 0 20 20'
                fill='none'
              >
                <g clip-path='url(#clip0_1_1879)'>
                  <path
                    d='M10 8.07153C11.8769 8.07153 13.3984 6.55 13.3984 4.6731C13.3984 2.79619 11.8769 1.27466 10 1.27466C8.12309 1.27466 6.60156 2.79619 6.60156 4.6731C6.60156 6.55 8.12309 8.07153 10 8.07153Z'
                    fill='black'
                  />
                  <path
                    d='M16.875 8.07153C18.0615 8.07153 19.0234 7.10965 19.0234 5.9231C19.0234 4.73655 18.0615 3.77466 16.875 3.77466C15.6885 3.77466 14.7266 4.73655 14.7266 5.9231C14.7266 7.10965 15.6885 8.07153 16.875 8.07153Z'
                    fill='black'
                  />
                  <path
                    d='M3.125 8.07153C4.31155 8.07153 5.27344 7.10965 5.27344 5.9231C5.27344 4.73655 4.31155 3.77466 3.125 3.77466C1.93845 3.77466 0.976562 4.73655 0.976562 5.9231C0.976562 7.10965 1.93845 8.07153 3.125 8.07153Z'
                    fill='black'
                  />
                  <path
                    d='M5.2418 10.0008C4.39609 9.30793 3.6302 9.39965 2.65234 9.39965C1.18984 9.39965 0 10.5825 0 12.036V16.302C0 16.9332 0.515234 17.4465 1.14883 17.4465C3.88422 17.4465 3.55469 17.496 3.55469 17.3286C3.55469 14.3057 3.19664 12.0888 5.2418 10.0008Z'
                    fill='black'
                  />
                  <path
                    d='M10.9302 9.41523C9.22222 9.27277 7.73765 9.41687 6.45715 10.4738C4.31429 12.1902 4.72668 14.5013 4.72668 17.3285C4.72668 18.0765 5.33527 18.6965 6.09465 18.6965C14.34 18.6965 14.6682 18.9625 15.1571 17.8797C15.3175 17.5135 15.2736 17.6299 15.2736 14.127C15.2736 11.3447 12.8645 9.41523 10.9302 9.41523Z'
                    fill='black'
                  />
                  <path
                    d='M17.3478 9.39967C16.3646 9.39967 15.6029 9.30889 14.7583 10.0008C16.7882 12.0733 16.4454 14.1389 16.4454 17.3286C16.4454 17.4971 16.1719 17.4465 18.8103 17.4465C19.4665 17.4465 20.0001 16.9149 20.0001 16.2614V12.036C20.0001 10.5825 18.8103 9.39967 17.3478 9.39967Z'
                    fill='black'
                  />
                </g>
                <defs>
                  <clipPath id='clip0_1_1879'>
                    <rect width='20' height='20' fill='white' />
                  </clipPath>
                </defs>
              </svg>{" "}
              Customer
            </NavLink>
          </>
        ) : (
          ""
        )}
        <NavLink
          to='/setting'
          className={({ isActive }) =>
            isActive
              ? "sidebar-list-item sidebar-list-item-active"
              : "sidebar-list-item"
          }
        >
          <svg
            xmlns='http://www.w3.org/2000/svg'
            width='20'
            height='20'
            viewBox='0 0 20 20'
            fill='none'
          >
            <path
              d='M17.2036 11.2725L16.8749 11.0825C16.4836 10.8569 16.2499 10.4519 16.2499 10C16.2499 9.54813 16.4836 9.14312 16.8749 8.9175L17.2036 8.7275C18.1011 8.20875 18.408 7.06375 17.8899 6.16625L17.2649 5.08375C16.748 4.18812 15.5986 3.88062 14.7036 4.3975L14.3749 4.58688C13.9836 4.81313 13.5155 4.81313 13.1249 4.58688C12.7336 4.36063 12.4999 3.95625 12.4999 3.50437V3.125C12.4999 2.09125 11.6586 1.25 10.6249 1.25H9.37489C8.34114 1.25 7.49989 2.09125 7.49989 3.125V3.505C7.49989 3.95687 7.26614 4.36125 6.87489 4.5875C6.48364 4.81313 6.01614 4.81375 5.62489 4.5875L5.29614 4.3975C4.40114 3.88062 3.25176 4.18812 2.73426 5.08375L2.10926 6.16625C1.59114 7.06375 1.89801 8.20937 2.79551 8.7275L3.12489 8.9175C3.51614 9.14312 3.74989 9.54813 3.74989 10C3.74989 10.4519 3.51614 10.8569 3.12489 11.0825L2.79614 11.2725C1.89864 11.7906 1.59176 12.9363 2.10989 13.8338L2.73489 14.9162C3.25239 15.8119 4.40176 16.1194 5.29614 15.6025L5.62489 15.4131C6.01614 15.1862 6.48364 15.1875 6.87489 15.4131C7.26614 15.6394 7.49989 16.0437 7.49989 16.4956V16.875C7.49989 17.9088 8.34114 18.75 9.37489 18.75H10.6249C11.6586 18.75 12.4999 17.9088 12.4999 16.875V16.495C12.4999 16.0431 12.7336 15.6388 13.1249 15.4125C13.5155 15.1869 13.9836 15.1862 14.3749 15.4125L14.7036 15.6025C15.5986 16.1188 16.748 15.8112 17.2649 14.9162L17.8899 13.8338C18.408 12.9363 18.1011 11.7906 17.2036 11.2725ZM9.99989 13.125C8.27676 13.125 6.87489 11.7231 6.87489 10C6.87489 8.27688 8.27676 6.875 9.99989 6.875C11.723 6.875 13.1249 8.27688 13.1249 10C13.1249 11.7231 11.723 13.125 9.99989 13.125Z'
              fill='black'
            />
          </svg>{" "}
          Setting
        </NavLink>
        <NavLink onClick={logOut} className='sidebar-list-item mt-[100px]'>
          <svg
            xmlns='http://www.w3.org/2000/svg'
            width='21'
            height='20'
            viewBox='0 0 21 20'
            fill='none'
          >
            <path
              d='M12.8704 10.7113C12.4795 10.7113 12.1635 11.0281 12.1635 11.4183V14.2462C12.1635 14.6358 11.8467 14.9532 11.4565 14.9532H9.33554V4.34859C9.33554 3.74484 8.95094 3.20542 8.37269 3.00464L8.16337 2.93461H11.4565C11.8467 2.93461 12.1635 3.25202 12.1635 3.64167V5.76258C12.1635 6.15274 12.4795 6.46951 12.8704 6.46951C13.2614 6.46951 13.5774 6.15274 13.5774 5.76258V3.64167C13.5774 2.47234 12.6258 1.52075 11.4565 1.52075H3.85655C3.82963 1.52075 3.8071 1.53279 3.78096 1.53629C3.74691 1.53344 3.71442 1.52075 3.67985 1.52075C2.90005 1.52075 2.26587 2.1548 2.26587 2.93461V15.6601C2.26587 16.2638 2.65047 16.8033 3.22872 17.004L7.48336 18.4223C7.62757 18.4668 7.771 18.4881 7.92168 18.4881C8.70149 18.4881 9.33554 17.8539 9.33554 17.0741V16.3671H11.4565C12.6258 16.3671 13.5774 15.4156 13.5774 14.2462V11.4183C13.5774 11.0281 13.2614 10.7113 12.8704 10.7113Z'
              fill='black'
            />
            <path
              d='M19.0261 8.0906L16.1982 5.26275C15.9961 5.06055 15.692 4.99971 15.4277 5.10923C15.164 5.21887 14.9914 5.47687 14.9914 5.76256V7.88348H12.1636C11.7733 7.88348 11.4565 8.20011 11.4565 8.5904C11.4565 8.9807 11.7733 9.29733 12.1636 9.29733H14.9914V11.4182C14.9914 11.7039 15.164 11.9619 15.4277 12.0716C15.692 12.1811 15.9961 12.1203 16.1982 11.9182L19.0261 9.09021C19.3025 8.81384 19.3025 8.36697 19.0261 8.0906Z'
              fill='black'
            />
          </svg>{" "}
          Logout
        </NavLink>
      </ul>
    </aside>
  );
};

export default Sidebar;
