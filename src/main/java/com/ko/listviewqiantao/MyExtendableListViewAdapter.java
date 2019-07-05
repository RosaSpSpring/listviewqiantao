package com.ko.listviewqiantao;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanComparator;

import static com.ko.listviewqiantao.MainActivity.TAG;

/**
 * @author lxm
 * @version 2019/6/25-10:21
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class MyExtendableListViewAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private List<CurrentBean.DataBean> mDataBeans;
	private List<String> mNamLis;
	Map<String, List<CurrentBean.DataBean>> result;


	public MyExtendableListViewAdapter(Context context, List<CurrentBean.DataBean> data,List<String> namLis) {
		this.mDataBeans = data;
		this.mContext =  context;
		this.mNamLis = namLis;

	}

	public MyExtendableListViewAdapter() {

	}

	//获取分组的个数
	@Override
	public int getGroupCount() {
		return result.size();
//		return 0;
	}
//获取指定分组中的子选项的个数
	@Override
	public int getChildrenCount(int groupPosition) {
		return result.get( mNamLis.get( groupPosition ) ).size();
	}
//获取指定的分组数据
	@Override
	public Object getGroup(int groupPosition) {
		return result.get( mNamLis.get( groupPosition ) );
	}
//获取制定分组中的指定子选项数据
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return result.get( mNamLis.get( groupPosition ) ).get( childPosition );
	}
//获取指定分组的ID,这个ID必须是唯一的
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
//获取子选项的ID,这个ID必须是唯一的
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
//	分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
	@Override
	public boolean hasStableIds() {
		return false;
	}
	/**
	 *
	 * 获取显示指定组的视图对象
	 *
	 * @param groupPosition 组位置
	 * @param isExpanded 该组是展开状态还是伸缩状态
	 * @param convertView 重用已有的视图对象
	 * @param parent 返回的视图对象始终依附于的视图组
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupViewHolder groupViewHolder ;
		if (convertView == null){
			convertView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.parent_item,parent,false );
			groupViewHolder = new GroupViewHolder();
			groupViewHolder.ll_group_parent = convertView.findViewById( R.id.ll_group_parent );
			groupViewHolder.tv_group_cell_name = convertView.findViewById( R.id.group_cell_name );
			groupViewHolder.tv_group_cell_daici = convertView.findViewById( R.id.group_cell_daici );
			groupViewHolder.tv_group_cell_caozuo = convertView.findViewById( R.id.group_cell_caozuo );
			groupViewHolder.tv_group_cell_pingshu = convertView.findViewById( R.id.group_cell_pingshu );
			groupViewHolder.tv_group_cell_date = convertView.findViewById( R.id.group_cell_date );
			groupViewHolder.tv_group_cell_operator = convertView.findViewById( R.id.group_cell_operator );
			convertView.setTag(groupViewHolder);
		}else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}



		//此处需要使用适配器放置数据
		Log.e(TAG, "开始处理数据----------");

		//把数据按名字分类封装
		result = getKindsName( mDataBeans );

		Set<Map.Entry<String, List<CurrentBean.DataBean>>> entrySet = result.entrySet();
		Iterator<Map.Entry<String, List<CurrentBean.DataBean>>> iterator_entryset = entrySet.iterator();

		Collection<List<CurrentBean.DataBean>> values = result.values();
		Iterator<List<CurrentBean.DataBean>> iterator_values = values.iterator();

		Set<String> strings1 = result.keySet();
		Iterator<String> iterator_name = strings1.iterator();




		List list = new ArrayList<String>();

		while (iterator_name.hasNext()){
			String next = iterator_name.next();
			list.add( next );
			List<CurrentBean.DataBean> value = result.get( next );
			Collections.sort( value );
			CurrentBean.DataBean dataBean = value.get( groupPosition );
			groupViewHolder.tv_group_cell_name.setText( dataBean.getName() );
			groupViewHolder.tv_group_cell_daici.setText( dataBean.getDaici() );
			groupViewHolder.tv_group_cell_caozuo.setText( dataBean.getCaozuo() );
			groupViewHolder.tv_group_cell_pingshu.setText( dataBean.getPingshu() );
			groupViewHolder.tv_group_cell_date.setText( dataBean.getDate() );
			groupViewHolder.tv_group_cell_operator.setText( dataBean.getOperator() );
			System.out.println( dataBean.toString() );
		}





		return convertView;
	}

	private void dealwithData() {



	}

	/**
	 *
	 * 获取一个视图对象，显示指定组中的指定子元素数据。
	 *
	 * @param groupPosition 组位置
	 * @param childPosition 子元素位置
	 * @param isLastChild 子元素是否处于组中的最后一个
	 * @param convertView 重用已有的视图(View)对象
	 * @param parent 返回的视图(View)对象始终依附于的视图组
	 * @return
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
	 *      android.view.ViewGroup)
	 */

	//取得显示给定分组给定子位置的数据用的视图
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder childViewHolder;
		if (convertView==null){
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item,parent,false);
			childViewHolder = new ChildViewHolder();
			childViewHolder.ll_child_child = convertView.findViewById(R.id.ll_child_child);
			childViewHolder.tv_child_cell_name = convertView.findViewById( R.id.tv_child_name );
			childViewHolder.tv_child_cell_daici = convertView.findViewById( R.id.tv_child_daici );
			childViewHolder.tv_child_cell_caozuo = convertView.findViewById( R.id.tv_child_caozuo );
			childViewHolder.tv_child_cell_pingshu = convertView.findViewById( R.id.tv_child_pingshu );
			childViewHolder.tv_child_cell_date = convertView.findViewById( R.id.tv_child_date );
			childViewHolder.tv_child_cell_operator = convertView.findViewById( R.id.tv_child_operator );
			convertView.setTag(childViewHolder);

		}else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

     	if (!isLastChild){
//			childViewHolder.tv_child_cell_name.setText( mChildDataBeans.get(groupPosition).get( childPosition ).getName() );
//			Log.e(TAG, "tvcellname>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + childViewHolder.tv_child_cell_name);

		}
//		childViewHolder.tv_child_cell_daici.setText( result.get( groupPosition ).getDaici() );
//		childViewHolder.tv_child_cell_caozuo.setText( result.get( groupPosition ).getCaozuo() );
//		childViewHolder.tv_child_cell_pingshu.setText( result.get( groupPosition ).getPingshu() );
//		childViewHolder.tv_child_cell_date.setText( result.get( groupPosition ).getDate() );
//		childViewHolder.tv_child_cell_operator.setText( result.get( groupPosition ).getOperator() );
		return convertView;
	}
	//指定位置上的子元素是否可选中
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	static class GroupViewHolder{
		LinearLayout ll_group_parent;
		TextView tv_group_cell_name;
		TextView tv_group_cell_daici;
		TextView tv_group_cell_caozuo;
		TextView tv_group_cell_pingshu;
		TextView tv_group_cell_date;
		TextView tv_group_cell_operator;
	}
	static class ChildViewHolder{
		LinearLayout ll_child_child;
		TextView tv_child_cell_name;
		TextView tv_child_cell_daici;
		TextView tv_child_cell_caozuo;
		TextView tv_child_cell_pingshu;
		TextView tv_child_cell_date;
		TextView tv_child_cell_operator;
	}
	private static Map<String, List<CurrentBean.DataBean>> getKindsName(List<CurrentBean.DataBean> data) {
		Map<String,List<CurrentBean.DataBean>> resultMap = new HashMap<String, List<CurrentBean.DataBean>>(  );

		//		Map<String,List<String>>  namelist = new HashMap<String, List<String>>(  );
		//		List<String> kindname = new ArrayList<String>(  );
		for (CurrentBean.DataBean myListBean : data){
			if (resultMap.containsKey( myListBean.getName())){//map中不同名字已存在,将该数据存放到同一个key的map中
				//                namelist.get( myListBean.getName() ).add( myListBean.getName() );//添加种类名字
				resultMap.get( myListBean.getName() ).add( myListBean );
			}else {//map中不存在的,新建一个key,用来存放数据
				//				kindname.add( myListBean.getName());
				List<CurrentBean.DataBean> newKeyListBean = new ArrayList<CurrentBean.DataBean>(  );
				newKeyListBean.add( myListBean );
				resultMap.put( myListBean.getName(),newKeyListBean );
			}
		}
		return resultMap;
	}
}
