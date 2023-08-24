import RegistrationFormName from './registrationFormName/RegistrationFormName';
import RegistrationFormPassword from './registrationFormPassword/RegistrationFormPassword';
import RegistrationFormPicture from './registrationFormPicture/RegistrationFormPicture';
import RegisterButton from './registerButton/RegisterButton';
import './RegisterWindow.css';
import { Link } from 'react-router-dom';
function RegisterWindow() {
    localStorage.setItem("valid_username", 0);
    localStorage.setItem("valid_pass", 0);
    localStorage.setItem("valid_conf_pass", 0);
    localStorage.setItem("valid_dis_name", 0);
    localStorage.setItem("valid_pic", 0);
    localStorage.setItem("valid_username_info", "");
    localStorage.setItem("valid_pass_info", "");
    localStorage.setItem("valid_conf_pass_info", "");
    localStorage.setItem("valid_dis_name_info", "");
    localStorage.setItem("valid_pic_info", "");
    if (!localStorage.getItem("users_array")) {
        localStorage.setItem("users_array", JSON.stringify([]));
    }

    
    return (
        <div className="container text-center" id="my-div1">
            <div className="row align-items-center" id="my-row">
                <div className="col" id="my-col">
                    <p id="reg-p">Register</p>
                    <RegistrationFormName name="Username" iconName="bi bi-person-circle" />
                    <RegistrationFormPassword name="Password" iconName="bi bi-key-fill" />
                    <RegistrationFormPassword name="Confirm Password" iconName="bi bi-key-fill" />
                    <RegistrationFormName name="Display name" iconName="bi bi-people-fill" />
                    <br />
                    <RegistrationFormPicture />
                    <br /><br />
                    <div className="text-center" id="my-text-reg">
                        <RegisterButton />
                        <p id="my-reg-p">Already registered? <Link to="/login" className="link-opacity-100" >Click here</Link> to login</p>
                    </div>
                    <br />
                </div>
            </div>
        </div>
    );
}
export default RegisterWindow;
