import React from 'react';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons';

const LoginForm = ({persistAuthentication}) => {

  // Login and authenticate User
  const handleLogin = (event) => {
    event.preventDefault();

    console.log('Login form submitted');
    const email = document.getElementById('emailInput').value;
    const password = document.getElementById('passwordInput').value;
    console.log('Email: ', email);

    const url = `http://localhost:8080/api/auth/${email}&${password}`;
    fetch (url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Something went wrong');
      }
    }).then((data) => {
      console.log('User authenticated: ', data);
      persistAuthentication(data);
    });
  }

  return (
    <div id="registrationForm">
        <form onSubmit={handleLogin} method="GET">
            <div className="d-flex flex-column" style={{gap: '0.25rem'}}>
                <div className="d-flex flex-column">
                  <label htmlFor="emailInput">Email</label>
                  <input type="email" id="emailInput" name="email" className="formGroup" />
                </div>
                <div className="d-flex flex-column">
                  <label htmlFor="passwordInput">Password</label>
                  <input type="password" id="passwordInput" name="password" className="formGroup" />
                </div>
                <div className="mt-3 w-100">
                  <button type="submit" className="registrationBtn">Login</button>
                </div>
            </div>
        </form>
    </div>
  )
}

export default LoginForm;