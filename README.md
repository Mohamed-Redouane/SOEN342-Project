# SOEN342-Project: Team 14
## Team Members:
| Name                            | ID       | Email                               | Section | GitHub Username |
|-------------------------------- |----------|-------------------------------------|---------|-----------------|
| Sisahga Phimmasone (Team Leader)| 40210015 |                                     | GG      | Sisahga         |
| Mohamed Nemroud                 | 40153847 | mohamed.nemroud@mail.concordia.ca   | GG      | Mohamed-Redouane|
| Omar Alshanyour                 | 40209637 |                                     | GG      | HelloMeFriend   |



## Guide ##

### Spring Boot (Backend & Logic) ###
* Open the 'flight-tracker' folder in a Java IDE.
* Under 'src/main/resources' create a new empty file and name it '.env'
* Copy and paste this code to connect to the Mongo Database:
```
MONGO_DATABASE="Flight-Tracker-DB"
MONGO_USER="sisahga"
MONGO_PASSWORD="flight-tracker"
MONGO_CLUSTER="flighttracker.b8zpvxc.mongodb.net/"
```
* Under 'src/main/java/_flight_tracker_boot.flighttracker' open 'FlightTrackerApplication.java'
* Run the code to start the server.

### React (Frontend) ###
* Open the 'flight-tracker-app' folder in Visual Studio Code.
* Open a new terminal and type `npm install` to install all modules and dependencies.
* Execute the following command: `npm start`
