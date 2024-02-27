import './App.css';
import axios from 'axios';
import {useState, useEffect} from 'react';

function App() {

  const [flights, setFlights] = useState([]);
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");


  const getFlights = async (event) => {
    event.preventDefault();
    setSource(event.target.source.value);
    setDestination(event.target.destination.value);
    console.log("Source: ", source);
    console.log("Destination: ", destination);
    const url = `http://localhost:8080/api/flights/${source}&${destination}`
    console.log('URL: ', url);

    try {
       const response = await axios.get(url);
       console.log(response.data);
       setFlights(response.data);
     } catch(err) {
       console.log(err);
    }
  }

  return (
    <div className="App d-flex flex-column justify-content-center align-items-center" style={{height: "100vh", gap: "4rem"}}>
      <h1>Flights</h1>
      {flights.length === 0 && 
        <div>
          <form onSubmit={getFlights} method="GET">
            <div className='d-flex flex-column justify-content-center align-items-center' style={{gap: "2rem"}}>
              <div className="d-flex" style={{gap: "1rem"}}>
                <label for="sourceInput">Source</label>
                <input id='sourceInput' className='form-group' type="text" placeholder="Ex: YUL" name="source" />
                <label for="destinationInput">Destination</label>
                <input id='destinationInput' className='form-group' type="text" placeholder="Ex: LAX" name="destination" />
              </div>
              <div>
                <button className='btn btn-lg btn-outline-dark' type="submit">Get Flights</button>
              </div>
            </div>
          </form>
        </div>
      }
      {flights.length > 0 && flights.map((flight, index) => {
        return (
          <div>
            <p key={flight.airport}>Airport: {flight.airport}</p>
            <p key={flight.flightNumber}>Flight Number: {flight.flightNumber}</p>
            <p key={flight.source}>Source: {flight.source}</p>
            <p key={flight.destination}>Destination: {flight.destination}</p>
          </div>
        )
      })}
    </div>
  );
}

export default App;