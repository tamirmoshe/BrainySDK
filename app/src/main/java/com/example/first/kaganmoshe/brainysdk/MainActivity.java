package com.example.first.kaganmoshe.brainysdk;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import EEG.EConnectionState;
import EEG.ESignalVolume;
import EEG.EegHeadSet;
import EEG.IHeadSetData;
import EEG.MindWave;

public class MainActivity extends Activity implements IHeadSetData{

    private ImageView mBallIV;
    private ImageView mConnectionIV;
    private Button mConnectBtn;

    private EegHeadSet mHeadSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadSet = new MindWave();
        mHeadSet.registerListener(this);

        mConnectBtn = (Button) findViewById(R.id.connectbtn);
        mBallIV = (ImageView) findViewById(R.id.ballImageView);
        mConnectionIV = (ImageView) findViewById(R.id.connectionImageView);

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeadSet.connect();
            }
        });
    }

    @Override
    public void onAttentionReceived(int attValue) {
        int size = (int)(attValue * 3.5 + 300);

        mBallIV.getLayoutParams().height = size;
        mBallIV.getLayoutParams().width = size;
        mBallIV.requestLayout();
    }

    @Override
    public void onHeadSetChangedState(String headSetName, EConnectionState connectionState) {
        switch (connectionState){
            case IDLE:
            case DEVICE_NOT_CONNECTED:
            case DEVICE_NOT_FOUND:
                mConnectionIV.setImageResource(R.drawable.nosignal_v1);
                break;
            case DEVICE_CONNECTING:
                mConnectionIV.setImageResource(R.drawable.connecting3_v1);
                break;
            case DEVICE_CONNECTED:
                mConnectionIV.setImageResource(R.drawable.connected_v1);
                break;
        }
    }

    @Override
    public void onMeditationReceived(int medValue) {}

    @Override
    public void onPoorSignalReceived(ESignalVolume signalVolume) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
