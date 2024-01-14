import React from "react";

const ShowMainCommentModal = ({ setShowModal3, username, comment2 }) => {
  return (
    <div className='flex justify-center items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none '>
      <div className='relative  my-6 mx-auto  w-[360px] h-1/2'>
        <div className='border-0 rounded-lg shadow-lg  flex flex-col w-full bg-white outline-none focus:outline-none'>
          <div className=''>
            <form method='dialog'>
              <button
                onClick={() => {
                  setShowModal3(false);
                }}
                className='close-btn absolute right-[10px] top-[10px]'
              >
                ✕
              </button>
            </form>

            <div className='mt-[50px] ml-[20px]'>
              <h3 className='comment-title'>Comment</h3>
              <h2 className='username'>Username: {username} </h2>

              <p className='description '> {comment2}</p>
            </div>

            <button
              className='ok-btn'
              // className='ok-btn'
              onClick={() => {
                setShowModal3(false);
              }}
            >
              Ok
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShowMainCommentModal;
