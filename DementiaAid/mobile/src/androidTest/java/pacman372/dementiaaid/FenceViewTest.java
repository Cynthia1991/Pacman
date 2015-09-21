package pacman372.dementiaaid;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import pacman372.dementiaaid.SetFence.CircularFence;
import pacman372.dementiaaid.SetFence.FenceView;

/**
 * Created by fuqian on 8/09/2015.
 */
public class FenceViewTest extends TestCase {
    FenceView viewModel = new FenceView();
    CircularFence Testfence = new CircularFence();
    LatLng latLng = new LatLng(123.0000,123.0000);

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testCanChangeRadiusTrue() throws Exception {

        Testfence.setCenter(latLng);
        viewModel.setFence(Testfence);
        //if fence != null, and center != null,
        //return true;
        assertEquals(true, viewModel.canChangeRadius());

    }
    public void testCanChangeRadiusFalse() throws Exception {

        //Testfence.setCenter(latLng);
        viewModel.setFence(Testfence);
        //if fence != null, and center != null,
        //return true;
        assertEquals(false, viewModel.canChangeRadius());

    }

    public void testMapClicked() throws Exception {
        LatLng latLng = new LatLng(123.0000,123.0000);
        viewModel.mapClicked(latLng);
        assertEquals(viewModel.getFence().getCoordinateX(), latLng.latitude);
        assertEquals(viewModel.getFence().getCoordinateY(),latLng.longitude);

    }

    public void testRadiusChanged() throws Exception {
        Testfence.setCenter(latLng);
        viewModel.setFence(Testfence);
        viewModel.radiusChanged(13);
        assertEquals(viewModel.getFence().getRadius(),13);

    }

    public void testGetPerimeterOptions() throws Exception {
        /*LatLng TestLatLng = new LatLng(130.00,120.88);
        Testfence.setCenter(latLng);
        viewModel.setFence(Testfence);
        LatLng TestLatLng = viewModel.getCameraLocation();
        assertEquals(viewModel.fence.getRadius(),13);*/

    }

    public void testGetCameraLocation() throws Exception {

    }

    public void testGetCenterOptions() throws Exception {

    }

    public void testSetFence() throws Exception {

    }

}