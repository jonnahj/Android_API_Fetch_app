# Android_API_Fetch
The android app uses a jetpack compose ui and livemodels to update the ui with data obtained over API call. The project has a thorough execution of separation of concerns. 

The MainActivity initialises the viewmodel which makes the API call. Once the data is fetched the livedata is updated triggering the UI change. 

IF THE API CALL IS SUCCESSFULL
The sprinner is replaced by the table representing the names and ids with the ListID being the sticky header. The scroll is user-friendly and the sticky headers outline a clear grouping. 

If the request is re-directed the data is fetches from the re-directed site. The network calls and operations are abstracted for the user. 

IF THE API CALL FAILS
The spinner is shown enlessly. 

ps: I've used the below resouces for reference and debugging guidance. Listing them in the order of usage:
https://developer.android.com/
https://chatgpt.com/
https://stackoverflow.com/questions
