import React, { useState, useEffect } from "react";

function RegistrationFormPassword(field) {

  const [message, setMessage] = useState("");
  const [password, setPassword] = useState("");

  const handleInputFocus = (event) => {
    const isValid = validatePassword(event.target.value.trim());

    if (field.name === "Password") {
      if (!isValid) {
        localStorage.setItem("valid_pass", 0);
        setMessage("Enter valid password [Min 7 (1 cap, 1 low, 1 num)]");
      } else {
        localStorage.setItem("valid_pass", 1);
        localStorage.setItem("valid_pass_info", event.target.value);
        setMessage("Valid password");
      }
    }
    if (field.name === "Confirm Password") {
      const storedPassword = localStorage.getItem("password")
      if (event.target.value === storedPassword && event.target.value !== "") {
        localStorage.setItem("valid_conf_pass", 1);
        localStorage.setItem("valid_conf_pass_info", event.target.value);
        setMessage("Valid password");
      } else {
        localStorage.setItem("valid_conf_pass", 0);
        setMessage("Password not match");
      }
    }
  };
  const handleInputBlur = (event) => {
    setMessage("");
  };

  const handleInputChange = (event) => {
    const isValid = validatePassword(event.target.value.trim());
    if (field.name === "Password") {
      const newPassword = event.target.value;
      setPassword(newPassword);
      localStorage.setItem("password", newPassword);

      if (isValid) {
        localStorage.setItem("valid_pass", 1);
        localStorage.setItem("valid_pass_info", event.target.value);
        setMessage("Valid password");
      }
      else {
        localStorage.setItem("valid_pass", 0);
        setMessage("Enter valid password [Min 7 (1 cap, 1 low, 1 num)]");
      }
    }
    if (field.name === "Confirm Password") {
      const storedPassword = localStorage.getItem("password")

      if (event.target.value === storedPassword && event.target.value !== "") {
        localStorage.setItem("valid_conf_pass", 1);
        localStorage.setItem("valid_conf_pass_info", event.target.value);
        setMessage("Valid password");
      } else {
        localStorage.setItem("valid_conf_pass", 0);
        setMessage("Password not match");
      }
    }
  };

  const validatePassword = (password) => {
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{7,}$/;
    return regex.test(password);
  };


  const getMessageClassName = () => {
    if (message === "Valid password") {
      return "valid-message";
    } else if (message === "Enter valid password [Min 7 (1 cap, 1 low, 1 num)]" || message === "Password not match") {
      return "invalid-message";
    } else {
      return "";
    }
  };

  return (
    < >
      <span><i className={field.iconName} id="my-icons"></i></span>
      <div className="col-auto">
        <label htmlFor="inputPassword2" className="visually-hidden">Password</label>
        <input type="password" className="form-control" id="inputPassword2" placeholder={field.name}
          onFocus={handleInputFocus} onBlur={handleInputBlur} onChange={handleInputChange} />
        <div className={`col-auto ${getMessageClassName()}`} id="my-email-input">{message}</div>
      </div>
    </>
  );
}

export default RegistrationFormPassword;