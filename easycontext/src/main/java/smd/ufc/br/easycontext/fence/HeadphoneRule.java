package smd.ufc.br.easycontext.fence;

import com.google.android.gms.awareness.fence.AwarenessFence;

import smd.ufc.br.easycontext.fence.method.HeadphoneMethod;

import static smd.ufc.br.easycontext.fence.method.HeadphoneMethod.HEADPHONE_UNPLUGGING;

/**
 * Created by davitabosa on 13/08/2018.
 */

public class HeadphoneRule implements Rule {

    private HeadphoneMethod method;
    private int headphoneState;


    HeadphoneRule() {

    }

    public HeadphoneRule pluggingIn(){
        HeadphoneRule headphoneFence = new HeadphoneRule();
        headphoneFence.method = HeadphoneMethod.HEADPHONE_PLUGGING_IN;

        return headphoneFence;
    }

    public HeadphoneRule unplugging(){
        HeadphoneRule headphoneFence = new HeadphoneRule();
        headphoneFence.method = HEADPHONE_UNPLUGGING;

        return headphoneFence;

    }
    public HeadphoneRule during(int headphoneState){
        HeadphoneRule headphoneFence = new HeadphoneRule();
        headphoneFence.method = HeadphoneMethod.HEADPHONE_DURING;
        headphoneFence.headphoneState = headphoneState;
        return headphoneFence;
    }


    @Override
    public AwarenessFence getAwarenessFence() {
        switch(method){
            case HEADPHONE_PLUGGING_IN:
                return com.google.android.gms.awareness.fence.HeadphoneFence.pluggingIn();

            case HEADPHONE_UNPLUGGING:
                return com.google.android.gms.awareness.fence.HeadphoneFence.unplugging();

            case HEADPHONE_DURING:
                return com.google.android.gms.awareness.fence.HeadphoneFence.during(headphoneState);
            default:
                return null;
        }
    }

}
