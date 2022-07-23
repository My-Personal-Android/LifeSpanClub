package coms.lifespanclub.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> stringMutableLiveData;

    public void init() {
        stringMutableLiveData = new MutableLiveData<>();

    }

    public void sendData(String msg) {
        stringMutableLiveData.setValue(msg);
    }

    public LiveData<String> getRpm() {
        return stringMutableLiveData;
    }

}
