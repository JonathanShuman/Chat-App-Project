import { useNavigate } from 'react-router-dom';
import React, { useState, useRef } from 'react';

function LoginButton(field) {
  const navigate = useNavigate();

  async function postToken() {
    
    const data = {
      username: localStorage.getItem("currentUserName"),
      password: localStorage.getItem("savepass"),
    }
    const res = await fetch('http://localhost:5000/api/Tokens', {
      'method': 'post',
      'headers': {
        'Content-Type': 'application/json',
      },
      'body': JSON.stringify(data)
    })

    const token = await res.text();
   
    sessionStorage.setItem("myToken", token);
     

  }


  async function getUser() { 
    const activeUserName = localStorage.getItem("active_user");
    const my_users_array = JSON.parse(localStorage.getItem('users_array'));
    const activeUserObject = my_users_array.find(user => user.username === activeUserName);
    const token = sessionStorage.getItem("myToken");
    

    const res = await fetch(`http://localhost:5000/api/Users/${activeUserObject.username}`, {
      'method': 'get',
      'headers': {
        "authorization": `Bearer ${token}`
      },

    })
    const userDetails = await res.json();
    sessionStorage.setItem("userDetails", JSON.stringify(userDetails));
  }

   const openChat = async (event) => {
    const check_name = localStorage.getItem("login_name_check");
    const check_pass = localStorage.getItem("login_pass_check");

    if (check_name === "1" && check_pass === "1") {
      
      field.handleLogin();
      await postToken();
      
      await getUser(); 
      
      navigate('/chat');
    }
    else {

      alert("Make sure you fill the fields correctly");

    }

  };


  return (
    <div>
      <button type="button" className="btn btn-outline-light" id="loging-button" onClick={openChat}>
        Log In
      </button>
    </div>

  );

}



export default LoginButton;