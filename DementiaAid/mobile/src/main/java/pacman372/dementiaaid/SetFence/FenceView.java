package pacman372.dementiaaid.SetFence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import pacman372.dementiaaid.EntityClasses.FencePoint;
import pacman372.dementiaaid.EntityClasses.PolygonalFence;
import pacman372.dementiaaid.R;
import pacman372.dementiaaid.ServerAidClasses.IDementiaAidService;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FenceView{
    boolean centerDefined = false;
    protected CircularFence fence;
    protected PolygonalFence pFence;
    public static final LatLng DEFAULT_CAMERA = new LatLng(-27.4667, 153.0333);
    private final static String baseURL="http://pacmandementiaaid.azurewebsites.net/";
    int carerID = 8; //TODO: Get from wherever this is stored after login
    int patientID = 1; //TODO: Get from wherever this is stored after login

    private Activity caller;
    //public static Context contextOfApplication;

    public FenceView(AppCompatActivity caller){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .build();
        service = retrofit.create(IDementiaAidService.class);
        mode = MODE.Circular;
        this.caller = caller;

        //contextOfApplication = getApplicationContext();
    }
    public FenceView(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .build();
        service = retrofit.create(IDementiaAidService.class);
        mode = MODE.Circular;
        //this.caller = caller;

        //contextOfApplication = getApplicationContext();
    }

    //Context applicationContext = MyActivityClass.getContextOfApplication();
    private IDementiaAidService service;
    public enum MODE {
        Circular,
        Polygonal
    }
    public boolean canChangeRadius() {
        return !isCenterUndefined();
    }
    MODE mode;
    public void SetMode(MODE mode) {
        this.mode = mode;
    }

    private boolean isCenterUndefined() {
        return fence == null && !centerDefined;
    }


    public void onPause(){
            switch (mode) {

                case Circular:

                    CircularFence circularFence = new CircularFence();
                    circularFence.radius = fence.radius;
                    circularFence.latitude = fence.latitude;
                    circularFence.longitude = fence.longitude;
                    //Context context;
                    String string = "pacman372.dementiaaid.userDetails";
                    SharedPreferences userDetails = caller.getSharedPreferences(string, 0);
                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putString("FenceType","CircularFence");
                    Gson gson = new Gson();
                    String circularFenceJson = gson.toJson(circularFence); // myObject - instance of MyObject
                    editor.putString("Fence",circularFenceJson);
                    //editor.putString("CircularFence",String.valueOf(carer.getID()));

                    editor.commit();

                    service.addFence(fence).enqueue(new Callback<CircularFence>() {

                        @Override
                        public void onResponse(Response<CircularFence> response, Retrofit retrofit) {


                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    break;
                case Polygonal:
                    //PolygonalFence polygonalFence = new PolygonalFence();
                    String pString = "pacman372.dementiaaid.userDetails";
                    SharedPreferences pUserDetails = caller.getSharedPreferences(pString, 0);
                    SharedPreferences.Editor pEditor = pUserDetails.edit();
                    pEditor.putString("FenceType","PolygonalFence");
                    Gson pGson = new Gson();
                    String PolygonalFenceJson = pGson.toJson(pFence); // myObject - instance of MyObject
                    pEditor.putString("Fence",PolygonalFenceJson);
                    //editor.putString("CircularFence",String.valueOf(carer.getID()));

                    pEditor.commit();


                    service.addFence(pFence).enqueue(new Callback<PolygonalFence>() {

                        @Override
                        public void onResponse(Response<PolygonalFence> response, Retrofit retrofit) {

                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    break;
            }
    }
    public int getRadius(){
        if (fence != null) {
            return fence.getRadius();
        }
        else return 10;
    }
    public void mapClicked(LatLng latLng) {
        switch (mode){
            case Circular:

                if (fence == null) {
                    fence = new CircularFence(DEFAULT_CAMERA, 10);
                    fence.carerID = carerID;
                    fence.patientID = patientID;
                }

                fence.setCenter(latLng);
                break;
            case Polygonal:
                if (pFence == null) {
                    pFence = new PolygonalFence();
                    pFence.carerID = carerID;
                    pFence.patientID = patientID;
                }
                pFence.getPoints().add(new FencePoint(latLng));
                break;
        }
    }

    public void radiusChanged(int radius) {
        if (fence != null) {
            fence.setRadius(radius);
        }
    }

    public void configureMap(GoogleMap map) {
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-27.4667, 153.0333)));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        switch (mode){
            case Circular:
                MarkerOptions markerOptions = getCenterOptions();
                if (markerOptions != null) {
                    map.addMarker(markerOptions);
                }

                CircleOptions circleOptions = getPerimeterOptions();
                if (circleOptions != null) {
                    map.addCircle(circleOptions);
                }

                LatLng cameraLocation = getCameraLocation();
                if (!map.getProjection().getVisibleRegion().latLngBounds.contains(cameraLocation)) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(cameraLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
                break;
            case Polygonal:
                if (pFence != null && pFence.getPoints().size() > 0) {

                    List<FencePoint> fencePoints = pFence.getPoints();
                    PolygonOptions po = new PolygonOptions().strokeWidth(3).strokeColor(Color.RED);
                    for (FencePoint point : fencePoints) {
                        LatLng ll = new LatLng(point.latitude, point.longitude);
                        map.addMarker(new MarkerOptions().position(ll));
                        po.add(ll);
                    }
                    map.addPolygon(po);
                }
                break;
        }
    }
    private CircleOptions getPerimeterOptions(){
        if (isCenterUndefined() || fence.radius <= 0) {
            return null;
        }

        return new CircleOptions().strokeColor(Color.RED).strokeWidth(2).radius(fence.radius).center(fence.getCenter());
    }

    private LatLng getCameraLocation() {
        if (isCenterUndefined()) {
            return DEFAULT_CAMERA;
        }

        return fence.getCenter();
    }

    private MarkerOptions getCenterOptions(){
        if (isCenterUndefined())
        {
            return null;
        }

        return new MarkerOptions().position(fence.getCenter());
    }
}
