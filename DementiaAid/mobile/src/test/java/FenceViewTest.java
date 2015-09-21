/**
 * Created by Ramona on 6/09/2015.
 */
import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import pacman372.dementiaaid.SetFence.FenceView;

public class FenceViewTest {
    FenceView viewModel;

    @Before
    public void setUp(){
        viewModel = new FenceView();
    }

    @Test
    public void cantChangeRadiusUntilMapClicked() {
        assertFalse(viewModel.canChangeRadius());
        viewModel.mapClicked(new LatLng(-27, 158));
        assertTrue(viewModel.canChangeRadius());
    }

    @Test
    public void perimeterIsUndefinedUntilMapClicked() {
        assertNull(viewModel.getPerimeterOptions());
        viewModel.mapClicked(new LatLng(-20, 100));
        assertNotNull(viewModel.getPerimeterOptions());
    }

    @Test
    public void cameraLocationFollowsMapClick(){
        assertEquals(FenceView.DEFAULT_CAMERA, viewModel.getCameraLocation());
        LatLng somewhereElse = new LatLng(-14,145);
        viewModel.mapClicked(somewhereElse);
        assertEquals(somewhereElse, viewModel.getCameraLocation());
    }


}
