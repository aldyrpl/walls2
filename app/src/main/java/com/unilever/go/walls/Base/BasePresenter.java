package com.unilever.go.walls.Base;

public interface BasePresenter<V> {

    void attach(V baseView);

    void detach();


}
