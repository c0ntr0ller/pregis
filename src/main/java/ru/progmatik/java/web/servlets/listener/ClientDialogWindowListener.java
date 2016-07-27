package ru.progmatik.java.web.servlets.listener;

import java.util.ArrayList;

/**
 * Класс, делает рассылку по всем клиентам.
 */
public class ClientDialogWindowListener {

    ArrayList<ClientDialogWindowObservable> observables = new ArrayList<>();

    public void addListener(ClientDialogWindowObservable observable) {
        observables.add(observable);
    }

    public void removeListener(ClientDialogWindowObservable observable) {
        observables.remove(observable);
    }

    public void removeAllListener() {
        observables.clear();
    }

    public void sendAnswer(Boolean answer) {
        if (answer) {
            observables.forEach(ClientDialogWindowObservable::go);
        } else {
            observables.forEach(ClientDialogWindowObservable::stop);
        }
    }
}
