# MVI-Clean-Architecture
This is a sample app & basic code that demonstrate how to build an Android application using the Uncle Bob's Clean Architecture approach.

The trick of the project is to demonstrate best practices, provide a set of guidelines, and present modern Android Application Architecture that is modular, scalable, maintainable and testable, suitable for bigger teams and long application lifecycle management.

<img src="https://miro.medium.com/max/4800/1*D1EvAeK74Gry46JMZM4oOQ.png" width="500">

Because it is an architectural project, UI has been kept simple. Sample data has been provided from [Weather API](https://api.openweathermap.org/data/2.5/) 

### Flow 
This app uses [_**MVI (Model View Intent)**_](https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d) architecture.
 
<img src="https://miro.medium.com/max/1400/1*3u5JnmqONR4UnwRE6tEV3Q.png" width="500">

### Modules
Modules are the collection of source files and build settings that allow you to divide your project into discrete units of functionality.

- **App Module**

  `:app` module is an [com.android.application](https://developer.android.com/studio/projects/android-library), which is needed to create the app bundle. It contains dependency graph and UI related classes. It presents data to screen and handle user interactions.

- **Base Module**

  `:base` module contains only framework related base classes that is used in other modules

- **Common Module**

  `:common` module contains code and resources which are shared between other modules

- **Data Module**

  `:data` module contains implementation of repository and local - remote repository interface adapt
  
- **Domain Module**

  `:domain` module contains use cases and repository interface adapt
  
- **Local Module**

  `:local` module contains local data source related classes
  
 - **Remote Module**
 
	`:remote` module contains remote data source related classes
	  
 - **Presentation Module**
 
	  `:presentation` module contains business logic

Each module has its own test.

### Tech Stack
- [Kotlin](https://kotlinlang.org)
- [Jetpack](https://developer.android.com/jetpack)
	* [Android KTX](https://developer.android.com/kotlin/ktx)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [Data Binding](https://developer.android.com/topic/libraries/data-binding)
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
    *  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
    * [Room](https://developer.android.com/training/data-storage/room)
- [Coroutines - Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html)
  - [State Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
  -   [Shared Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
  -  [Channels](https://kotlinlang.org/docs/channels.html#channel-basics)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [KotlinX](https://github.com/Kotlin/kotlinx.serialization)
- [KotlinX Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Testing](https://developer.android.com/training/testing/fundamentals)
    *  [MockK](https://mockk.io/)
    * [Junit4](https://junit.org/junit4/)
    * [Truth](https://github.com/google/truth)
    * [Turbine](https://github.com/cashapp/turbine)
    * [Fragment Testing](https://developer.android.com/guide/fragments/test)
    * [Navigation Testing](https://developer.android.com/guide/navigation/navigation-testing)
    * [Coroutine Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test)
    * [Barista](https://github.com/AdevintaSpain/Barista)
    * [Dagger Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing)

### Contributions