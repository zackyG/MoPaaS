package com.mopaas_mobile.adapter;

import java.util.ArrayList;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.domain.Balance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class BalanceListAdapter extends BaseAdapter {

	private LayoutInflater inflate;
	private ArrayList<Balance> balancelist;
	private Context context;
	
	public BalanceListAdapter(Context context,ArrayList<Balance> balanlist)
	{
		this.balancelist = balanlist;
		this.context = context;
		inflate = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		if(balancelist==null)return 0;
		else return balancelist.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(balancelist==null) return null;
		else return balancelist.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		if(balancelist==null) return 0;
		else return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null||convertView.getTag()==null)
		{
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.item_balancedetail, null);
			holder.detailBalanceTxt = (TextView)convertView.findViewById(R.id.detail_balance);
			holder.tradeNoTxt = (TextView)convertView.findViewById(R.id.trade_no);
			holder.balanceDateTxt = (TextView)convertView.findViewById(R.id.balance_date);
			holder.balanceStatusTxt = (TextView)convertView.findViewById(R.id.balance_status);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder)convertView.getTag();
			holder.detailBalanceTxt.setText("");
			holder.tradeNoTxt.setText("");
			holder.balanceStatusTxt.setText("");
			holder.balanceDateTxt.setText("");
		}
		holder.balanceDateTxt.setText((balancelist.get(position).balanceDate.split(" "))[0]);
		holder.tradeNoTxt.setText(balancelist.get(position).tradeNo);
		if(balancelist.get(position).balanceStatus.equals("1"))
		{
			holder.balanceStatusTxt.setText(R.string.paydone);
			holder.balanceStatusTxt.setTextColor(context.getResources().getColor(R.color.specifygrey));
		}else 
		{
			holder.balanceStatusTxt.setText(R.string.paying);
			holder.balanceStatusTxt.setTextColor(context.getResources().getColor(R.color.red));
		}
		holder.detailBalanceTxt.setText(context.getResources().getString(R.string.rmb)+balancelist.get(position).detailBalance + "元");
		return convertView;
	}
	private int switchType(String type)
	{
		if("0".equals(type))return R.string.balancetype_1;
		if("1".equals(type))return R.string.balancetype_2;
		if("2".equals(type))return R.string.balancetype_3;
		if("3".equals(type))return R.string.balancetype_4;
		else return R.string.balancetype_5;
	}
	class ViewHolder
	{
		TextView detailBalanceTxt;
		TextView tradeNoTxt;
		TextView balanceStatusTxt;
		TextView balanceDateTxt;
	}
}
