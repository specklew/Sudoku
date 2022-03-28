package pl.cp.sudoku;

public abstract class Observable {

    private final Observer observer;

    public Observable(Observer observer) {
        this.observer = observer;
    }

    public void notifyObservers() {
        this.observer.update();
    }
}
