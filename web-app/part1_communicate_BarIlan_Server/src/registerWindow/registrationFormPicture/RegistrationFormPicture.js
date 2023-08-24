import React, { useState } from 'react';

function RegistrationFormPicture(field) {
  const [selectedImage, setSelectedImage] = useState("");

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const file_url = URL.createObjectURL(file);
      setSelectedImage(file_url);
      localStorage.setItem("valid_pic", 1);
      localStorage.setItem("valid_pic_info", file_url);
    }
    else {
      localStorage.setItem("valid_pic", 0);
    }
  };

  return (
    <>
      <span>
        <i className="bi bi-cloud-upload m-3" id="my-icons"> Picture :</i>
      </span>
      <div id="my-file-img">
        <input
          className="form-control form-control-sm"
          id="formFileSm"
          type="file"
          accept="image/*"
          onChange={handleImageChange}
        />
        <img src={selectedImage} className="rounded float-end" alt="..." id="my-image" />
      </div>
    </>
  );
}

export default RegistrationFormPicture;
