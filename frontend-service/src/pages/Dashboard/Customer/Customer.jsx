import React, { useEffect, useState } from "react";
import axiosInstance from "../../../axios/axios-instance";
import TableBody from "./TableBody";
import { Link, NavLink } from "react-router-dom";
import { useParams } from 'react-router-dom';




const Customer = () => {
  const [users, setUsers] = useState([]);
  useEffect(() => {
    
    getUsers();
  }, []);

  const {id} = useParams();

  async function getUsers() {
    try {
      const response = await axiosInstance.get("/USER-SERVICE/users");
      // console.log(response.data);
      setUsers(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  const deleteUser = async (id) => {
    const response = await axiosInstance.delete(`/USER-SERVICE/users/${id}`);
    getUsers()
  }


  // console.log(users);
  return (
    <div className='main-container'>
      <div className='overflow-x-auto'>
        <div className="mb-7">
          <Link 
            className="text-white bg-gradient-to-r from-red-400 via-red-500 to-red-400 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 shadow-lg shadow-red-500/50 dark:shadow-lg dark:shadow-red-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2"
            to='/addCustomer'
          >
            Add User
          </Link>
        </div>
        

        <table id='customers'>
          {/* head */}
          <thead>
            <tr>
              <th></th>
              <th>Username</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user, index) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>{user.phone}</td>
                <td className="flex items-center px-6 py-4">
                  <Link className="font-medium text-blue-600 dark:text-blue-500 hover:underline" to={`/editCustomer/${user.id}`}>Edit</Link>
                  <button onClick={() => deleteUser(user.id)} className="font-medium text-red-600 dark:text-red-500 hover:underline ms-3">Remove</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Customer;
