package com.example.viewmodellivedatademoenrichi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModal extends ViewModel {

    private final MutableLiveData<Integer> countLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> historyLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> statusLiveData = new MutableLiveData<>();

    private int stepSize = 1;

    public CounterViewModal() {
        countLiveData.setValue(0);
        historyLiveData.setValue("Historique : --");
        statusLiveData.setValue("Neutre");
    }

    public void increment() {
        Integer current = countLiveData.getValue();
        if (current != null) {
            int newVal = current + stepSize;
            countLiveData.setValue(newVal);
            historyLiveData.setValue("+" + stepSize + "  ->  " + newVal);
            updateStatus(newVal);
        }
    }

    public void decrement() {
        Integer current = countLiveData.getValue();
        if (current != null) {
            int newVal = current - stepSize;
            countLiveData.setValue(newVal);
            historyLiveData.setValue("-" + stepSize + "  ->  " + newVal);
            updateStatus(newVal);
        }
    }

    public void reset() {
        countLiveData.setValue(0);
        historyLiveData.setValue("Remis a zero");
        statusLiveData.setValue("Neutre");
    }

    public void setStep(int step) {
        this.stepSize = step;
    }

    private void updateStatus(int val) {
        if (val > 10)       statusLiveData.setValue("Tres eleve");
        else if (val > 0)   statusLiveData.setValue("Positif");
        else if (val == 0)  statusLiveData.setValue("Neutre");
        else if (val > -10) statusLiveData.setValue("Negatif");
        else                statusLiveData.setValue("Tres bas");
    }

    public void incrementFromBackground() {
        new Thread(() -> {
            Integer current = countLiveData.getValue();
            if (current != null) {
                countLiveData.postValue(current + stepSize);
            }
        }).start();
    }

    public LiveData<Integer> getCount()   { return countLiveData; }
    public LiveData<String> getHistory()  { return historyLiveData; }
    public LiveData<String> getStatus()   { return statusLiveData; }
}