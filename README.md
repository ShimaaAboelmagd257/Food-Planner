# Springles

Springles is a  food Planner Java project is an MVP  application designed to help users plan their meals, discover recipes, and access video tutorials for preparing meals. It utilizes various technologies and design patterns to deliver a seamless user experience.

## Features

- **Meal Recipe Display**: Browse through a collection of meal recipes fetched from the [TheMealDB API](https://www.themealdb.com/api.php).
- **Video Tutorials**: Access YouTube video tutorials linked to each recipe for step-by-step guidance on meal preparation.
- **User Authentication**: Sign in or sign up using Firebase authentication with Google or email/password credentials.
- **Search Page**: Search for specific meal recipes based on keywords or categories.
- **Profile**: View and manage user profile information, including preferences and saved favorites.
- **Home Cards**: Display recommended meal cards on the home screen for quick access to popular recipes.
- **Favorites Page**: Save your favorite meal recipes for easy access and future reference.
- **Data Persistence**: Utilize Room Persistence Library to store user data locally for offline access and improved performance.
- **Network Requests**: Fetch data from the API using Retrofit to provide up-to-date meal information.

## Design Patterns

The project follows the following design patterns:

- **Model-View-Presenter (MVP)**: Separates concerns by dividing the application into three layers - Model, View, and Presenter - to enhance maintainability and testability.
- **Repository Pattern**: Implements a repository layer to abstract the data sources (API, local database) from the rest of the application, promoting a clean architecture and easier data management.

## Technologies Used

- **Java**: The primary programming language used for developing the application.
- **Firebase Authentication**: Handles user authentication with Firebase Authentication, allowing users to sign in using Google or email/password credentials.
- **Room Persistence Library**: Provides local data storage using SQLite for caching user data and improving app performance.
- **Retrofit**: Makes network requests to the [TheMealDB API](https://www.themealdb.com/api.php) for fetching meal data.
- **YouTube API**: Integrates with the YouTube API to embed video tutorials within the application.

## Installation

To run the Food Planner Java project locally, follow these steps:

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.
