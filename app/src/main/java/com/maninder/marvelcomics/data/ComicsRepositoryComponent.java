package com.maninder.marvelcomics.data;

import com.maninder.marvelcomics.ApplicationModule;
import com.maninder.marvelcomics.ComicsRepositoryModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This is a Dagger component. Refer to {@link com.maninder.marvelcomics.ApplicationModule} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * com.maninder.marvelcomics.ApplicationModule}.
 */
@Singleton
@Component(modules = {ComicsRepositoryModel.class, ApplicationModule.class})
public interface ComicsRepositoryComponent {

    ComicsRepository getComicsRepository();

}
