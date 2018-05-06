package moon.the.on.junburg.com.retrofitpracticereddit;

import java.util.Map;

import moon.the.on.junburg.com.retrofitpracticereddit.model.Feed;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Junburg on 2018. 5. 6..
 */

public interface RedditApi {

    String BASE_URL = "https://www.reddit.com/";

    @Headers("Content-Type: application/json")
    @GET(".json")
    Call<Feed> getData();

    @POST("{user}")
    Call<ResponseBody> login(
            @HeaderMap Map<String, String> headers,
            @Path("user") String username,      // junburg
            @Query("user") String user,     // ?user=junburg
            @Query("passwd") String password,     // &passwd=berrynice428!
            @Query("api-type") String type          // &api-type=json
    );
}
