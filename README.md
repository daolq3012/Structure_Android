## Structure_Android
[![CircleCI](https://circleci.com/gh/daolq3012/Structure_Android/tree/master.svg?style=shield)](https://circleci.com/gh/daolq3012/Structure_Android/tree/master)
[![License: CC BY 4.0](https://img.shields.io/badge/License-CC%20BY%204.0-lightgrey.svg)](https://creativecommons.org/licenses/by/4.0/)

A Project common structure on Android used based on [googlesamples/android-architecture](https://github.com/googlesamples/android-architecture) :

- Create 3 architect **MVP, MVVM, MVVMP** and templates for each.
- This Project demo request API search user github and show result using [retrofit](https://github.com/square/retrofit) and save data to local using [Realm](https://github.com/realm/realm-java) And [SQLite](https://www.sqlite.org/)
- Using Reactive library: [RxJava](https://github.com/ReactiveX/RxJava),[RxAndroid](https://github.com/ReactiveX/RxAndroid)

### 1. MVP
Diagram:

![alt text](https://github.com/daolq3012/Structure_Android/blob/master/images/mvp.png?raw=true)

Source Code

**Branch:** [mvp-architecture](https://github.com/daolq3012/Structure_Android/tree/mvp-architecture)


### 2. MVVM
The same MVP structure in this **MVVM** structure _ViewModel_ as a _Presenter_ in **MVP**
Diagram:

![alt text](https://github.com/daolq3012/Structure_Android/blob/master/images/mvvm.png?raw=true)

Source Code:

**Branch:** [mvvm-architecture](https://github.com/daolq3012/Structure_Android/tree/mvvm-architecture)


### 3. MVVMP
MVVMP is a structure mixing between [MVP](https://github.com/daolq3012/Structure_Android/tree/mvp-architecture) and [MVVM](https://github.com/daolq3012/Structure_Android/tree/mvvm-architecture)
- Split _ViewModel_ to 2 components _ViewModel_ And _Presenter_
  * ViewModel binding data with view(layout), Logic processing not using in here
  * Presenter reponsible for logic and reflected up ViewModel

Diagram:

![alt text](https://github.com/daolq3012/Structure_Android/blob/master/images/mvvmp.png?raw=true)

Source Code:

**Branch:** [mvvmp-architecture](https://github.com/daolq3012/Structure_Android/tree/mvvmp-architecture)

  
### 4. MVVMP using Dagger 2
This architech use [Retrolambda](https://github.com/evant/gradle-retrolambda)

Diagram:

- The same with MVVMP structure

Source Code:

**Branch:** [mvvmp-dagger-architecture](https://github.com/daolq3012/Structure_Android/tree/mvvmp-dagger-architecture)


### Code style
- Download and import to Android studio [codestyle.jar](https://github.com/daolq3012/Structure_Android/blob/master/codestyle/codestyle.jar?raw=true)

## ☑ TODO

- [X] Add MVP Examples include Unit Test
- [X] Add MVVM Examples include Unit Test
- [X] Add MVVMP Examples include Unit Test
- [X] Add MVVMP-Dagger Examples include Unit Test
- [ ] Update documentation for each
- [ ] Add test UI using [Cucumber](https://cucumber.io/) and [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/setup/) (BDD technical)
- [ ] Add MVVMP-Dagger Examples include Unit Test using **Kotlin**

## 👬 Contribution

The Example are built using [Android studio](https://developer.android.com/studio/index.html)

- Open pull request with improvements
- Discuss ideas in issues
- Spread the word
- Reach out to me directly at dao.le.2511@gmail.com


## License

The content of this project itself is licensed under the Creative Commons Attribution 4.0 International (CC BY 4.0)