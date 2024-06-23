import React, { Component, useState } from 'react'
import axios from 'axios';

export default class UserSignup extends React.Component {
    //Setting the state for the initial launch
    constructor(props){
        super(props);
        this.state= {
            basicDetails: {
                firstName:'',
                lastName:'',
                email:'',
                phoneNumber:'',
                gender:'',
                dateOfBirth:'',
                role:'',
                userName:'',
                password:'',
                dateCreated: new Date()
            },
        };
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    //On input change, it updates the state to the new value
    handleInputChange(event) {
        const {name, value} = event.target;
        this.state[name] = value;
        this.state.basicDetails[name] = value;
        console.log(this.state)
    }

    render() {
        return(
           <div>
            <h1>Sign Up for User</h1>
            <form>
                <h3>
                    <label className='firstName'>First Name: </label>
                    <input className='inputfirstname' type="text" name="firstName" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='lastName'>Last Name: </label>
                    <input className='inputlastname' type="text" name="lastName" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='email'>Email: </label>
                    <input className='inputemail' type="text" name="email" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='phoneNumber'>Phone Number: </label>
                    <input className='inputphonenumber' type="text" name="phoneNumber" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='dateOfBirth'>Date Of Birth: </label>
                    <input className='inputdateofbirth' type="text" name="dateOfBirth" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='userName'>Username: </label>
                    <input className='inputusername' type="text" name="userName" onChange={this.handleInputChange}/>
                </h3>
                <h3>
                    <label className='password'>Password: </label>
                    <input className='inputpassword' type="text" name="password" onChange={this.handleInputChange}/>
                </h3>

                
            </form>
            {/* <button onClick={this.handleButtonClicked.bind(this)}>Submit</button> */}
            <button>Submit</button>
           </div>
        )
      }
    }