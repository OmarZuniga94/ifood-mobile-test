# Tweet Feelings (ifood-mobile-test)
Create an app that given an Twitter username it will list user's tweets. When I tap one of the tweets the app will visualy indicate if it's a happy, neutral or sad tweet.

<p align="center">
  <img src="screen_1.png" width="250"/>
  <img src="screen_2.png" width="250"/>
  <img src="screen_3.png" width="250"/>
</p>


## How does it works?
* First you need to authenticate to Twitter API.
* Then search an user by the username.
* The App will display the user tweets.
* Click a Tweet to analyze it.

## Technical Description
* Modeling Architecture:  VIPER
* Android Components:  DataBinding / AndroidX / Kotlin / Material Design
* External API's:
    - Stetho:  Facebook API that uses Chrome Developer Tools to debug application.
    - Retrofit v2:  Networking API for Web Services.
    - Twitter Client:  Twitter API for connection to Twitter Core.
    - Google Natural Language:   Google API set up in GCP console.

## To Do
* Unit tests
