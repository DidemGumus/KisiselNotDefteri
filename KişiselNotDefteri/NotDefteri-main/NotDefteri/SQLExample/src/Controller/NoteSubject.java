package Controller;

import java.util.ArrayList;
import java.util.List;

public class NoteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }

    }
    public void notifyObservers(String message, int targetUserId) {
        for (Observer observer : observers) {
            if (observer instanceof UserObserver) {
                UserObserver userObserver = (UserObserver) observer;
                if (userObserver.getUserId() == targetUserId) {
                    userObserver.update(message);
                }
            }
        }
    }

}
