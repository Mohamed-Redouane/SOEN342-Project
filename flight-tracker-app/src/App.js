import './App.css';
import './index.css';
import Navbar from './components/navbar/Navbar';
import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { faPlaneDeparture } from '@fortawesome/free-solid-svg-icons';

function App() {

  const [flights, setFlights] = useState([]);
  const [noFlights, setNoFlights] = useState(false);
  const [registered, setRegistered] = useState(false);
  const [userAccountType, setUserAccountType] = useState("");
  const [departureDates, setDepartureDates] = useState([]);
  const [departureTimes, setDepartureTimes] = useState([]);
  const [arrivalDates, setArrivalDates] = useState([]);
  const [arrivalTimes, setArrivalTimes] = useState([]);

  const zeroFlightsStyle = {
    'backgroundColor': '#FF474C',
    'color': 'white',
    'borderRadius': '0.25rem',
    'gap': '1rem'
  }

  const refreshSearch = () => {
    setFlights([]);
    setNoFlights(false);
    console.log("X clicked.")
  }

  const getFlights = async (event) => {
    event.preventDefault();
    const source = event.target.source.value;
    const destination = event.target.destination.value;
    console.log("Source: ", source);
    console.log("Destination: ", destination);
    const url = `http://localhost:8080/api/flights/${source}&${destination}`
    console.log('URL: ', url);

    fetch(url, {
      method: 'GET',
      headers: {  
        'Content-Type': 'application/json',
      },
    }).then(response => {
        if (response.ok) {
          return response.json()
        } else {  
          throw new Error('Something went wrong');
        }
      }).then((data) => { 
      if (data.length === 0) {
        setNoFlights(true);
        setTimeout(() => { 
          setNoFlights(false);
        }, 1500);
      } else {
        setNoFlights(false);
        setFlights(data);
        console.log(data[0]);
      }
    });
  }

  const [showOverlay, setshowOverlay] = useState(false);

  const handleRegistrationDisplay = () => {
    console.log('Register button clicked');
    setshowOverlay(true);
  }

  const handleCloseRForm = () => {
    setshowOverlay(false);
  }

  const setAccountType = (someType) => {
    console.log("Setting account type...");
    console.log("Account type: ", someType);
    setRegistered(true);
    setUserAccountType(someType);
  }

  return (
    <>
      <Navbar openRegistrationForm={handleRegistrationDisplay} openLoginForm={handleRegistrationDisplay} removeOverlay={handleCloseRForm} setUserAccountType={setAccountType} />
      <div id="MainContent" className={showOverlay ? "overlay" : ""}>
        <div className="App d-flex flex-column justify-content-center align-items-center" style={{height: "100vh", gap: "4rem"}}>
          <h1>Flights</h1>
          {noFlights &&
            <div className='p-3 d-flex justify-content-center align-items-center' style={zeroFlightsStyle}>
              <p>No flights found</p>
            </div>
          }
          {flights.length === 0 &&
            <div>
              <form onSubmit={getFlights} method="GET">
                <div className='d-flex flex-column justify-content-center align-items-center' style={{gap: "2rem"}}>
                  <div className="d-flex align-items-center" style={{gap: "1rem"}}>
                    <div className="d-flex flex-column" style={{gap: "0.25rem"}}>
                      <label htmlFor="sourceInput" className="formLabel"><b>Source</b></label>
                      <input id='sourceInput' className='formGroup' type="text" placeholder="Ex: YUL" name="source" />
                    </div>
                    <div className="d-flex flex-column" style={{gap: "0.25rem"}}>
                      <label htmlFor="destinationInput" className="formLabel"><b>Destination</b></label>
                      <input id='destinationInput' className='formGroup' type="text" placeholder="Ex: LAX" name="destination" />
                    </div>
                  </div>
                  <div>
                    <button className='getFlightsBtn' type="submit"><div className='d-flex align-items-center btnText'><p>Get Flights</p> <FontAwesomeIcon icon={faPlaneDeparture}/></div></button>
                  </div>
                </div>
              </form>
            </div>
          }
          {flights.length > 0 && flights.map((flight, index) => {
            return (
              <>
                {!(flight.flightType === "PRIVATE_FLIGHT" && userAccountType !== "AIRPORT_ADMIN") &&
                  <div className="d-flex flex-column" style={{gap: "3rem"}}>
                    {/* Non-Registered Visibile Data */}
                    {console.log("index: " + index)}
                    {console.log(typeof index)}
                    {console.log(departureDates[0])}
                    {console.log("Departure Dates: " + departureDates)}
                    {console.log(typeof departureDates)}
                    <div className="d-flex flex-column flightData" style={{gap: "1rem"}}>
                      <p key={flight.flightNumber}>
                        <div className="d-flex justify-content-between"><span><b>Flight Number:</b></span> <span>{flight.flightNumber}</span></div>
                      </p>
                      <p key={flight.source}>
                        <div className="d-flex justify-content-between"><span><b>Source:</b> </span><span>{flight.source}</span></div>
                      </p>
                      <p key={flight.destination}>
                        <div className="d-flex justify-content-between"><span><b>Destination:</b> </span><span>{flight.destination}</span></div>
                      </p>
                      <p key={flight.airport}>
                        <div className="d-flex justify-content-between"><span><b>Airport:</b> </span><span>{flight.airport}</span></div>
                      </p>
                      <p key={"dep" + index}>
                        <div className="d-flex justify-content-between"><span><b>Departure Time:</b></span> <span>{flight.scheduledDepartureTime}</span></div>
                      </p>
                      <p key={"arr" + index}>
                        <div className="d-flex justify-content-between"><span><b>Arrival Time:</b></span> <span>{flight.scheduledArrivalTime}</span></div>
                      </p>
                      {registered && flight.flightType !== "PRIVATE_FLIGHT" &&
                        <>
                          <div>
                            <p key={flight.airline_id}><div className="d-flex justify-content-between"><span><b>Airline:</b></span> <span>{flight.airline_name}</span></div></p>
                          </div>
                          <div>
                            <p key={flight.aircraft_id}><div className="d-flex justify-content-between"><span><b>Aircraft:</b></span> <span>{flight.aircraft_name}</span></div></p>
                          </div>
                        </>
                      }
                      {flight.flightType === "PRIVATE_FLIGHT" && userAccountType === "AIRPORT_ADMIN" &&
                        <>
                          <p key={flight.aircraft_id_ + index}><b>Aircraft Id:</b> {flight.aircraft_id}</p>
                        </>
                      }
                    </div>
                    <div>
                      <button className="getSearchBtn" onClick={refreshSearch}>
                        <div className="d-flex align-items-center btnText">
                          <p>Enter new Source-Destination Pair &nbsp;&nbsp;</p><FontAwesomeIcon icon={faArrowRight} />
                        </div>
                      </button>
                    </div>
                  </div>
                }
              </>
            )
          })}
        </div>
      </div>
    </>
  );
}

export default App;