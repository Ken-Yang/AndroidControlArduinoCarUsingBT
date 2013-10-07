package net.kenyang.controlcarusingbt;

import java.util.ArrayList;
import java.util.HashMap;

import net.kenyang.controlcarusingbt.R;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListSelectBT extends ListActivity {

	private ArrayList<HashMap<String,Object>> alBT = new ArrayList<HashMap<String,Object>>();
	private HashMap<String,Object> hmItem = new HashMap<String, Object>(); 
	private static BluetoothAdapter mBluetoothAdapter = null; // used for searching and managing bluetooth device
	private final int REQUEST_ENABLE_BT=1;
	private boolean bIsRegister = false;
	private AdapterListSelectBT adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liset_select_bt);
		this.setTitle(getString(R.string.list_title));
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    Toast.makeText(this, "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
		    return;
		}
		        
		// if bluetooth is disable
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent mIntentOpenBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(mIntentOpenBT, REQUEST_ENABLE_BT);
		}else{
			fnSearchBT();
		}
		
			
	}
	
	private void fnSearchBT(){
	
		if (!bIsRegister){
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mReceiver, filter); 
			mBluetoothAdapter.startDiscovery(); // start search
			bIsRegister = true;
		}	
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (bIsRegister){
			mBluetoothAdapter.cancelDiscovery();
			unregisterReceiver(mReceiver);
			bIsRegister = false;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		fnSearchBT();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_ENABLE_BT){
			fnSearchBT();
		}
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(ListSelectBT.this, ActivityControlPage.class);
		mBluetoothAdapter.cancelDiscovery();
		intent.putExtra(Constant.ITEM, alBT.get(position));
		
		startActivity(intent);
		this.finish();
		
		
	}

	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            // When search a device
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
            	
                // Get bluetooth device
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                hmItem = new HashMap<String, Object>(); 
                hmItem.put(Constant.NAME, device.getName().trim());
                hmItem.put(Constant.DEVICE, device);
                alBT.add(hmItem);
                if (adapter==null){
            		adapter = new AdapterListSelectBT(ListSelectBT.this, alBT) ;
                    setListAdapter(adapter);
            	}else{
            		adapter.fnUpdate(alBT);
            	}
                
            }
       }
	};	
}
