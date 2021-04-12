Marvel App
======================

The app displays a list of characters by consuming the marvel api. On clicking any
list item it opens the details screen. The list view has a Search Bar. 
This Search Bar is used for searching the comics by character name
In the menu you can sort by name ascending or descending from the list of characters

![Apr-07-2021 9-27-53 AM](https://user-images.githubusercontent.com/8774947/113883200-91597100-9783-11eb-8e23-7f6059ba1f9a.gif)

![Apr-07-2021 9-31-11 AM](https://user-images.githubusercontent.com/8774947/113883719-07f66e80-9784-11eb-9ba8-6062c7a4943b.gif)


<img width="1241" alt="Screen Shot 2021-04-12 at 5 11 21 PM" src="https://user-images.githubusercontent.com/8774947/114469155-3922d480-9bb2-11eb-8ac1-284b01087e1f.png">


The favorites screen shows the characters only marked as favorites, when you click on the heart image, it is removed from the favorites

## The project features:
- [x] Kotlin
- [x] Dagger Hilt
- [x] Coroutines
- [x] Picasso
- [x] Navigation
- [x] DataBinding
- [x] ViewModel
- [x] LiveData
- [x] PagedList2
- [x] Room
- [x] Retrofit2
- [x] OkHttp
- [x] JUnit4
- [x] Mockito

## Modules:
1. data : It is an implementation of the repository, datasource(local, remote), mappers and api services.
2. domain : Contains the usecases. It has the unit tests covering all the use cases)
2. shared : Contains method extensions, constants, base classes
