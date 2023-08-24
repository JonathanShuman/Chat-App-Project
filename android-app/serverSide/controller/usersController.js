import userModel from '../models/userModel.js';
import firebaseModel from '../models/firebaseModel.js';
import jwt from 'jsonwebtoken'
import tokenController from './tokenController.js';

async function createUser(req, res) {
  try {
    userModel.addNewUser(req.body.username, req.body.password, req.body.displayName, req.body.profilePic);
    // Code to handle success, such as sending a response or performing additional actions
    res.sendStatus(200);
  } catch (error) {
    // Code to handle errors
    console.error(error);
    res.sendStatus(500);
  }
}

async function getUser(req, res) {
    const { username } = req.params;
    const token = req.headers.authorization.split(' ')[1];
    try {
      // Verify the token
      const decodedToken = jwt.verify(token, 'secretKey');
  
      // Check if the decoded token matches the requested username
    //   if (decodedToken.userId !== username) {
    //     return res.status(403).json({ message: 'Access denied' });
    //   }
  
      // Get the user details from the database
      const user = await userModel.getUserForToken(username);
  
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }

      const ret_user = {username:user.username,
                        displayName:user.displayName,
                        profilePic:user.profilePic};
  
      // Return the user details to the client
      return res.status(200).json(ret_user);
    } catch (error) {
      console.error('Error retrieving user:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  }



  async function addfirebaseToken(req, res) {
    try {
      firebaseModel.addOrUpdateFirebaseToken(req.body.username, req.body.firebaseToken);
      // Code to handle success, such as sending a response or performing additional actions
      res.sendStatus(200);
    } catch (error) {
      // Code to handle errors
      console.error(error);
      res.sendStatus(500);
    }
  }


export default { createUser, getUser, addfirebaseToken };

  
  
  
  
  
  
  
  
  