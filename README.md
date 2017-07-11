## Structure_Android
[![CircleCI](https://circleci.com/gh/daolq3012/Structure_Android/tree/mvvmp-architecture.svg?style=shield)](https://circleci.com/gh/daolq3012/Structure_Android/tree/mvvmp-architecture)
[![License: CC BY 4.0](https://img.shields.io/badge/License-CC%20BY%204.0-lightgrey.svg)](https://creativecommons.org/licenses/by/4.0/)

### MVP-VM
Split ViewModel to 2 components ViewModel & Presenter:
- ViewModel binding data with view(layout), logic processing not using in here.
- Presenter responsible for logic and reflected on ViewModel.

### Diagram
![alt text](https://github.com/daolq3012/Structure_Android/blob/master/images/mvvmp.png?raw=true)

### Templates
Template MVP-VM Activity & MVP-VM Fragment: [MVP-VM_templates.rar](https://github.com/daolq3012/Structure_Android/blob/mvvmp-architecture/templates/MVVMP_templates.rar?raw=true)

Extract this file and copy into
**..\Android Studio\plugins\android\lib\templates**
to create MVVMP Activity or Fragment easier like photo below:

![template](https://raw.githubusercontent.com/daolq3012/Structure_Android/mvvmp-architecture/templates/Templates.png)
