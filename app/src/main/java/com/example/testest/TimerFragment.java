package com.example.testest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment implements View.OnClickListener{
    View rootView;
    private long timeCountInMilliSeconds = 1 * 60000;
    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private ImageView imageViewReset;
    private ImageView imageViewStartStop;
    private CountDownTimer countDownTimer;
    private ProgressBar horizontalProgressBar;
    private EditText editNum;
    TextView numTextView;
    private androidx.appcompat.widget.AppCompatButton addButton;
    int num;
    int progress;
    int percent = 0;
    int status = 0;


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_timer, container, false);
        progressBarCircle =  rootView.findViewById(R.id.progressBarCircle);
        editTextMinute = rootView.findViewById(R.id.editTextMinute);
        textViewTime = rootView.findViewById(R.id.textViewTime);
        imageViewReset = rootView.findViewById(R.id.imageViewReset);
        imageViewStartStop = rootView.findViewById(R.id.imageViewStartStop);
        horizontalProgressBar = rootView.findViewById(R.id.horizontalProgressBar);
        editNum = rootView.findViewById(R.id.editNum);
        numTextView = rootView.findViewById(R.id.numTextView);
        //addButton = rootView.findViewById(R.id.numAddButton);

        imageViewReset.setOnClickListener(this);
        imageViewStartStop.setOnClickListener(this);
        //addButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewReset:
                reset();
                break;
            case R.id.imageViewStartStop:
                startStop();
                break;
            //case R.id.numAddButton:

        }
    }

    /**
     * 카운트 다운 시간을 리셋하고 재시작하는 기능
     */
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    /**
     * 타이머가 시작하고 멈추는 기능
     */
    private void startStop() {

        if (timerStatus == TimerStatus.STOPPED) {


            setTimerValues();
            setProgressBarValues();

            imageViewReset.setVisibility(View.VISIBLE);
            imageViewStartStop.setImageResource(R.drawable.stop);
            editTextMinute.setEnabled(false);

            timerStatus = TimerStatus.STARTED;

            startCountDownTimer();

            num = Integer.parseInt(editNum.getText().toString());
            progress = (int) Math.ceil(100/num);

            int nowValue = horizontalProgressBar.getProgress();
            int maxValue = horizontalProgressBar.getMax();

            if(maxValue == nowValue) {
                nowValue = 0;
            } else {
                horizontalProgressBar.setVisibility(View.VISIBLE);
                nowValue += progress;
                Log.d("progress", "progress");
                percent += progress;

            }

            if(nowValue == 0){
                //horizontalProgressBar.setVisibility(View.GONE);
            }


            if (status == 0) {
                horizontalProgressBar.setProgress(nowValue);
                numTextView.setText(Integer.toString(percent) + "%");
            }



        } else {

            imageViewReset.setVisibility(View.GONE);
            imageViewStartStop.setImageResource(R.drawable.play);
            editTextMinute.setEnabled(true);

            timerStatus = TimerStatus.STOPPED;
            status = 1;
            stopCountDownTimer();

        }

    }

    /**
     * 타이머에 시간이 설정 되어있는지 체크하크
     *  - 있는 경우 : 타이머에 시간 세팅
     *  - 없는 경우 : 시간을 설정해달라는 안내 토스트 메세지
     */
    private void setTimerValues() {
        int time = 0;
        if (!editTextMinute.getText().toString().isEmpty()) {

            time = Integer.parseInt(editTextMinute.getText().toString().trim());
        } else {

            Toast.makeText(getContext(), "시간을 설정해주세요", Toast.LENGTH_LONG).show();
        }

        timeCountInMilliSeconds = time * 60 * 1000;
    }

    /**
     * 카운트다운 시작 기능
     */
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));

                setProgressBarValues();

                imageViewReset.setVisibility(View.GONE);

                imageViewStartStop.setImageResource(R.drawable.play);

                editTextMinute.setEnabled(true);

                timerStatus = TimerStatus.STOPPED;
                status = 0;
            }

        }.start();
        countDownTimer.start();
    }

    /**
     *  카운트 다운 정지 및 초기화
     */
    private void stopCountDownTimer() {

        countDownTimer.cancel();
    }

    /**
     * 원형 프로그레스 바에 값 세팅
     */
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * 밀리언 초를 시간으로 포멧해주는 기능
     *
     * @param milliSeconds
     * @return HH:mm:ss 시간 포멧
     */
    private String hmsTimeFormatter(long milliSeconds) {

        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

    }
}