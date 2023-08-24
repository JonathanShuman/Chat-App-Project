import React, { useState, useRef } from 'react';


function LoginFormName(field) {
    const usernameRef = useRef();
    const [message, setMessage] = useState("");
    const my_users_array = JSON.parse(localStorage.getItem('users_array'));
    const handleInputFocus = (event) => {
        localStorage.setItem("active_user", usernameRef.current.value);
        var value = event.target.value;
        localStorage.setItem("currentUserName", value);
        if (my_users_array.find(user => user.username === value)) {
            localStorage.setItem("login_name_check", 1);
            setMessage("Username exist");
        }
        else {
            localStorage.setItem("login_name_check", 0);
            setMessage("Username not exist");
        }
    };

    const handleInputBlur = (event) => {
        setMessage("");
    };


    const handleInputChange = (event) => {
        localStorage.setItem("active_user", usernameRef.current.value);
        var value = event.target.value;
        localStorage.setItem("currentUserName", value);
        if (my_users_array.some(user => user.username === value)) {
            localStorage.setItem("login_name_check", 1);
            setMessage("Username exist");
        }
        else {
            localStorage.setItem("login_name_check", 0);
            setMessage("Username not exist");
        }

    };

    const getMessageClassName = () => {
        if (message === "Username exist") {
            return "valid-message";
        } else if (message === "Username not exist") {
            return "invalid-message";
        } else {
            return "";
        }
    };

    return (
        <>
            <i className="bi bi-person-circle" id="username-icon"></i>
            <div className="input-group mb-3" id="my-div-username">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Username"
                    aria-label="Username"
                    aria-describedby="basic-addon1"
                    id="username-inp"
                    onFocus={handleInputFocus} onBlur={handleInputBlur} onKeyUp={handleInputChange}
                    ref={usernameRef}
                />

                <div className={`col-auto ${getMessageClassName()}`} >  {message} </div>
            </div>
        </>
    );
}

export default LoginFormName;