import React from "react";
import { Link } from "react-router-dom";

const TableBody = ({ user, index }) => {
  //   console.log(index);
  return (
    <tr>
      <td>{user.id}</td>
      <td>{user.username}</td>
      <td>{user.email}</td>
      <td>{user.phone}</td>
      <td className="flex items-center px-6 py-4">
        <Link className="font-medium text-blue-600 dark:text-blue-500 hover:underline" to={`/editCustomer/${user.id}`}>Edit</Link>
        <a href="#" className="font-medium text-red-600 dark:text-red-500 hover:underline ms-3">Remove</a>
      </td>
    </tr>
  );
};

export default TableBody;
