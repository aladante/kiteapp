# kiteapp
This app will track the lat long of selected location and pass that to an api that will provide the wind power and directions.

Based on that information the app will send a notification to the user that the conditions to kitesurf are there.

Fly breezy folks



# START THE APPLICATION
make sure you have docker-compose installed on your computer

navigate to ./support_api and run docker-compose up
This will start a deamon that will figurate as the server which the application will talk to

When the app has started make sure to set the time to the current hour.
Else the application won't send a notification as the timeframe doesn't add up


 Change the IP address to your own IP adrress in the file
``` app/src/main/java/com/jandorresteijn/kiteapp/SyncService.kt```
It very important to change this as this will be the server which the application talks to.

# NOTE do not use localhost or 127.0.0.1 it need the actual IP adrress. localhost isn't allowed on android
# NOTE the app now does request to the server ```const val DEFAULT_SYNC_INTERVAL = (30 * 100).toLong()``` in production this will be set to 35 min to send 1 message in a hour



In the application there is button that will trigger the background service 
The start up intent doesn't registrate in a local setup this is why the trigger was implemented to show the service 


## MAP

https://github.com/osmdroid/osmdroid





