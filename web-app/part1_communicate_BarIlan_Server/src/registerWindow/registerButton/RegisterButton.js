import { useNavigate } from 'react-router-dom';
function RegisterButton() {
        var urlPicture = localStorage.getItem("valid_pic_info");
        const navigate = useNavigate();
        async function postUser(new_user) {
                const imageUrl = new_user["picture"]; // URL of the chosen picture

                // Fetch the image as a Blob
                const imageResponse = await fetch(imageUrl);
                const imageBlob = await imageResponse.blob();
              
                // Read the Blob as a data URL
                const reader = new FileReader();
                reader.readAsDataURL(imageBlob);
              
                // Wait for the FileReader to load the data
                await new Promise((resolve) => {
                  reader.onloadend = resolve;
                });
              
                const base64Image = reader.result; // Base64-encoded image string
                const data = {
                        username: new_user["username"],
                        password: new_user["password"],
                        displayName: new_user["display_name"],
                        profilePic: base64Image,
                }
                const res = await fetch('http://localhost:5000/api/Users', {
                        'method': 'post',
                        'headers': {
                                'Content-Type': 'application/json',
                        },
                        'body': JSON.stringify(data)
                })
               
        }

        const saveReg = (event) => {
                var new_user;
                const my_users_array = JSON.parse(localStorage.getItem('users_array'));
                var check_username = localStorage.getItem("valid_username");
                var check_pass = localStorage.getItem("valid_pass");
                var check__confirm_pass = localStorage.getItem("valid_conf_pass");
                var check_dis_name = localStorage.getItem("valid_dis_name");
                var check_pic = localStorage.getItem("valid_pic");
                var check_username_info = localStorage.getItem("valid_username_info");
                var check_pass_info = localStorage.getItem("valid_pass_info");
                var check__confirm_pass_info = localStorage.getItem("valid_conf_pass_info");
                var check_dis_name_info = localStorage.getItem("valid_dis_name_info");
                var check_pic_info = localStorage.getItem("valid_pic_info");
                if (check_username === "1" && check_pass === "1" && check__confirm_pass === "1" && check_dis_name === "1" && check_pic === "1") {
                        new_user = {
                                username: check_username_info,
                                password: check_pass_info,
                                confirm_password: check__confirm_pass_info,
                                display_name: check_dis_name_info,
                                picture: check_pic_info,
                        };
                        postUser(new_user);
                        my_users_array.push(new_user);
                        localStorage.setItem("users_array", JSON.stringify(my_users_array));
                        alert("Register Successfully");
                        navigate('/login');
                }
                else {
                        alert("Make sure all fields are valid");
                }

        };
        return (
                <button type="button" className="btn btn-outline-primary btn-outline-light" onClick={saveReg}>Register</button>
        );

}

export default RegisterButton;