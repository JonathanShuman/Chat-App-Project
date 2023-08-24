import React, { useState } from 'react';

function RegistrationFormName(field) {
  const [message, setMessage] = useState("");
  const handleInputFocus = (event) => {
    const isValid = validateEmail(event.target.value.trim());
    const my_users_array = JSON.parse(localStorage.getItem("users_array"));
    const matchedUser = my_users_array.find(user => user.username === event.target.value);
    if (field.name === "Username") {
      if (matchedUser) {
        localStorage.setItem("valid_username", 0);
        setMessage("Username already exist");
        return;
      }
      if (!isValid) {
        localStorage.setItem("valid_username", 0);
        setMessage("Enter valid email [email_name]@gmail.com");
      } else {
        localStorage.setItem("valid_username", 1);
        localStorage.setItem("valid_username_info", event.target.value);
        setMessage("Valid email address");
      }
    }
    if (field.name === "Display name") {
      setMessage("");
      if (event.target.value !== "") {
        localStorage.setItem("valid_dis_name", 1);
        localStorage.setItem("valid_dis_name_info", event.target.value);
      }
      else {
        localStorage.setItem("valid_dis_name", 0);
      }
    }
  };
  const handleInputBlur = (event) => {
    setMessage("");
  };

  const handleInputChange = (event) => {
    const isValid = validateEmail(event.target.value.trim());
    const my_users_array = JSON.parse(localStorage.getItem("users_array"));
    const matchedUser = my_users_array.find(user => user.username === event.target.value);
    if (field.name === "Username") {
      if (matchedUser) {
        localStorage.setItem("valid_username", 0);
        setMessage("Username already exist");
        return;
      }
      if (isValid) {
        localStorage.setItem("valid_username", 1);
        localStorage.setItem("valid_username_info", event.target.value);
        setMessage("Valid email address");
      } else {
        localStorage.setItem("valid_username", 0);
        setMessage("Enter valid email [email_name]@gmail.com");
      }
    }
    if (field.name === "Display name") {
      setMessage("");
      if (event.target.value !== "") {
        localStorage.setItem("valid_dis_name", 1);
        localStorage.setItem("valid_dis_name_info", event.target.value);
      }
      else {
        localStorage.setItem("valid_dis_name", 0);
      }
    }
  };

  const validateEmail = (email) => {
    const regex = /^\w+@gmail\.com$/i;
    return regex.test(email);
  };

  const getMessageClassName = () => {
    if (message === "Valid email address") {
      return "valid-message";
    } else if (message === "Enter valid email [email_name]@gmail.com" || "Username already exist") {
      return "invalid-message";
    } else {
      return "";
    }
  };


  return (
    < >
      <span><i className={field.iconName} id="my-icons"></i></span>
      <div className="col-auto">
        <input type="text" className="form-control" placeholder={field.name} aria-label={"Username"} aria-describedby="basic-addon1" id="username-inp" onFocus={handleInputFocus} onBlur={handleInputBlur} onKeyUp={handleInputChange} />
        <div className={`col-auto ${getMessageClassName()}`} id="my-email-input">{message}</div>
      </div>
    </>
  );
}

export default RegistrationFormName;