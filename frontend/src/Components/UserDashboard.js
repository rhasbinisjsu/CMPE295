import React, {useState, useEffect} from "react";
import axios from 'axios';
import {Routes, Route, useHistory} from 'react-router-dom'

import Table from 'react-bootstrap/Table'
import Button from 'react-bootstrap/Button'
import Dropdown from 'react-bootstrap/Dropdown';

function UserDashboard(){
    const history = useHistory();
    const [data, setData] = useState(null)


    return(
        <div>
            <h1>User Dashboard</h1>
            <Table>
                <thead className = "header"> 
                    <tr>
                        <th>Crop #</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Vegetation</th>
                        <th>Type</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Status</th>
                    </tr>
                </thead>
            
            </Table>
            <Button>Sign Out</Button>
            <Button>Soil Health</Button>
            <Button>Show All Crops</Button>
        </div>
    )
}

export default UserDashboard;
