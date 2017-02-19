# Nerdery Android NAT
This is the Base Project for The Nerdery's Android NAT.
It provides a number of key pieces to help you get started on the NAT.

Nothing that we provide should be interpreted as a mandatory component; if you want to remove, modify,
or replace any of the functionality we have provided with your own implementation, you are free to do so.

# Android verions
* App should target the latest SDK
* App must support API versions 19 and up.

# APIs
The api package contains an ApiManager, which provides access to two API classes:
 * WeatherApi: This is the Weather Underground API. The only implemented endpoint is /conditions/hourly.
 * IconApi: This API is used to provide better icons than Weather Underground provides.

# Resources
We have set up a few resources for you already.
All of the icons you need should already be in the appropriate drawable folders.
The colors used to indicate that the current temperature is "warm" or "cool" are provided in colors.xml.
Take special note of metrics.md, which defines a number of metrics for styling the application.

# Design notes
The header of the application should be static and *not* collapse when scrolling. One question that could be asked in a second interview is how might you go about making the header collapsable.
We have a standard at the Nerdery that if something changes, it should animate it. However, for the NAT we recognize that time constraints really don't allow for much in the way of animations. If you see a way to use a freebie animation that is easily implemented in a couple lines or less, feel free to include it in the project.
The status bar isn't up to modern material designs standards. Please ignore the designs in that aspect and use the system defaults.