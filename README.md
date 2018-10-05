



### reelzdb App Overview

This app allows a user to search for a movie by title. The search action triggers a call to the OMDB database via its API.
If the search text yields results, the app will display matching titles and their Posters in a grid view. The user can then click on an image poster which opens a new view displaying more details about the selected movie.


### Design Considerations

In the design of the app, I followed the Model View Controller design pattern as much as possible to make the classes smaller and more modular. This should make it easier for other developers to assimilate and extend.

Used the OMDB JSON structure as a blueprint for the main object class (Movie). This should make it easy if in the future, these objects need to be stored in a local database (such as favorite movies), and can be readily be mapped using any of the ORMs available for Android.

As the app requires network calls to fetch data, this task is carried out by using AsyncTask so that it runs on child thread and not on the UI thread.
 
The list_item_movie layout file contains the main movie poster and some other data elements as a CardView. This CardView layout is in a separate layout file so it can be used in other layouts such as larger screens or tablets, or even inside layouts using Fragments.

When the user rotates the device to landscape, the results of the search are not lost, and will be kept on the screen but instead of displaying two images, four will be displayed per row -given the wider view. 


Additional Notes: 
Naming convention: Renamed the Retrofit Interface from MovieOMDBAPI to MovieAPI. If in the future, a different API is used, there will be no need to rename functions and variables based on the new movie db. The only reference to the word OMDB is in the URL.


### Third-party Libraries

##### Picasso: 
The app relies greatly on displaying images, especially the first screen where a grid of images is displayed. So there is a need for improving the loading and caching of images. Libraries like Picasso speed up this process.

##### Retrofit2 and OkHttp:
The app uses an external API which returns JSON objects. Having created functions that connect over the network via HTTP and needing to handle the JSON object by streaming and parsing it, using Retrofit2 with OkHttp provides a more framework for handling API requests.


### Unit Testing

Although I haven't used Espresso extensively and not with Retrofit, I believe that using IdlingResource to handle waiting for the non-main thread to complete its work. 
A quick search showed that there may be some issues with using Espresso to test apps that use Retrofit (more research need to be done).

Also, we can check/assert that a certain text is at the corresponding position on the Recycler View.


### Potential/future enhancement

If time is not a constraint, I would create a list of improvements whether they are code structure related or UI/UX related, then prioritize them based on their tangible impact to separate those that are just a nice-to-have.
A high impact change would be along the lines of improving performance, the user experience, or making the app display properly on different screen sizes or at least the ones that are going to be mostly used for a specific app.
I would also add reviewing the code and refactoring if more modularization and exception checking are needed. 
A nice-to-have could include animations such as screen/activity transitions, or giving the user the option of adding the app as a widget on the home screen (a plus for heavy users).

Material.io is a good resource to consult for ideas to make the UI more dynamic especially the UI Design Pattern continues to change and evolve.

Add a Progress Bar to show users that progress is being made, which also discourage from performing more clicks and slowing the app.

Use Butterknife library for layout binding instead of the usual findViewById reducing the code lines by half. In this app however, there aren't many fields but would still be a better option as far as planning ahead is concerned.

Create some level of persistence: Store the search history and display it as an autocomplete list for later access.

Use Fragments to accommodate for a tablet layout without duplicating code.

Include a fuzzy search whereby the title containing typos can still result in approximate results matching the search term. This would be a great addition for a highly functional app.

Add comments to the code (this one is usually at the top of my list) even if the app has extensive documentation.


### Resources

developer.android.com (hide keyboard)

Used the Loader pointers from this site: https://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
And https://medium.com/google-developers/making-loading-data-on-android-lifecycle-aware-897e12760832

https://github.com/JakeWharton/okhttp-idling-resource
This seems like a possible way to test Retrofit/OkHttp calls with IdlingResource.

Made use of some ideas from my projects that involved using Retrofit, adapters, background tasks, which also include some starter code.

stack overflow.com for general error messages







