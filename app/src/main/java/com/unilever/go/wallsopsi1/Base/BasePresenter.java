package com.unilever.go.wallsopsi1.Base;

public interface BasePresenter<V> {

    void attach(V baseView);

    void detach();


}
