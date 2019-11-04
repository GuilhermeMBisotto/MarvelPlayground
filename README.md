# Marvel Playground

[![Build](https://action-badges.now.sh/arildojr7/events-sample-android)]()
[![Kotlin](https://img.shields.io/badge/kotlin-powered-blue.svg)]()
[![Koin](https://img.shields.io/badge/koin-2.0.1-orange.svg)]()
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)]()

## Introduction
**Marvel Playground** is an application that consumes Marvel API and where I can play with new android libraries and implementations.


## Architecture
The app uses **MVVM** as its architecture, as Google's own recommendation.

![dsd](images/architecture.png)

## Frameworks
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Koin](https://github.com/InsertKoinIO/koin)
- [Coil](https://github.com/coil-kt/coil)
- [Lottie](https://github.com/airbnb/lottie-android)
- [Retrofit](https://github.com/square/retrofit)
- [Mockito](https://site.mockito.org/)
- [AndroidX](https://developer.android.com/jetpack/androidx?authuser=1)
- [Paging](https://developer.android.com/reference/android/arch/paging/package-summary)

## Preview
![](images/preview.gif)
> **Note:** As it is a GIF, there is a limitation on the amount of colors ...

## IMPORTANT
> **Note** To everything works as wanted, you need to change Marvel API key in keys.gradle file.

## Code Coverage

| Module | Class % | Method % | Line % |
|------ | --------- | ------------ | ------------|
| Data | 20% (9/45) | 33% (47/140) | 14% (47/333) |
| Core | 2% (1/45) | 0% (1/101) | 0% (1/433) |
| UI is just resources |
| App UI package | 18% (11/61) | 18% (20/102) | 13% (52/396) |

## Future Features
> There are several features I would like to implement, and these are some of them.

- Make paging works with Room
- Add Robolectric to the project, to help with tests
- Improve internet validation
- Add WorkManager to deal with Room when save things
- Make repository classes work with coroutines flow, to make app more reactive
- Work with coroutines parallelism when possible
