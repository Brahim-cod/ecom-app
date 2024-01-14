import React, { useEffect, useState } from "react";
import Swal from "sweetalert2";
import axiosInstance from "../../axios/axios-instance";

const ItemModal2 = ({
  singleData,
  setShowModal,
  value,
  setControl,
  control,
  userId
}) => {
  const { id, title, image, price, qty } = singleData;
  const [inputValue, setInputValue] = useState("");
 


 

  
 

  const handleOK = async (data) => {
    
    if (data) {
      const comment = inputValue;

      const runningItem = data;
      const selectedqty = value;

      

      if (selectedqty === 0) {
        Swal.fire({
          title: "Please add at least one quantity",
          icon: "error",
          text: "You will be redirected in a few moments...",
          showConfirmButton: false,
          timer: 2000,
        });
        setShowModal(false);
        return;
      }else{
        const command = {
          description : comment,
          quantity : selectedqty,
          createdAt : new Date(),
          amount : selectedqty * price,
          productId : id,
          userId : userId
        }
        console.log(command);
        var res = await axiosInstance.post('/COMMAND-SERVICE/commands', command)
        Swal.fire({
          title: "Command passed successfully with id " + res.data.id,
          icon: "success",
          text: "You will be redirected in a few moments...",
          showConfirmButton: false,
          timer: 2000,
        });
        setShowModal(false);
        setControl(!control);
      }
      
  
        setControl(!control);
      
    } else {
      setControl(!control);
    }
  };
 

  

  return (
    <>
      <div className='flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none '>
        <div className='relative  my-6 mx-auto w-[360px] h-1/2'>
          <div className='border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none'>
            <div className='modal-box'>
              <form method='dialog'>
                {/* if there is a button in form, it will close the modal */}
                <button
                  onClick={() => {
                    setShowModal(false);
                  }}
                  className='close-btn absolute right-[10px] top-[10px]'
                >
                  ✕
                </button>
              </form>
             

              
                <>
                  <div className='mt-[50px]'></div>
                </>
              

              <input
                type='text'
                name=''
                id=''
                className='modal-input ml-[20px]'
                placeholder='Write something...'
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
              />

              <button
                className="ok-btn"
                // className='ok-btn'
                onClick={() => handleOK(singleData)}
              >
                Ok
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ItemModal2;
