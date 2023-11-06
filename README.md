# FireFighter Mobile Application

## Overview

The FireFighter Mobile Application is a multi-tiered system designed to address critical challenges faced in fire-fighting operations, particularly when relying on volunteer firefighters. It offers features that facilitate efficient personnel coordination and rapid emergency response, ensuring that firefighters can react swiftly to incoming calls. The application is built with a minimalistic user interface for quick and intuitive decision-making in emergency situations, allowing captains and dispatch to manage more complex tasks efficiently.

## Table of Contents

- [Features](#features)
- [Authentication](#authentication)
- [Navigation](#navigation)
- [Video](#video)
- [Playing Sounds](#playing-sounds)
- [Database](#database)
- [Notification System](#notification-system)

## Features

- Multi-tiered user system with different access levels: basic firefighters, captains, dispatch officers, and municipal authorities.
- Efficient notification system that allows basic firefighters and captains to accept or reject incoming calls for rapid response.
- Comprehensive dashboard for officers and dispatch personnel with real-time tracking of responders' locations and estimated arrival times.
- Ability for dispatch officers to transfer calls to other stations in cases of insufficient personnel.
- Integration with Google Maps API for GPS-based tracking.
- Sound effects for various actions.
- Video tutorial on app usage.
- Robust back-end server and database to manage user data and facilitate API calls.
- Integration with HiveMQTT broker for real-time notifications.

## Authentication

The application includes a login system with different access levels based on user roles. The basic login credentials for the application are "username" and "password" for basic users and "dev" and "dev" for development users. User authentication can be further improved in future iterations.

## Navigation

Intents are used for navigation, allowing users to launch the Google Maps app for navigation purposes. The app can navigate to a specified latitude/longitude pair, address, or search result (e.g., "Oshawa Walmart"). In the future, the navigation functionality will be integrated directly into the app using the Google Maps API instead of relying on external apps.

## Video

The application includes video playback functionality, allowing users to view videos related to emergency procedures and guidelines.

## Playing Sounds

Users can play specific sounds as needed for emergency alerts and notifications. The application incorporates the ability to play and pause sounds, offering flexibility in sound management.

## Database

The application uses a MySQL database to store user information and other relevant data. The database is managed through a backend server, serving as a REST API endpoint for handling database interactions.

## Notification System

The notification system is a crucial component of the FireFighter Mobile Application, providing real-time updates to users. Notifications can be triggered by events such as incoming calls. The application employs the HiveMQTT broker to efficiently send and receive notifications, enhancing responsiveness and ensuring users are promptly informed about important events and alerts.
