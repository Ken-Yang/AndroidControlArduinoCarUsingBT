package net.kenyang.controlcarusingbt;

import java.util.ArrayList;
import java.util.HashMap;

import net.kenyang.controlcarusingbt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListSelectBT extends BaseAdapter {
	
	private LayoutInflater inflater;
	private Context cxt;
	private ArrayList<HashMap<String, Object>>al;
	private int iLength;
	
	
	public AdapterListSelectBT(Context cxt,ArrayList<HashMap<String, Object>> al) {
		this.cxt = cxt;
		this.al = al;
		this.iLength = al.size();
		inflater = LayoutInflater.from(this.cxt);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return iLength;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		ViewHolder holder;
		if (convertView ==null){
			convertView = inflater.inflate(R.layout.list_select_bt_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tvBtName);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(al.get(position).get(Constant.NAME).toString());
		
		
		return convertView;
	}
	static class ViewHolder {
		TextView name;
	}
	
	public void fnUpdate(ArrayList<HashMap<String, Object>> al){
		this.al = al;
		this.iLength = al.size();
		notifyDataSetChanged();
	}

}
