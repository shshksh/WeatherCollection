package com.mob.weathercollection;

import com.mob.weathercollection.model.Todo;
import com.mob.weathercollection.model.Weather;
import com.mob.weathercollection.util.KmaService;
import com.mob.weathercollection.util.RetrofitImpl;
import com.mob.weathercollection.util.TestService;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void kma_request_test() throws IOException, InterruptedException {
        KmaService kmaService = RetrofitImpl.getKmaService();

        Call<Weather> weather = kmaService.getWeather("2647070000");
        weather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                System.out.println("yes");
                if (response.isSuccessful()) {
                    System.out.println(response.message());
                    System.out.println(response);
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                System.out.println("no " + t.getMessage());
            }
        });
        Thread.sleep(5000);
    }

    @Test
    public void json_convert_test() throws InterruptedException {
        TestService testService = RetrofitImpl.getTestService();

        Call<Todo> todo = testService.getTodo();
        todo.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        Thread.sleep(5000);
    }
}