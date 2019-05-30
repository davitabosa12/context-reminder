package smd.ufc.br.easycontext.fence;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.awareness.fence.FenceState;

public interface FenceAction {

	public void doOperation(Context context, FenceState state, Bundle data);

}
