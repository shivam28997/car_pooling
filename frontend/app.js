const API_BASE_URL = 'http://localhost:8080'; // Adjust the base URL as needed

const username = 'user'; // Replace with the actual username
const password = 'password'; // Replace with the actual password

// Fetch city names and populate dropdowns
function populateCityDropdowns() {
    axios.get(`${API_BASE_URL}/car-owner/locations`)
        .then(response => {
            const locations = response.data;

            // Create options for the dropdowns
            const createStartPoint = document.getElementById('startPoint');
            const createEndPoint = document.getElementById('endPoint');
            const updateStartPoint = document.getElementById('updateStartPoint');
            const updateEndPoint = document.getElementById('updateEndPoint');
            const pickupLocation = document.getElementById('pickupLocation');
            const dropoffLocation = document.getElementById('dropoffLocation');

            [createStartPoint, createEndPoint, updateStartPoint, updateEndPoint, pickupLocation, dropoffLocation].forEach(select => {
                select.innerHTML = '<option value="" disabled selected>Select a city</option>'; // Default option
                locations.forEach(city => {
                    const option = document.createElement('option');
                    option.value = city;
                    option.textContent = city;
                    select.appendChild(option);
                });
            });
        })
        .catch(error => {
            console.error('Error fetching locations:', error);
        });
}

// Initialize the dropdowns when the page loads
document.addEventListener('DOMContentLoaded', function() {
    populateCityDropdowns();
});

// Register a car owner
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phone').value;
    const vehicleModel = document.getElementById('vehicleModel').value;
    const licensePlate = document.getElementById('licensePlate').value;

    console.log('Submitting form with:', { name, email, phoneNumber, vehicleModel, licensePlate });

    axios.post(`${API_BASE_URL}/car-owner/register`, {
        name,
        email,
        phoneNumber,
        vehicleModel,
        licensePlate
    }, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            console.log('Response received:', response.data);
            const carOwner = response.data;
            const details = `
            <p><strong>Car Owner ID:</strong> ${carOwner.id}</p>
            <p><strong>Name:</strong> ${carOwner.name}</p>
            <p><strong>Email:</strong> ${carOwner.email}</p>
            <p><strong>Phone Number:</strong> ${carOwner.phoneNumber}</p>
            <p><strong>Vehicle Model:</strong> ${carOwner.vehicleModel}</p>
            <p><strong>License Plate:</strong> ${carOwner.licensePlate}</p>
        `;
            document.getElementById('registerResponse').innerHTML = details;
            document.getElementById('registerForm').reset();
        })
        .catch(error => {
            console.error('Error registering car owner:', error);
        });
});

// Update car owner details
document.getElementById('updateForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('carOwnerId').value;
    const name = document.getElementById('updateName').value || undefined;
    const email = document.getElementById('updateEmail').value || undefined;
    const phoneNumber = document.getElementById('updatePhone').value || undefined;
    const vehicleModel = document.getElementById('updateVehicleModel').value || undefined;
    const licensePlate = document.getElementById('updateLicensePlate').value || undefined;

    axios.put(`${API_BASE_URL}/car-owner/${carOwnerId}`, {
        name,
        email,
        phoneNumber,
        vehicleModel,
        licensePlate
    }, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const updatedCarOwner = response.data;
            const details = `
                <p><strong>Name:</strong> ${updatedCarOwner.name}</p>
                <p><strong>Email:</strong> ${updatedCarOwner.email}</p>
                <p><strong>Phone Number:</strong> ${updatedCarOwner.phoneNumber}</p>
                <p><strong>Vehicle Model:</strong> ${updatedCarOwner.vehicleModel}</p>
                <p><strong>License Plate:</strong> ${updatedCarOwner.licensePlate}</p>
            `;
            document.getElementById('updateResponse').innerHTML = details;
            document.getElementById('updateForm').reset();
        })
        .catch(error => {
            console.error('Error updating car owner:', error);
        });
});

// See car owner details
document.getElementById('getCarOwnerDetails').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('carOwnerIdk').value;

    axios.get(`${API_BASE_URL}/car-owner/${carOwnerId}`, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const carOwner = response.data;

            if (carOwner) {
                const details = `
                <p><strong>Name:</strong> ${carOwner.name}</p>
                <p><strong>Email:</strong> ${carOwner.email}</p>
                <p><strong>Phone Number:</strong> ${carOwner.phoneNumber}</p>
                <p><strong>Vehicle Model:</strong> ${carOwner.vehicleModel}</p>
                <p><strong>License Plate:</strong> ${carOwner.licensePlate}</p>
            `;
                document.getElementById('getCarOwnerResponse').innerHTML = details;
            } else {
                document.getElementById('getCarOwnerResponse').innerHTML = `<p>Owner with ID ${carOwnerId} does not exist.</p>`;
            }

            document.getElementById('getCarOwnerDetails').reset();
        })
        .catch(error => {
            console.error('Error fetching car owner details:', error);
            document.getElementById('getCarOwnerResponse').innerHTML = `<p>An error occurred while fetching the car owner details.</p>`;
        });
});

// Create a journey
document.getElementById('createJourneyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('ownerId').value;
    const startPoint = document.getElementById('startPoint').value;
    const endPoint = document.getElementById('endPoint').value;
    const availableSeats = document.getElementById('seats').value;
    const startTime = document.getElementById('startTime').value;
    const status = document.getElementById('status').value;

    axios.post(`${API_BASE_URL}/car-owner/${carOwnerId}/journeys`, {
        startPoint,
        endPoint,
        availableSeats,
        startTime,
        status
    }, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const journey = response.data;
            const details = `
            <p><strong>Journey ID:</strong> ${journey.id}</p>
            <p><strong>Start Point:</strong> ${journey.startPoint}</p>
            <p><strong>End Point:</strong> ${journey.endPoint}</p>
            <p><strong>Distance:</strong> ${journey.distance}</p>
            <p><strong>Available Seats:</strong> ${journey.availableSeats}</p>
            <p><strong>Start Time:</strong> ${new Date(journey.startTime).toLocaleString()}</p>
            <p><strong>Status:</strong> ${journey.status}</p>
        `;
            document.getElementById('createJourneyResponse').innerHTML = details;
            document.getElementById('createJourneyForm').reset();
        })
        .catch(error => {
            console.error('Error creating journey:', error);
            let errorMessage = 'An error occurred while creating the journey.';
            if (error.response && error.response.status === 409) {
                errorMessage = 'Cannot start a new journey before completing the previous one.';
            }
            document.getElementById('createJourneyResponse').innerHTML = `<p style="color: red;">${errorMessage}</p>`;
        });
});

// Update a journey
document.getElementById('updateJourneyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('carOwnerIdL').value;
    const journeyId = document.getElementById('journeyId').value;
    const startPoint = document.getElementById('updateStartPoint').value || undefined;
    const endPoint = document.getElementById('updateEndPoint').value || undefined;
    const availableSeats = document.getElementById('updateSeats').value || undefined;
    const status = document.getElementById('updateStatus').value || undefined;

    axios.put(`${API_BASE_URL}/car-owner/${carOwnerId}/journeys/${journeyId}`, {
        startPoint,
        endPoint,
        availableSeats,
        status
    }, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const updatedJourney = response.data;
            const details = `
            <p><strong>Journey ID:</strong> ${updatedJourney.id}</p>
            <p><strong>Start Point:</strong> ${updatedJourney.startPoint}</p>
            <p><strong>End Point:</strong> ${updatedJourney.endPoint}</p>
            <p><strong>Available Seats:</strong> ${updatedJourney.availableSeats}</p>
            <p><strong>Status:</strong> ${updatedJourney.status}</p>
        `;
            document.getElementById('updateJourneyResponse').innerHTML = details;
            document.getElementById('updateJourneyForm').reset();
        })
        .catch(error => {
            console.error('Error updating journey:', error);
        });
});

// View journey details
document.getElementById('viewJourneyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const journeyId = document.getElementById('viewJourneyId').value;

    axios.get(`${API_BASE_URL}/car-owner/journeys/${journeyId}`, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const journey = response.data;
            const details = `
                <p><strong>Journey ID:</strong> ${journey.id}</p>
                <p><strong>Start Point:</strong> ${journey.startPoint}</p>
                <p><strong>End Point:</strong> ${journey.endPoint}</p>
                <p><strong>Available Seats:</strong> ${journey.availableSeats}</p>
                <p><strong>Status:</strong> ${journey.status}</p>
            `;
            document.getElementById('viewJourneyResponse').innerHTML = details;
            document.getElementById('viewJourneyForm').reset();
        })
        .catch(error => {
            console.error('Error fetching journey details:', error);
        });
});

// View all journeys by car owner
document.getElementById('getJourneysForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('carOwnerIdJ').value;

    axios.get(`${API_BASE_URL}/car-owner/${carOwnerId}/journeys`, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const journeys = response.data;
            const list = journeys.map(journey => `
                <li>
                    <p><strong>Journey ID:</strong> ${journey.id}</p>
                    <p><strong>Start Point:</strong> ${journey.startPoint}</p>
                    <p><strong>End Point:</strong> ${journey.endPoint}</p>
                    <p><strong>Available Seats:</strong> ${journey.availableSeats}</p>
                    <p><strong>Status:</strong> ${journey.status}</p>
                </li>
            `).join('');
            document.getElementById('viewJourneysByOwnerResponse').innerHTML = `<ul>${list}</ul>`;
            document.getElementById('getJourneysForm').reset();
        })
        .catch(error => {
            console.error('Error fetching journeys by car owner:', error);
        });
});

// View current journey by car owner
document.getElementById('getCurrentJourneyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const carOwnerId = document.getElementById('carOwnerIdC').value;

    axios.get(`${API_BASE_URL}/car-owner/${carOwnerId}/currentJourney`, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const journey = response.data;
            if (journey) {
                const details = `
                    <p><strong>Journey ID:</strong> ${journey.id}</p>
                    <p><strong>Start Point:</strong> ${journey.startPoint}</p>
                    <p><strong>End Point:</strong> ${journey.endPoint}</p>
                    <p><strong>Available Seats:</strong> ${journey.availableSeats}</p>
                    <p><strong>Status:</strong> ${journey.status}</p>
                `;
                document.getElementById('viewCurrentJourneyResponse').innerHTML = details;
            } else {
                document.getElementById('viewCurrentJourneyResponse').innerHTML = '<p>No current journey in progress.</p>';
            }
            document.getElementById('getCurrentJourneyForm').reset();
        })
        .catch(error => {
            console.error('Error fetching current journey by car owner:', error);
            document.getElementById('viewCurrentJourneyResponse').innerHTML = '<p>Error fetching current journey. Please try again later.</p>';
        });
});


// Join a journey

document.getElementById('joinJourneyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const journeyId = document.getElementById('journeyIdL').value;
    const riderId = document.getElementById('customerId').value;
    const requestedStartPoint = document.getElementById('pickupLocation').value;
    const requestedEndPoint = document.getElementById('dropoffLocation').value;
    const requestedTravelTime = document.getElementById('travelTime').value; // Format: yyyy-MM-ddTHH:mm

    axios.post(`${API_BASE_URL}/api/customers/requestRide/${riderId}/${journeyId}`, {
        requestedStartPoint,
        requestedEndPoint,
        requestedTravelTime
    }, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const joinResponse = response.data;
            const details = `
            <p><strong>Journey ID:</strong> ${joinResponse.journey.id}</p>
            <p><strong>Customer ID:</strong> ${joinResponse.customer.id}</p>
            <p><strong>Pickup Location:</strong> ${joinResponse.requestedStartPoint}</p>
            <p><strong>Dropoff Location:</strong> ${joinResponse.requestedEndPoint}</p>
            <p><strong>Status:</strong> ${joinResponse.status}</p>
            <p><strong>Requested Travel Time:</strong> ${joinResponse.requestedTravelTime}</p>
        `;
            document.getElementById('joinJourneyResponse').innerHTML = details;
            document.getElementById('joinJourneyForm').reset();
        })
        .catch(error => {
            console.error('Error joining journey:', error);
        });
});

// Response by car owner for a ride request

document.getElementById('respondToRequestForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const rideRequestId = document.getElementById('rideRequestId').value;
    const status = document.getElementById('status').value;

    axios.put(`${API_BASE_URL}/car-owner/ride-requests/${rideRequestId}/respond`, null, {
        params: { status },
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const updatedStatus = response.data;
            document.getElementById('respondRequestResponse').innerHTML = `<p>Response sent successfully! Updated Status: ${updatedStatus}</p>`;
            document.getElementById('respondToRequestForm').reset();
        })
        .catch(error => {
            console.error('Error responding to ride request:', error);
            document.getElementById('respondRequestResponse').innerHTML = '<p>Error responding to ride request. Please try again.</p>';
        });
});

// Register a new customer

document.getElementById('registerCustomerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const customerData = {
        name: document.getElementById('customerName').value,
        email: document.getElementById('customerEmail').value,
        phoneNumber: document.getElementById('phoneNumber').value
    };

    axios.post(`${API_BASE_URL}/api/customers/register`, customerData, {
        auth: {
            username,
            password
        }
    })
        .then(response => {
            const registeredCustomer = response.data;
            const details = `
            <p><strong>ID:</strong> ${registeredCustomer.id}</p>
            <p><strong>Name:</strong> ${registeredCustomer.name}</p>
            <p><strong>Email:</strong> ${registeredCustomer.email}</p>
            <p><strong>Phone Number:</strong> ${registeredCustomer.phoneNumber}</p>
        `;
            document.getElementById('registerCustomerResponse').innerHTML = details;
            document.getElementById('registerCustomerForm').reset();
        })
        .catch(error => {
            console.error('Error registering customer:', error);
            document.getElementById('registerCustomerResponse').innerHTML = '<p>Error registering customer. Please try again later.</p>';
        });
});




