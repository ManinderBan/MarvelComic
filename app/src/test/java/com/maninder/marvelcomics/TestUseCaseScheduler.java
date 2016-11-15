package com.maninder.marvelcomics;


import com.maninder.marvelcomics.threading.UseCase;
import com.maninder.marvelcomics.threading.UseCaseScheduler;
/**
 * Created by Maninder on 15/11/16.
 */

/**
 * A scheduler that executes synchronously, for testing purposes.
 */
public class TestUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <R extends UseCase.ResponseValue> void notifyResponse(R response,
                                                                 UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }

    @Override
    public <R extends UseCase.ResponseValue> void onError(
            UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseCallback.onError();
    }
}