import React, { Component } from 'react'
import axios from 'axios';


export default class UserLogin extends React.Component {
    constructor(props){
    super(props);
  
    this.state = {
      username : '',
      password : '',
    }
    this.handleInputChange = this.handleInputChange.bind(this);
  
  }

  handleInputChange(event) {
    const {name, value} = event.target;
    this.state[name] = value;
    console.log(this.state)
  }

//   handleButtonClicked = async (id) =>{
//     let body = this.state

//     //add api call here
//     //await axios.post/get

    render() {
        
        return(
          <div className='background'>
            <div>
              <form>
                <h1>Sign In</h1>
    
                <h3>
                      <label className='username'>Username: </label>
                      <input className='inputusername' type="text" name="username" onChange={this.handleInputChange}/>
                  </h3>
                  <h3>
                      <label className='password'>Password: </label>
                      <input className='inputpassword' type="text" name="password" onChange={this.handleInputChange}/>
                  </h3>
              </form>
              {/* <button onClick={this.handleButtonClicked.bind(this)}>Submit</button> */}
              <button>Submit</button>
            </div>
          </div>
        )
      }
    }

//   }