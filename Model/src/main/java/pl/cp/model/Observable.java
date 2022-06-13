package pl.cp.model;

import java.io.Serializable;

public abstract class Observable implements Serializable {

    private final Observer observer;

    public Observable(Observer observer) {
        this.observer = observer;
    }

    public void notifyObservers() {
        this.observer.update();
    }
}
