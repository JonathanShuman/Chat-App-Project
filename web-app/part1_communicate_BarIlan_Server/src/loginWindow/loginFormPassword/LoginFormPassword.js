import React, { useState } from 'react';

function LoginFormPassword(field) {
    const [message, setMessage] = useState("");
    const my_users_array = JSON.parse(localStorage.getItem('users_array'));

    const handleInputFocus = (event) => {
        var value = event.target.value;
        const curr_username = localStorage.getItem("currentUserName");
        const matchedUser = my_users_array.find(user => user.username === curr_username);
        if (matchedUser) {
            if (matchedUser.password === value) {
                localStorage.setItem("login_pass_check", 1);
                setMessage("Correct passwords");
            }
            else {
                localStorage.setItem("login_pass_check", 0);
                setMessage("Incorrect passwords");
            }
        }
    };


    const handleInputBlur = (event) => {
        setMessage("");
    };


    const handleInputChange = (event) => {
        var value = event.target.value;
        const curr_username = localStorage.getItem("currentUserName");
        const matchedUser = my_users_array.find(user => user.username === curr_username);
        if (matchedUser) {
            if (matchedUser.password === value) {
                localStorage.setItem("savepass", value);
                localStorage.setItem("login_pass_check", 1);
                setMessage("Correct passwords");
            }
            else {
                localStorage.setItem("login_pass_check", 0);
                setMessage("Incorrect passwords");
            }
        }
    };

    const getMessageClassName = () => {
        if (message === "Correct passwords") {
            return "valid-message";
        } else if (message === "Incorrect passwords") {
            return "invalid-message";
        } else {
            return "";
        }
    };



    return (
        <>
            <i className="bi bi-key-fill" id="password-icon"></i>
            <div className="input-group mb-3" id="my-div-username">
                <input
                    type="password"
                    className="form-control"
                    placeholder="Password"
                    aria-label="Password"
                    aria-describedby="basic-addon2"
                    id="password-inp"
                    onFocus={handleInputFocus} onBlur={handleInputBlur} onKeyUp={handleInputChange}
                />
                <div className={`col-auto ${getMessageClassName()}`} >  {message} </div>
            </div>
        </>
    );
}

export default LoginFormPassword;