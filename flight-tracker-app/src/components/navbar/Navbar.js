import React from 'react';
import './navbar.css';
import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleDown, faUser } from '@fortawesome/free-solid-svg-icons';
import { faAngleUp, faRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import RegistrationForm from "./registration-form/RegistrationForm";
import LoginForm from './login-form/LoginForm';

const Navbar = ({openRegistrationForm, removeOverlay, openLoginForm, setUserAccountType}) => {
  const [showAuthenticationForms, setShowAuthenticationForms] = useState(false);
  const [showRegistrationForm, setShowRegistrationForm] = useState(false);
  const [showLoginForm, setShowLoginForm] = useState(false);

  const [authenticated, setAuthenticated] = useState(false);

  const [status, setStatus] = useState("Unregistered");
  const [username, setUsername] = useState("");
  const [accountType, setAccountType] = useState("");

  const [toggleAccountDropdown, setToggleAccountDropdown] = useState(false);

  const handleRegisterClick = () => {
    console.log('Register button clicked');
    setShowAuthenticationForms(true);
    setShowRegistrationForm(true);
    setShowLoginForm(false);
    openRegistrationForm();
  }

  const handleLoginClick = () => {
    console.log('Login button clicked');
    setShowAuthenticationForms(true);
    setShowLoginForm(true);
    setShowRegistrationForm(false);
    openLoginForm();
  }

  const handleRAngleUpClick = () => {
    console.log("Angle up clicked");
    setShowAuthenticationForms(false);
    setShowRegistrationForm(false);
    removeOverlay();
  }

  const handleLAngleUpClick = () => {
    console.log("Angle up clicked");
    setShowAuthenticationForms(false);
    setShowLoginForm(false);
    removeOverlay();
  }

  const handleRegister = (data) => {
    console.log('User authenticated: ', data);
    setAuthenticated(true);
    setStatus("Registered");
    setShowAuthenticationForms(false);
    setShowRegistrationForm(false);
    setUsername(data.name);
    setAccountType(data.accountType);
    setUserAccountType(data.accountType);
    removeOverlay();
  }

  const handleLogin = (data) => {
    console.log('User authenticated: ', data);
    setAuthenticated(true);
    setStatus("Registered");
    setShowAuthenticationForms(false);
    setShowLoginForm(false);
    setUsername(data.name);
    setAccountType(data.accountType);
    setUserAccountType(data.accountType);
    removeOverlay();
  }

  return (
    <header>
        <nav className="navbarMasterCtn">
            <div>
                <div
                    id="navbarMainContainerContent"
                    className={`d-flex align-items-center navbarMainContainer ${showAuthenticationForms ? "navbarDropdownMode" : ""}`}
                    style={{gap: '1rem'}}
                >
                    <div className="statusCtn">
                        <p><b>STATUS</b>: <span style={authenticated ? {color: "#0bf446"} : {}}><i>{status}</i></span></p>
                    </div>
                    {!authenticated &&
                        <>
                            <div className="registerCtn d-flex justify-content-center align-items-center">
                                {showRegistrationForm ?
                                    (<div
                                        className="d-flex align-items-center justify-content-end"
                                        style={{width: "106px", cursor: "pointer", color: "#FFF", fontSize: "1.5rem"}}
                                    >
                                        <FontAwesomeIcon icon={faAngleUp} style={{padding: "0.75rem"}} onClick={handleRAngleUpClick} />
                                    </div>) :
                                    (<button className="registerBtn" onClick={handleRegisterClick}>
                                        <div
                                            className="d-flex align-items-center"
                                            style={{gap: '0.5rem'}}
                                        >
                                            Register <FontAwesomeIcon icon={faUser}/>
                                        </div>
                                    </button>)
                                }
                            </div>
                            <div className="registerCtn d-flex justify-content-center align-items-center">
                                {showLoginForm ?
                                    (<div
                                        className="d-flex align-items-center justify-content-end"
                                        style={{width: "106px", cursor: "pointer", color: "#FFF", fontSize: "1.5rem"}}
                                    >
                                        <FontAwesomeIcon icon={faAngleUp} style={{padding: "0.75rem"}} onClick={handleLAngleUpClick} />
                                    </div>) :
                                    (<button className="registerBtn" onClick={handleLoginClick}>
                                        <div
                                            className="d-flex align-items-center"
                                            style={{gap: '0.5rem'}}
                                        >
                                            Login <FontAwesomeIcon icon={faUser}/>
                                        </div>
                                    </button>)
                                }
                            </div>
                        </>
                    }
                    {authenticated &&
                        <>
                            <div className="accountTypeCtn">
                                <p><b>Account Type</b>: <span style={{color: "#0bf446"}}>{accountType}</span></p>
                            </div>
                            <div>
                                <button className="registerBtn" onClick={() => { toggleAccountDropdown ? setToggleAccountDropdown(false) : setToggleAccountDropdown(true) }}>
                                    <div
                                        className="d-flex align-items-center"
                                        style={{gap: '0.5rem'}}
                                    >
                                        <p>{username}</p> 
                                        {toggleAccountDropdown ? 
                                            <FontAwesomeIcon icon={faAngleUp} style={{position: "relative"}}/> :
                                            <FontAwesomeIcon icon={faAngleDown} style={{position: "relative", top: "2px"}}/>   
                                        }

                                    </div>
                                </button>
                            </div>
                            {toggleAccountDropdown &&
                                <div className="accountDropdown">
                                    <div className="accountDropdownCtn">
                                        <div 
                                            className="accountDropdownItem d-flex align-items-center" 
                                            style={{gap: '0.5rem', cursor: "pointer"}}
                                        >
                                            <button
                                                className="text-left p-2 logoutBtn w-100"
                                                style={{all: "unset", borderRadius: "1rem", cursor: "pointer"}}
                                                onClick={() => { window.location.reload() }}
                                            >
                                                <p>Logout</p> <FontAwesomeIcon icon={faRightFromBracket} />
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            }
                        </>
                    }
                </div>
                {showRegistrationForm &&
                    <RegistrationForm persistAuthentication={handleRegister} />
                }
                {showLoginForm &&
                    <LoginForm persistAuthentication={handleLogin} />
                }
            </div>
        </nav>
    </header>
  )
}

export default Navbar;