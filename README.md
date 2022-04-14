# AndroidSample

Android application using the latest version of [Android Jetpack](https://developer.android.com/jetpack) libraries

## Architecture Overview

This application architecture was built taking into consideration this [guide](https://developer.android.com/jetpack/guide) suggested by Google. The main two packages in the
project ara core & ui. The package core represents the Data Layer while the package ui represents the UI Layer.

In the Data Layer, Repositories were developed to act as a single source of truth, that is, the data from repositories is always retrieved from the local database
managed by room and it exposed methods to synchronize data with the REST API service.

## Libraries

The Data Layer used the following libraries:

* [Room](https://developer.android.com/jetpack/androidx/releases/room): SQLite ORM
* [Volley](https://google.github.io/volley/): Networking library
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Dependency Injection library
* [Gson](https://github.com/google/gson): JSON serialization/deserialization 

The UI Layer used the following libraries:

* [Navigation](https://developer.android.com/guide/navigation): Navigation graphs.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): State holders & manage ui data
