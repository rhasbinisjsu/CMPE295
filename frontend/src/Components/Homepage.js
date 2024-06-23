
import Table from 'react-bootstrap/Table'
import Button from 'react-bootstrap/Button'
import Dropdown from 'react-bootstrap/Dropdown';

import { Grid } from '@mui/material'
import logo from './styles/Crop.png'

import axios from 'axios';
import {Routes, Route, useHistory} from 'react-router-dom'
// import LocalHospitalSharpIcon from '@mui/icons-material/LocalHospitalSharp';
// import './styles/Homepage.css';
// import background from './styles/backgroundimg.jpeg'




function Homepage(){
    const history = useHistory();

    function handleUserLoginClick(){
        history.push("/userLogin")
    }
    function handleUserSignUpClick(){
        history.push("/userSignUp")
    }
    function handleUserDashboardClick(){
        history.push("/userDashboard")
    }
    
    return(
    <div>
        <Grid align ='center'> 
        <h1>
            
            Welcome to CropSense
            <img src={logo} alt="Logo" style={{ height: '60px', width: '60px', marginRight: '10px' }} />
        </h1>
        <div className='homepage'>
                        <Button onClick={handleUserLoginClick}>Log In </Button>
                        <Button onClick={handleUserSignUpClick}>Sign Up</Button>
                        <Button onClick={handleUserDashboardClick}>Dashboard</Button>
                    </div>
        </Grid>
    </div>
    )
}
export default Homepage;