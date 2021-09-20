# InterviewAssignment

## Overview
The purpose of this assignment is to create a simple App to display the current weather forecast using the OpenWeatherMap free weather API (http://api.openweathermap.org/data/2.5/weather?id=city_id&appid=appid). 

## About Application
1. App icon for opening the application

![app_icon](https://user-images.githubusercontent.com/24316533/134036696-9a44cb29-c773-474a-9f59-a8905f473462.jpg)

2. Splash Screen

![Splash_screen](https://user-images.githubusercontent.com/24316533/134036845-fb4522e4-15f1-4c2d-89db-03b2cdbd8c90.jpg)

Onclicking the application icon, application opens. The above screen shot is the screenshot of splash screen. 
Here lottie gif image is shown which is related to weather to attract the user.

3. Home Screen

![landing_home_screen](https://user-images.githubusercontent.com/24316533/134037182-a49f7987-d046-4c58-8af2-5b80e4d4a1ff.jpg)

On finishing the splash screen display, the user is landed to the home screen where user can find the button to know the weather updates about this location. 

4. Weather Information Screen

![Weather_info_screen](https://user-images.githubusercontent.com/24316533/134038071-74f9614c-a6b6-42ec-bdac-df8acb30d1a3.jpg)

- On clicking the button to view weather information, the weather api called only when the wifi is connected. 
- If wifi is not connected user is alerted to connect to wifi using a toast message.
- For the first time, if the wifi is connected, weather api is called and result is parsed and displayed to user. 
- At the same time the api is scheduled for every 2 hours and updated in sharedpreferences. During this time if the user closes and comes back, data will be displlayed to user from sharedpreferences which last last updated.
- If user is tries to get update before 2 hours from last updated result, then previous updated result will be displayed to user.

5. Alert User on Connecting to Wifi

![user_alert_wifi](https://user-images.githubusercontent.com/24316533/134038925-bbb1b2cc-eec0-459d-abf3-231cb8487a64.jpg)

If the user tries to check the weather information when wifi is not connected, user get alert message in toast to connect to the wifi and try again.

## Technologies Used
- Kotlin
- MVVM
- View Binding
- WorkManager(PeriodicWorkRequest - for frequent updating pai response for every 2 hours)
- SharedPreferences
- Constraint Layout
- retrofit
- Coroutines
- Lottie animation on splash screen

## Future vision
- Can be improved with the UI design.
- Alert users on high raining, hotness and several scenarios based on weather details from the api.
- Option can be given to user to check the weather details, time of other cities/countries.
