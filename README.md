# TimeWizeApp_opsc

<h1>cloning the application</h1>
<li>get acess to our repository link</li></li>
<li>copy the link in our repository </li></li>
<li>Open your Android Studio then go to the File > New > Project from Version Control </li></li>
<li>Paste the link then click on the "Clone " button </li>

<h1>compilling the application </h1>

<li>the application runs on android studio</li>
<li>when the application launches, it lands  on the registration page </li>

![registrationPg](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/e0e7a546-d1f9-4001-a183-29d772ad3c7f)

<li>the user needs to register in order to acess the features of the application</li>
<li>the user needs to provide with their email and password, they also need to re-enter their password for security purposes</li>
<li>then click on the sign up button </li>
<li>the user's credentials are saved in the online database called firebase firestore</li>

![loginPg](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/baab4ec7-6ffc-4411-bc76-4aafad693b11)


<li>after the user is registered they can now log into the application using their email as well as password   </li>
<h1>Dashboard</h1

![dashloard](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/68caab07-7bad-46d9-aab4-fefca9922a9b)



<li>after the log in process, the screen will land to the dashboard</li>
<li>in the dash board we have a a button for adding categories and a button for adding timesheets </li>
<h1>categories</h1>

![category2](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/67594c3d-9070-4620-8854-ba8cae666bc5)



<li>when the user clicks on the categiries button they will be redirected to the page where they will provide with the catrgory name then they will submit the category name by clicking the "save a category"s button  </li>
<li>when the category is saved, the dashboard screen will open again allowing users to click on the timesheet button</li>
<h1>timesheet</h1>


![Timesheet55](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/b72b46b5-d32e-458b-b20a-66f6f17cf8a6)


<li>when the timesheet button is clicked, the timesheet page will open that will prompt the user to select the category that the timesheet will belong to,start time as well as end time, description of the task. they also have the ability to upload the picture of the timesheets. the user can save the timesheet  </li>
<li>all this information will be saved to firestore </li>

<li>in the dashboard there are three dots at the top right conner of the screen</li>
<li>the user needs to click there to see the menu</li>
<li>the user will then click on the hours activity</li>

<h1>capturing hours </h1>

![Hours](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/03f8ad0f-353d-4851-a90e-ad72dfd83227)



<li>in the hours activity, the user will be prompt to select the date, select the category that the hours will belong to</li>
<li>they will be prompt for minimum daily goals as well as maximum daily goals</li>
<li>they will prompt for the start date as well as the end date</li>
<li>when done providing with the information, they will click on the show hours button</li>
<li>the hours spent will be displayed</li>

<li>in the same activity there is a button for a graph "show graph", when that button is clicked , there graph showing the minimum, maximum daily goals and the total hours spent will be shown to show the progress </li>
<h1>graph</h1>

![graph](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/ffc591b6-a80b-4848-be71-f96e0828e1b7)



<h1>timesheet list </h1>
<li>in the menu, there is a button to show list of timesheets </li>
<li>when that button is clicked, the screen for timesheets will show, the user needs to provide wit hthe start date and the end date of the timesheets they want to dislay, select the category that the timesheet belongs to , then click on the view timesheet button, when that button is clicked, the list of timesheets will be shown along with the picture associated  with </li>

![list](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/e334c970-80cb-478c-8c46-627777dbccfe)



<h1>additional featuers </h1>
<h1>Promodoro Timer </h1>

![timer](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/77aba44d-38ce-4aae-b713-65a0f73be625)

<li>in the promodoro timer page, the user needs to provide with the time that they want to work for as well as the break time they would love to have then click on the start button </li>
<li>when the timer reaches 00:00:00 the break time  will automatically begin </li>

<h1>Task Rewards</h1>

![rewards](https://github.com/lilithaJVC/TimeWizeAppPOE/assets/104263642/4c1a6314-175a-4715-a9ef-3fba516ddf35)
<li>in the rewards page, this is where the user can earn badges when they complete a certain amount of tasks. When they complete each task thier progress bar will go up and once it reaches 100 they will earn the badge</li>
<li>Users can work to earn many badges at the same time</li>
