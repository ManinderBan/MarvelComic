package com.maninder.marvelcomics;

/**
 * Created by Maninder on 14/11/16.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
