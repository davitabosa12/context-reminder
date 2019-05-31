package smd.ufc.br.easycontext;

import org.junit.Test;

import smd.ufc.br.easycontext.fence.HeadphoneRule;

public class HeadphoneFenceTest {
    @Test(expected = Exception.class)
    public void testBuildNull(){
        HeadphoneRule.Builder builder = new HeadphoneRule.Builder();
        builder.build();
    }
}
