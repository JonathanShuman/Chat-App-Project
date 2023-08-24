function MyUser() {
  
    
    const userInfo = JSON.parse(sessionStorage.getItem("userDetails"));
    
    return (
        <>
            <div className="chat-header">
                <img src={userInfo.profilePic} id="active-user-img" alt="image" className="round-image" />
                <p className="name" id="my-user-name">{userInfo.displayName}</p>
            </div>
        </>

    );

}

export default MyUser;