package com.example.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager = null;
	private float[] gyro = new float[3];
	private float[] gyroMatrix = new float [9];
	private float[] gyroOrientation = new float [3];
	private float[] magnet = new float [3];
	private float[] accel = new float [3];
	private float[] accMagOrientation = new float [3];
	private float[] fusedOrienation = new float [3];
	private float[] rotationMatrix = new float [9];
	private float maxAbsAccX = 0.0f, maxAbsAccY = 0.0f, maxAbsAccZ = 0.0f;
	
	private TextView tvX;
	private TextView tvY;
	private TextView tvZ;
    
	private TextView tvMaxAccX;
	private TextView tvMaxAccY;
	private TextView tvMaxAccZ;
    
	private TextView tvYaw;
	private TextView tvRoll;
	private TextView tvPitch;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gyroOrientation[0] = 0.0f;
        gyroOrientation[1] = 0.0f;
        gyroOrientation[2] = 0.0f;
        
        gyroMatrix[0] = 1.0f; gyroMatrix[1] = 0.0f; gyroMatrix[2] = 0.0f;
        gyroMatrix[3] = 0.0f; gyroMatrix[4] = 1.0f; gyroMatrix[5] = 0.0f;
        gyroMatrix[6] = 0.0f; gyroMatrix[7] = 0.0f; gyroMatrix[8] = 1.0f;
        
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        
        tvX= (TextView)findViewById(R.id.accelXVal);
    	tvY= (TextView)findViewById(R.id.accelYVal);
    	tvZ= (TextView)findViewById(R.id.accelZVal);
    	
    	tvMaxAccX= (TextView)findViewById(R.id.maxAccX);
    	tvMaxAccY= (TextView)findViewById(R.id.maxAccY);
    	tvMaxAccZ= (TextView)findViewById(R.id.maxAccZ);
        
    	tvYaw= (TextView)findViewById(R.id.fusedYawVal);
    	tvRoll= (TextView)findViewById(R.id.fusedRollVal);
    	tvPitch= (TextView)findViewById(R.id.fusedPitchVal);

        tvX.setText("0.0");
        tvY.setText("0.0");
        tvZ.setText("0.0");
        
        tvYaw.setText("0.0");
        tvRoll.setText("0.0");
        tvPitch.setText("0.0");
        
        tvMaxAccX.setText("0.0");
        tvMaxAccY.setText("0.0");
        tvMaxAccZ.setText("0.0");
        
        initListeners();
        
    }
	protected void initListeners() 
	{
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),	
				SensorManager.SENSOR_DELAY_FASTEST);
		 
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_FASTEST);
		 
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_FASTEST);
	};
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {};

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		
		switch(event.sensor.getType()) {
	    case Sensor.TYPE_ACCELEROMETER:
	        // copy new accelerometer data into accel array and calculate orientation
	        System.arraycopy(event.values, 0, accel, 0, 3);
	        tvX.setText(String.format("%+1.2f", accel[0]));
	        tvY.setText(String.format("%+1.2f", accel[1]));
	        tvZ.setText(String.format("%+1.2f", accel[2]));
	        
	        if (Math.abs(accel[0]) > maxAbsAccX) { maxAbsAccX = Math.abs(accel[0]); tvMaxAccX.setText(String.format("%+1.2f", maxAbsAccX)); }
	        if (Math.abs(accel[1]) > maxAbsAccY) { maxAbsAccY = Math.abs(accel[1]); tvMaxAccY.setText(String.format("%+1.2f", maxAbsAccY)); }
	        if (Math.abs(accel[2]) > maxAbsAccZ) { maxAbsAccZ = Math.abs(accel[2]); tvMaxAccZ.setText(String.format("%+1.2f", maxAbsAccZ)); }
	        
	        //calculateAccMagOrientation();
	        break;
	 
	    case Sensor.TYPE_GYROSCOPE:
	        // process gyro data
	        //gyroFunction(event);
	        break;
	 
	    case Sensor.TYPE_MAGNETIC_FIELD:
	        // copy new magnetometer data into magnet array
	        //System.arraycopy(event.values, 0, magnet, 0, 3);
	        break;
	    }
	};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
 