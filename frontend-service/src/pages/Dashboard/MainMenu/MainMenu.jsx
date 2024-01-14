import React, { useContext, useEffect, useRef, useState } from "react";
import ItemCard from "../../ItemCard/ItemCard";
import axiosInstance from "../../../axios/axios-instance";
import { ToggleContext } from "../../../context/ToggleProvider";
import OrderItemCard from "./OrderItemCard";
import SearchItems from "../../SearchItems/SearchItems";
import Swal from "sweetalert2";

const MainMenu = () => {
  const { openOrderDetailsToggle, control, setControl, filteredData } =
    useContext(ToggleContext);

  const [storesItem, setStoresItem] = useState([]);
  // console.log(storesItem);

  const [totalQty, setTotalQty] = useState();
  // console.log(totalQty);

  const [username, setUsername] = useState([]);
  const [userId, setUserId] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const user = localStorage.getItem("accessToken");
    const loginUser = JSON.parse(localStorage.getItem("user"));
    if (loginUser?.role === "admin") {
      setIsAdmin(true);
    }

    setUsername(user);
    setUserId(loginUser.id);
  }, []);

  const [totalPrice, setTotalPrice] = useState();

  const [data, setData] = useState([]);

  const [cats, setCats] = useState([]);
  const [catName, setCatName] = useState(null);
  const [toggleState, setToggleState] = useState(1);
  const toggleTab = (index) => {
    setToggleState(index);
  };
  useEffect(() => {
    async function getCats() {
      try {
        const response = await axiosInstance.get("/PRODUCT-SERVICE/categories");
        // console.log(response.data);
        setCats(response.data);
      } catch (error) {
        console.error(error);
      }
    }
    getCats();
  }, []);
  

  const fetchDataByCategory = async (categoryId) => {
    setData([]);
    try {
      const response = await axiosInstance.get(
        `/PRODUCT-SERVICE/products/category/${categoryId}`
      );
      setData(response.data);
      console.log(response.data)
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleCategoryClick = (categoryId, index) => {
    setToggleState(index);
    fetchDataByCategory(categoryId);
  };
  useEffect(() => {
    handleCategoryClick(1, 1);
  }, []);

  useEffect(() => {
    const getStoredItem = JSON.parse(localStorage.getItem("item"));
    if (getStoredItem) {
      setStoresItem(getStoredItem);
    }
  }, [control]);
  useEffect(() => {
    let totalQty = 0;
    let totalPrice = 0;
    let totalOpPrice = 0;
    storesItem.forEach((item) => {
      item?.options?.forEach((it) => {
        totalOpPrice += parseFloat(it?.price);
      });
      totalQty += parseInt(item?.qty);
      const price = parseFloat(item?.price);
      const qty = parseInt(item?.qty);
      totalPrice += price * qty;
    });
    console.log(totalOpPrice);
    setTotalPrice(totalPrice + totalOpPrice);
    setTotalQty(totalQty);
  }, [storesItem]);

  const handleDelete = (id) => {
    console.log(id);
    const leaveItem = storesItem.filter((item) => item.uuid !== id);
    if (leaveItem) {
      setControl(!control);
      localStorage.setItem("item", JSON.stringify(leaveItem));
    } else {
      localStorage.removeItem("item");
      setControl(!control);
    }
  };





  // console.log(selectedName, selectedCode);
 
  // f
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div className='main-container xl:flex gap-[30px] '>
      <div
        className={
          openOrderDetailsToggle
            ? "hidden"
            : "taking-order mb-0 lg:mb-6 xl:mb-0 transition-all"
        }
      >
        <div className="flex flex-row justify-between align-baseline items-center">
          <h1 className='main-title'>Products</h1>
          {/* <div className='main-title pr-7'>
            <p>{username}</p>
          </div> */}
        </div>
        
        <div className='tabs-group lg:w-full xl:w-[500px] 2xl:w-full '>
          <div className='button-group'>
            {cats?.map((cate, index) => (
              <button
                key={index}
                cate
                className={
                  toggleState === index + 1 ? "tab tab-active " : "tab"
                }
                onClick={() => handleCategoryClick(cate.id, index + 1)}
              >
                <div className='tab-circle'>
                  <svg
                    xmlns='http://www.w3.org/2000/svg'
                    width='30'
                    height='30'
                    viewBox='0 0 30 30'
                    fill='none'
                  >
                    <g clip-path='url(#clip0_1_1313)'>
                      <path
                        d='M15 1.28906C7.07168 1.28906 0.644531 7.71621 0.644531 15.6445C0.644531 19.3686 2.06279 22.7613 4.38844 25.3125L4.74217 25.3711H25.2579L25.6116 25.3125C27.9372 22.7613 29.3555 19.3686 29.3555 15.6445C29.3555 7.71621 22.9283 1.28906 15 1.28906Z'
                        fill='#FFBD86'
                      />
                      <path
                        d='M23.8956 4.0033H16.6677C16.1784 4.0033 15.7782 4.36359 15.7782 4.80392C15.7782 5.24419 16.1785 5.60437 16.6677 5.60437H17.0414C17.5307 5.60437 17.9309 5.96466 17.9309 6.40494C17.9309 6.84515 17.5306 7.20527 17.0414 7.20527H16.4737C15.9845 7.20527 15.5844 7.56568 15.5844 8.00584C15.5844 8.44611 15.9845 8.80628 16.4737 8.80628H20.6936L21.5112 7.20603C21.5054 7.20591 21.4997 7.20527 21.4938 7.20527H20.9654C20.4763 7.20527 20.076 6.84515 20.076 6.40494C20.076 5.96466 20.4763 5.60437 20.9654 5.60437H23.8956C24.3849 5.60437 24.7852 5.24419 24.7852 4.80392C24.7852 4.36359 24.3848 4.0033 23.8956 4.0033Z'
                        fill='#FFE5C2'
                      />
                      <path
                        d='M24.4618 12.269H23.7963C23.7736 12.763 23.7336 13.2518 23.6778 13.7339H24.4618C25.622 13.7339 26.5658 14.6777 26.5658 15.8379V16.8404C26.5658 18.0005 25.622 18.9443 24.4618 18.9443H22.3184C22.0973 19.4545 21.8534 19.9439 21.5872 20.4092H24.4619C26.4298 20.4092 28.0307 18.8082 28.0307 16.8404V15.8379C28.0307 13.87 26.4297 12.269 24.4618 12.269Z'
                        fill='#DFF6FD'
                      />
                      <path
                        d='M19.8919 22.7937C22.244 20.3729 23.8121 16.2567 23.8761 11.5645C23.8784 11.4005 23.7472 11.2657 23.5832 11.2657H6.41678C6.25271 11.2657 6.12152 11.4005 6.12381 11.5645C6.18779 16.2566 7.75594 20.3729 10.1081 22.7937H19.8919Z'
                        fill='#F2FBFF'
                      />
                      <path
                        d='M11.865 4.42383H11.3391L12.2877 5.3724C12.8103 5.54918 13.1878 6.044 13.1878 6.62555V12.4962H14.0667V6.62555C14.0667 5.41148 13.0791 4.42383 11.865 4.42383Z'
                        fill='white'
                      />
                      <path
                        d='M6.66977 0.0857812L1.31629 5.43926C1.20186 5.55369 1.20186 5.73914 1.31629 5.85357L5.73309 10.2704C5.84752 10.3848 6.03297 10.3848 6.14741 10.2704L10.1207 6.29713C10.2688 6.149 10.4598 6.05145 10.6667 6.01828L12.3073 5.75537C12.4677 5.72965 12.5314 5.53307 12.4165 5.41822L7.08409 0.0857812C6.96965 -0.0285938 6.78415 -0.0285938 6.66977 0.0857812Z'
                        fill='#D6694B'
                      />
                      <path
                        d='M2.88438 7.26309L8.48413 1.66334C8.51582 1.63165 8.55286 1.60897 8.59223 1.59403L7.08403 0.0858252C6.96959 -0.0286084 6.78415 -0.0286084 6.66971 0.0858252L1.31629 5.4393C1.20186 5.55374 1.20186 5.73918 1.31629 5.85362L2.82051 7.3579C2.83545 7.32356 2.85631 7.29116 2.88438 7.26309Z'
                        fill='#C4573A'
                      />
                      <path
                        d='M23.2091 6.43308C22.6343 6.1012 21.8988 6.31864 21.5968 6.90968L18.7424 12.4962H20.7121L23.6004 8.07101C23.964 7.5139 23.7852 6.76571 23.2091 6.43308Z'
                        fill='#CBC4CC'
                      />
                      <path
                        d='M22.366 7.53857C22.6172 7.04703 23.1681 6.81517 23.6771 6.94156C23.5801 6.73584 23.4219 6.55607 23.2091 6.4332C22.6343 6.10133 21.8988 6.31877 21.5968 6.9098L18.7424 12.4963H19.833L22.366 7.53857Z'
                        fill='#B5ADB6'
                      />
                      <path
                        d='M11.8709 22.7937H18.129C18.9139 22.2099 19.6451 21.4053 20.292 20.3989C21.2501 18.9083 21.9433 17.1065 22.3348 15.1329L22.1526 15.0743H7.84739L7.66516 15.1329C8.05663 17.1065 8.74985 18.9083 9.70797 20.3989C10.3548 21.4053 11.086 22.2098 11.8709 22.7937Z'
                        fill='#EA9B58'
                      />
                      <path
                        d='M11.7934 22.7351H14.8513C14.0956 22.1566 13.3911 21.3722 12.7655 20.3989C11.8073 18.9083 11.1142 17.1065 10.7226 15.1329H7.66516C8.05663 17.1065 8.74985 18.9083 9.70797 20.3989C10.3335 21.3721 11.0379 22.1564 11.7934 22.7351Z'
                        fill='#D88A55'
                      />
                      <path
                        d='M7.6652 15.1329H22.3348C22.4875 14.3631 22.5944 13.5671 22.6531 12.7533C22.6654 12.5831 22.5315 12.4376 22.3608 12.4376H7.63924C7.4685 12.4376 7.33467 12.583 7.34698 12.7533C7.40569 13.5671 7.5125 14.3631 7.6652 15.1329Z'
                        fill='#FFE5C2'
                      />
                      <path
                        d='M10.708 12.4376H7.63924C7.4685 12.4376 7.33467 12.583 7.34698 12.7533C7.40569 13.5671 7.5125 14.3631 7.66526 15.1329H10.734C10.5814 14.3631 10.4745 13.5671 10.4158 12.7533C10.4035 12.583 10.5373 12.4376 10.708 12.4376Z'
                        fill='#FED2A4'
                      />
                      <path
                        d='M7.95289 25.3711H22.047C22.9826 24.9531 23.6564 24.0183 23.7247 22.9196C23.7309 22.8193 23.6495 22.7351 23.549 22.7351H6.45096C6.35047 22.7351 6.26902 22.8193 6.27524 22.9196C6.3435 24.0183 7.01733 24.9531 7.95289 25.3711Z'
                        fill='#DFF6FD'
                      />
                      <path
                        d='M7.83008 25.3125H10.8823C10.0111 24.8699 9.39272 23.9696 9.3275 22.9196C9.32129 22.8193 9.40274 22.7351 9.50322 22.7351H6.45096C6.35047 22.7351 6.26902 22.8193 6.27524 22.9196C6.34045 23.9696 6.95885 24.8699 7.83008 25.3125Z'
                        fill='#C8EFFE'
                      />
                      <path
                        d='M6.60146 27.0117L6.30908 27.0703C8.7215 28.9082 11.7331 30 15 30C18.2669 30 21.2784 28.9082 23.6909 27.0703L23.4039 27.0117H6.60146Z'
                        fill='#D6694B'
                      />
                      <path
                        d='M6.30907 27.0703H23.6908C24.383 26.543 25.0262 25.9546 25.6116 25.3125H4.38843C4.97372 25.9546 5.6169 26.543 6.30907 27.0703Z'
                        fill='#C4573A'
                      />
                      <path
                        d='M7.03499 25.3125H4.38843C4.97378 25.9546 5.61696 26.543 6.30913 27.0703H23.6909C23.9841 26.8469 24.268 26.6119 24.5429 26.3672H9.05401C8.57958 26.3672 8.11985 26.2027 7.75312 25.9018L7.03499 25.3125Z'
                        fill='#A74B30'
                      />
                      <path
                        d='M18.7784 0.996094H15.8308C15.3417 0.996094 14.9414 1.35633 14.9414 1.79666C14.9414 2.23687 15.3417 2.59705 15.8308 2.59705H18.7784C19.2676 2.59705 19.6679 2.23687 19.6679 1.79666C19.6679 1.35633 19.2676 0.996094 18.7784 0.996094Z'
                        fill='#FFE5C2'
                      />
                    </g>
                    <defs>
                      <clipPath id='clip0_1_1313'>
                        <rect width='30' height='30' fill='white' />
                      </clipPath>
                    </defs>
                  </svg>
                </div>
                <span>{cate.name}</span>
              </button>
            ))}
          </div>
        </div>

        <div className='content-tabs mt-[34px]'>
          <div
            className={
              toggleState === toggleState
                ? "content grid grid-cols-2 xs:grid-cols-3 md:grid-cols-4 lg:grid-cols-3 xl:grid-cols-3 2xl:grid-cols-4 gap-[20px] lg:gap-[15px] active-content"
                : "hidden "
            }
          >
            {filteredData.length ? (
              <>
                <SearchItems filteredData={filteredData}></SearchItems>
              </>
            ) : (
              <>
                {data?.map((singleData, index) => (
                  <ItemCard
                    key={index}
                    singleData={singleData}
                    setControl={setControl}
                    control={control}
                    userId={userId}
                  ></ItemCard>
                ))}
              </>
            )}
          </div>
          <div
            className={
              toggleState === 2
                ? "content grid md:grid-cols-4 lg:grid-cols-4 xl:grid-cols-4 gap-[15px]  active-content"
                : "hidden"
            }
          ></div>
        </div>
      </div>
      
      
    </div>
  );
};

export default MainMenu;
