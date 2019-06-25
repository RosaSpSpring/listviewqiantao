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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
	String[] groupString = {"sheshou" , "fuzhu" , "tanke" , "fashi"};
	String[][] childString  = {
			{"孙尚香","后羿","马可波罗","狄仁杰"},
			{"孙膑","蔡文姬","鬼谷子","杨玉环"},
			{"张飞","廉颇","牛魔","项羽"},
			{"诸葛亮","王昭君","安琪拉","干将"}

	};

	public MyExtendableListViewAdapter(Context context, List<CurrentBean.DataBean> data) {
		this.mDataBeans = data;
		this.mContext =  context;
	}

	public MyExtendableListViewAdapter() {

	}

	//获取分组的个数
	@Override
	public int getGroupCount() {
		return groupString.length;
	}
//获取指定分组中的子选项的个数
	@Override
	public int getChildrenCount(int groupPosition) {
		return childString[groupPosition].length;
	}
//获取指定的分组数据
	@Override
	public Object getGroup(int groupPosition) {
		return groupString[groupPosition];
	}
//获取制定分组中的指定子选项数据
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childString[groupPosition][childPosition];
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
		return true;
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
		//这里需要通过适配器放置数据
		groupViewHolder.tv_group_cell_name.setText( mDataBeans.get( groupPosition ).getName() );
		groupViewHolder.tv_group_cell_daici.setText( mDataBeans.get( groupPosition ).getDaici() );
		groupViewHolder.tv_group_cell_caozuo.setText( mDataBeans.get( groupPosition ).getCaozuo() );
		groupViewHolder.tv_group_cell_pingshu.setText( mDataBeans.get( groupPosition ).getPingshu() );
		groupViewHolder.tv_group_cell_date.setText( mDataBeans.get( groupPosition ).getDate() );
		groupViewHolder.tv_group_cell_operator.setText( mDataBeans.get( groupPosition ).getOperator() );

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
		//此处需要使用适配器放置数据
		Log.e(TAG, "开始处理数据-----------------");
		//筛选数据
		List<String> nameList = new ArrayList<>(  );
		nameList.add( "hela" );
		List<CurrentBean.DataBean> result = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			//筛选过后的集合数据
			result = mDataBeans.stream().filter((CurrentBean.DataBean mybean) -> nameList.contains( mybean.getName())).collect( Collectors.toList());
			//按照属性进行排序

			//打印原有的mDataBeans集合中的数据
			mDataBeans.forEach( (CurrentBean.DataBean mybean) -> Log.e(TAG, mybean.getName() + "mDataBeans--->" +mybean.getOperator()));
			//打印过滤后的mDataBeans中的数据
			result.forEach( (CurrentBean.DataBean mybean) -> Log.e(TAG, mybean.getName() + "result--->" +mybean.getOperator()  ) );

		}
		System.out.println("进入筛选了");
		Log.e(TAG, "已经进入筛选了" );

		ComparatorDate c = new ComparatorDate();
		Collections.sort(result, c);  // 订单按时间排序

		childViewHolder.tv_child_cell_name.setText( result.get(groupPosition).getName() );
		childViewHolder.tv_child_cell_daici.setText( result.get( groupPosition ).getDaici() );
		childViewHolder.tv_child_cell_caozuo.setText( result.get( groupPosition ).getCaozuo() );
		childViewHolder.tv_child_cell_pingshu.setText( result.get( groupPosition ).getPingshu() );
		childViewHolder.tv_child_cell_date.setText( result.get( groupPosition ).getDate() );
		childViewHolder.tv_child_cell_operator.setText( result.get( groupPosition ).getOperator() );
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
}
