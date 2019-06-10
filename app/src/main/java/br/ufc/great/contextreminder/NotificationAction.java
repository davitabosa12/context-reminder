package br.ufc.great.contextreminder;

import
        android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;

import smd.ufc.br.easycontext.fence.FenceAction;

public class NotificationAction implements FenceAction {
    private  static  String TAG = "NotificationAction";
    @Override
    public void doOperation(Context context, FenceState state, Bundle data) {
        if(state.getCurrentState() == FenceState.TRUE){
            String s = data.getString("text");
            Log.d(TAG, "doOperation: " + s);
        } else {
            String k = state.getFenceKey();
            Log.d(TAG, "doOperation: " + k + " is false or unknown");
        }

    }
}
