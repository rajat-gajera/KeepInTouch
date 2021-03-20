package com.example.keepintouch.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAxfUQTqo:APA91bF5w3CLVh4IE6Rkert2TEYmQtG6jENdRW0MAhfcKxvfXLx9Ay4z5lBwRmgtXMKklcxRSydeoQ2nBnatsN6gB8WTfDnglrTJSkQfWYMmoWh4r_YtGI-AoJ6HXYnH6ERUzi90b8sD"
            }
    )

    @POST("fcm/send")
    Call<MyNotification> sendNotification(@Body NotificationSender body);

}
