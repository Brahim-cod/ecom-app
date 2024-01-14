import React, { useEffect, useState } from "react";
import axiosInstance from "../../../axios/axios-instance";


const PreviousOrder = () => {

  const [commands, setCommands] = useState([]);
  useEffect(() => {
    
    getCommands();

  }, []);

  async function getCommands() {
    try {
      const user = JSON.parse(localStorage.getItem("user"));
      const response = await axiosInstance.get(`/COMMAND-SERVICE/commands/user/${user.id}`);
      // console.log(response.data);
      setCommands(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  return (

    <div className='main-container'>
      <div className='overflow-x-auto'>
        <div className="mb-7">
          Previous Order
        </div>
        

        <table id='customers'>
          {/* head */}
          <thead>
            <tr>
              <th>ID</th>
              <th>Description</th>
              <th>Quantity</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Product Title</th>
              <th>Username</th>
            </tr>
          </thead>
          <tbody>
            {commands.map((command, index) => (
              <tr key={command.id}>
                <td>{command.id}</td>
                <td>{command.description}</td>
                <td>{command.quantity}</td>
                <td>{command.createdAt}</td>
                <td>{command.amount}</td>
                <td>{command.product.title}</td>
                <td>{command.user.username}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
  </div>
  );
};

export default PreviousOrder;
