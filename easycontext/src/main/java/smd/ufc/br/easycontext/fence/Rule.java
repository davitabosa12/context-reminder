package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

interface Rule {
    abstract AwarenessFence getAwarenessFence();
}
