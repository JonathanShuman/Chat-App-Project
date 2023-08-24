import jwt from 'jsonwebtoken'
import userModel from '../models/userModel.js';

async function generateToken(req, res) {

    const { username, password } = req.body;

    try {
        
        // Check if the user exists in the database
        const user = await userModel.checkUserExist(req.body.username, req.body.password)
        //console.log(req.body.username);
        //console.log(req.body.password);
        //await User.findOne({ username: username, password: password });
        //console.log(user);
        
    
        if (!user) {
            console.log("User dont exist");
          return res.status(401).json({ message: 'Invalid username or password' });
          
        }
    
        // Generate a token
        const token = jwt.sign({ username: user.username}, 'secretKey');
        res.setHeader('Content-Type', 'text/plain');

        req.session.token = token;
    
        // Return the token to the client
        return res.status(200).send(token);
      } catch (error) {
        console.error('Error generating token:', error);
        return res.status(500).json({ message: 'Internal server error' });
      }


}

export default {generateToken}