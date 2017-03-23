# Structure_Android
Mixing [MVP](https://github.com/daolq3012/Structure_Android/tree/mvp-architecture) & [MVVM](https://github.com/daolq3012/Structure_Android/tree/mvvm-architecture)

Split ViewModel to 2 components ViewModel & Presenter
- ViewModel binding data with view(layout), Logic processing not using in here
- Presenter reponsible for logic and reflected on ViewModel

# Diagram
TBD

# Templates
Template MVVMP Activity & MVVMP Fragment: [MVVMP_templates.rar](https://github.com/daolq3012/Structure_Android/blob/mvvmp-dagger-architecture/templates/MVVMP_Dagger_templates.zip?raw=true)

Extract this file and copy into
**..\Android Studio\plugins\android\lib\templates**
to create MVVMP Activity or Fragment easier like photo below:

![template](https://raw.githubusercontent.com/daolq3012/Structure_Android/mvvmp-architecture/templates/Templates.png)
