# MarvelComics

This Application used MVP + Clean Architecture + Dagger 2.
This application show a list of the first 100 comics from the Marvel API.
Contains this features:
1. Display the list of the first 100 comics.
2. Display the Comics information.
3. Given a budget, the application show the maximum number of that you can buy without exceeding that budget.

#Organzation
The project is organized by application features.
- comicsdetail: contain all the class related to Comics detail;
- comicslist: contain classes the allow to display the list of Comics;
- data: subpackage for Remote and Local repository

#Dependency

- Dagger 2 (Dependency Injection)
- Retrofit 2
- ButterKnife
- RxJava
- Picasso (Image Loader)
- Mockito (Unit Test)

Maninder Ban