package com.nikola3in1.audiobooks.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class PlayerResultReciever extends ResultReceiver {
    private Receiver mReceiver;

    public PlayerResultReciever(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
