package pacman372.dementiaaid.ServerAidClasses;

import java.util.List;

import pacman372.dementiaaid.EntityClasses.PolygonalFence;
import pacman372.dementiaaid.SetFence.CircularFence;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Ramona on 11/10/2015.
 */
public interface IDementiaAidService {
    @GET("api/Fences?$filter=carer_id eq {carerId}")
    Call<List<PolygonalFence>> getFences(@Path("carerId") int carerId);

    @POST("api/Fences")
    Call<PolygonalFence> addFence(@Body PolygonalFence fence);


    @POST("api/Fence")
    Call<CircularFence> addFence(@Body CircularFence fence);
}
