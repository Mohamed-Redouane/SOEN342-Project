import React from 'react';
import './RegistrationForm.css';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons';

const RegistrationForm = ({persistAuthentication}) => {

  // Register and store User
  const handleRegistration = (event) => {
    event.preventDefault();

    console.log('Registration form submitted');
    const name = document.getElementById('nameInput').value;
    const accountType = document.getElementById('accountTypeInput').value;
    const email = document.getElementById('emailInput').value;
    const password = document.getElementById('passwordInput').value;
    console.log('Name: ', name);
    console.log('Account Type: ', accountType);
    console.log('Email: ', email);

    const url = 'http://localhost:8080/api/auth';
    fetch (url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        name: name,
        accountType: accountType,
        email: email,
        password: password
      })
    }).then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Something went wrong');
      }
    }).then((data) => {
      console.log('User registered: ', data);
      persistAuthentication(data);
    });
  }

  return (
    <div id="registrationForm">
        <form onSubmit={handleRegistration} method="POST">
            <div className="d-flex flex-column" style={{gap: '0.25rem'}}>
                <div className="d-flex flex-column">
                  <label htmlFor="nameInput">Name</label>
                  <input type="text" id="nameInput" name="name" className="formGroup" />
                </div>
                <div className="d-flex flex-column">
                  <label htmlFor="accountTypeInput">Account Type</label>
                  <div className="customSelect d-flex align-items-center">
                    <select id="accountTypeInput" defaultValue={"INDIVIDUAL"} name="accountType" className="formGroup w-100">
                        <option value="INDIVIDUAL">Individual User (Client)</option>
                        <option value="AIRPORT_ADMIN">Airport Administrator</option>
                        <option value="AIRLINE_ADMIN">Airline Administrator</option>
                    </select>
                    <div className="dropdownIcon">
                        <FontAwesomeIcon icon={faAngleDown} />
                    </div>
                  </div>
                </div>
                <div className="d-flex flex-column">
                  <label htmlFor="emailInput">Email</label>
                  <input type="email" id="emailInput" name="email" className="formGroup" />
                </div>
                <div className="d-flex flex-column">
                  <label htmlFor="passwordInput">Password</label>
                  <input type="password" id="passwordInput" name="password" className="formGroup" />
                </div>
                <div className="mt-3 w-100">
                  <button type="submit" className="registrationBtn" onClick={handleRegistration}>Register</button>
                </div>
            </div>
        </form>
    </div>
  )
}

export default RegistrationForm