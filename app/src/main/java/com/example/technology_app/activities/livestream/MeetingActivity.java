package com.example.technology_app.activities.livestream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.technology_app.R;
import com.example.technology_app.activities.chat.ChatActivity;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.adapters.CartAdapter;
import com.example.technology_app.fragment.ViewerFragment;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;
    String userId, accessToken;
    private Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meeting);
        Paper.init(this);
        userId = Paper.book().read("userId");
        accessToken = Paper.book().read("accessToken");
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        getLive();
    }

    private void getLive() {
        compositeDisposable.add(api.getLive(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getLiveModel -> {
                            if (getLiveModel.getStatus() == 200) {
                                String meetingId = getLiveModel.getMetadata().getMeetings().get(0).getId();
                                String token = getLiveModel.getMetadata().getMeetings().get(0).getToken();
                                String mode = "VIEWER";
                                String localParticipantName = "TechnologyApp";
                                boolean streamEnable = mode.equals("CONFERENCE");

                                // initialize VideoSDK
                                VideoSDK.initialize(getApplicationContext());

                                // Configuration VideoSDK with Token
                                VideoSDK.config(token);

                                // Initialize VideoSDK Meeting
                                meeting = VideoSDK.initMeeting(
                                        MeetingActivity.this, meetingId, localParticipantName,
                                        streamEnable, streamEnable, null, mode, false, null);

                                // join Meeting
                                meeting.join();

                                meeting.addEventListener(new MeetingEventListener() {
                                    @Override
                                    public void onMeetingJoined() {
                                        if (meeting != null) {
                                             if (mode.equals("VIEWER")) {
                                                getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.mainLayout, new ViewerFragment(), "viewerFragment")
                                                        .commit();
                                            }
                                        }
                                    }
                                });
                            } else {
                                Log.d("Fail", "get cart Fail");
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                )
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MeetingActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish ChatActivity
    }

    public Meeting getMeeting() {
        return meeting;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}