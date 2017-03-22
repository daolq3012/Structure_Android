# Structure_Android
- Base on [googlesamples/android-architecture](https://github.com/googlesamples/android-architecture)
create 3 architect **MVP, MVVM, MVVMP** and templates for each kind.
- This Project Demo call API search user github and show result using [retrofit](https://github.com/square/retrofit) and save data to local using [Realm](https://github.com/realm/realm-java) & SQLite

## MVP
https://github.com/daolq3012/Structure_Android/tree/mvp-architecture
## MVP using Dagger 2
https://github.com/daolq3012/Structure_Android/tree/mvp-dagger-architecture
## MVVM
https://github.com/daolq3012/Structure_Android/tree/mvvm-architecture
## MVVMP
- Mixing [MVP](https://github.com/daolq3012/Structure_Android/tree/mvp-architecture) & [MVVM](https://github.com/daolq3012/Structure_Android/tree/mvvm-architecture)

- Split ViewModel to 2 components ViewModel & Presenter
  * ViewModel binding data with view(layout), Logic processing not using in here
  * Presenter reponsible for logic and reflected up ViewModel
  
https://github.com/daolq3012/Structure_Android/tree/mvvmp-architecture
  
## MVVMP using Dagger 2
https://github.com/daolq3012/Structure_Android/tree/mvvmp-dagger-architecture
