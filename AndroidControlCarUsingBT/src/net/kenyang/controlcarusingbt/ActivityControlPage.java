package net.kenyang.controlcarusingbt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityControlPage extends Activity {
	private static BluetoothDevice mBluetoothDevice = null;
	private static BluetoothSocket mBluetoothSocket = null; 
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 
	private static final int CONNECT_SUCCESS = 1;
	private static  OutputStream mOutputStream = null;
	private boolean bIsConnected = false;
	private static ProgressDialog pd = null;
	private static Thread threadConnect = null;
	private HashMap<String, Object> hmItem = null;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_page);
		
		Button btnFoward, btnLeft,btnRight,btnBack,btnPause;
		btnFoward 	= (Button) findViewById(R.id.btnForward);
		btnLeft 	= (Button) findViewById(R.id.btnLeft);
		btnRight 	= (Button) findViewById(R.id.btnRight);
		btnBack 	= (Button) findViewById(R.id.btnBack);
		btnPause 	= (Button) findViewById(R.id.btnStop);
		
		btnFoward.setOnClickListener(listener);
		btnLeft.setOnClickListener(listener);
		btnRight.setOnClickListener(listener);
		btnBack.setOnClickListener(listener);
		btnPause.setOnClickListener(listener);
		
		
		hmItem 				= (HashMap<String, Object>) this.getIntent().getExtras().getSerializable("item");
		mBluetoothDevice 	= (BluetoothDevice) hmItem.get(Constant.DEVICE);
		threadConnect 		= new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					bIsConnected = true;
					mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
					mBluetoothSocket.connect();
					handler.sendMessage(handler.obtainMessage(CONNECT_SUCCESS));
					
				} catch (IOException e) {
				}
			}
		});
		threadConnect.start();
		fnShowPd();		
	}
	private void fnShowPd(){
		pd = new ProgressDialog(this);
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage(getString(R.string.loading));
		pd.show();
	}
	
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnForward:
					fnWrite("w");
				break;
				case R.id.btnBack:
					fnWrite("s");
					break;
				case R.id.btnLeft:
					fnWrite("a");
					break;
				case R.id.btnRight:
					fnWrite("d");
					break;
				case R.id.btnStop:
					fnWrite("x");
					break;

				default:
					break;	
			}
		}
	};
	
	

	// handle READ & CONNECT
	private static Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case CONNECT_SUCCESS:
					pd.dismiss();
					try {
						mOutputStream = mBluetoothSocket.getOutputStream();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				
	
				default:
					break;
			}
		}
	};
	
	private void fnWrite(String strValue){
		try {
			mOutputStream.write(strValue.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (mBluetoothSocket !=null){
			if (bIsConnected){
				try {
					mBluetoothSocket.close();
					finish();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
