package net.ictcampus.minolettin.transcriptwriter;

import android.support.design.widget.FloatingActionButton;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

public class AudioActivityTest extends ActivityInstrumentationTestCase2<AudioActivity>{
    public AudioActivityTest() {
        super(AudioActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }


    @SmallTest
    public void test_fabSafe(){
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabSafe);
        assertNotNull(fab);
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
