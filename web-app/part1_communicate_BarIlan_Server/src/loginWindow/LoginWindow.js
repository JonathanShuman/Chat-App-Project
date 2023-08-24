import React from 'react';
import './LoginWindow.css';
import { Link } from 'react-router-dom';
import LoginFormName from './loginFormName/LoginFormName';
import LoginFormPassword from './loginFormPassword/LoginFormPassword';
import LoginButton from './loginButton/LoginButton';

function LoginWindow(field) {
 
  localStorage.setItem("login_name_check" , 0);
  localStorage.setItem("login_pass_check" , 0);
  const handleLogin = () => {
    field.setIsLoggedIn(true);
  }

  return (
<div className="container text-center" id="my-div1">
  <div className="row align-items-center" id="my-row">
    <div className="col" id="my-col">
      <p id="reg-p">Login</p>
     <LoginFormName />
      {/* end of username field */}
     <LoginFormPassword />
      {/* end of password field */}
      <LoginButton handleLogin={handleLogin} />
      <div>
        <p className="text-center text-white">
          Don't have an account? <Link to="/">Sign up</Link>
        </p>
      </div>
      {/* end of sign up option + login button */}
    </div>
  </div>
</div>

  );
}

export default LoginWindow;
