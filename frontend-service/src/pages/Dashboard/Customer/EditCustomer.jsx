import React, { useEffect, useState } from 'react'
import axiosInstance from "../../../axios/axios-instance";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import { useParams } from 'react-router-dom';

const EditCustomer = () => {
    const navigate = useNavigate();

    const {id} = useParams()

    const [user, setUser] = useState({
        username: '',
        password: '',
        email: '',
        phone: '',
        role: 'user',
    })

    const {username, password, email, phone} = user
    const handleInput = (event) => {
        setUser({...user, [event.target.name]: event.target.value})
    }

    useEffect(() => {
        loadUsers();
    }, [])

    const handleSubmit = async (event) => {
        event.preventDefault()
        try {
            const response = await axiosInstance.put(`/USER-SERVICE/users/${id}`, user);
            console.log(response);
      
            if (response.status === 200) {
              Swal.fire({
                title: "User edited successfully",
                icon: "success",
                text: "User with id " + response.data.id,
                showConfirmButton: false,
                timer: 2000,
              });
              navigate('/customer')
            }
          } catch (error) {
            console.error("Error-user:", error);
          }
    }

    const loadUsers = async () => {
        const res = await axiosInstance.get(`/USER-SERVICE/users/${id}`)
        console.log(res);
        setUser(res.data)

    }
  return (
    <div className='main-container'>
      <div className='overflow-x-auto'>
        <div className='taking-order mb-0 lg:mb-6 xl:mb-0 transition-all'>
            <h1 className='main-title'>Edit Customer</h1>
            <form className="max-w-md mx-auto" onSubmit={handleSubmit} autoComplete="off">
            <div className="relative z-0 w-full mb-5 group">
                <input type="text" name="username" onChange={handleInput} id="floating_username" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required value={username} />
                <label for="floating_username" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Username</label>
            </div>
            <div className="relative z-0 w-full mb-5 group">
                <input type="email" name="email" onChange={handleInput} id="floating_email" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required value={email} />
                <label for="floating_email" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Email address</label>
            </div>
            <div className="relative z-0 w-full mb-5 group">
                <input type="password" name="password" onChange={handleInput} id="floating_password" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required value={password} />
                <label for="floating_password" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Password</label>
            </div>
            
            
            <div className="grid md:grid-cols-2 md:gap-6">
                <div className="relative z-0 w-full mb-5 group">
                    <input type="tel" name="phone" onChange={handleInput} id="floating_phone" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " required value={phone} />
                    <label for="floating_phone" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Phone number (123-456-7890)</label>
                </div>
            </div>
            <button type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
        </form>
        </div>
        
        

        

      </div>
    </div>
  )
}

export default EditCustomer