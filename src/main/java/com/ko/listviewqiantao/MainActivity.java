package com.ko.listviewqiantao;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = MainActivity.class.getSimpleName();
    private ExpandableListView mExpandableListView;
//	private List<ArrayList<CurrentBean.DataBean>> mchildData = new ArrayList<ArrayList<CurrentBean.DataBean>>();
Map<String, List<CurrentBean.DataBean>> result = new HashMap<>(  );
	private  MyExtendableListViewAdapter adapter;
	private Map<String,List<CurrentBean.DataBean>> datasets = new HashMap<>(  );
	//遍历集合,获取名字填充到这个数组中
	private String[] PList = new String[result.keySet().size()];
	//父布局
	List<CurrentBean> parentEntities;
	//子布局
	List<CurrentBean.DataBean> childEntities;

	//放名字的list集合
	List<String> nalis = new ArrayList<>();




	private int lastClick = -1;//上次惦记的group的position

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		mExpandableListView = findViewById(R.id.lv_external);
//		initData();
		//設置分組監聽Group点击事件,点击一个group隐藏其他的(只显示一个)
		mExpandableListView.setOnGroupClickListener( new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if (lastClick == -1){
					mExpandableListView.expandGroup( groupPosition );
				}
				if (lastClick != -1 && lastClick != groupPosition){
					mExpandableListView.collapseGroup( lastClick );
					mExpandableListView.expandGroup( groupPosition );
				}else if (lastClick == groupPosition){
					if (mExpandableListView.isGroupExpanded( groupPosition )){
						mExpandableListView.collapseGroup( groupPosition );
					}else if (!mExpandableListView.isGroupExpanded( groupPosition )){
						mExpandableListView.expandGroup( groupPosition );
					}
				}
				lastClick = groupPosition;
				return true;
			}
		} );
		//设置子项布局监听
		mExpandableListView.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//				result.get( groupPosition ).get( childPosition ).getDate()
				return true;

			}
		} );
		//控制只能打开一个组
		mExpandableListView.setOnGroupExpandListener( new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				int count = new MyExtendableListViewAdapter().getGroupCount();
				for (int i = 0; i < count; i++) {
					if (i != groupPosition) {
						mExpandableListView.collapseGroup( i );
					}
				}
			}
		} );
		getCurData();

	}

	private void initData() {
		parentEntities = new ArrayList<>(  );
		childEntities = new ArrayList<>(  );
		CurrentBean.DataBean dataBean;

		String relt = "{\n" + "  \"error\":false,\n" + "  \"results\":" +
				"[\n" + "    " +
				"{\n" + "\t  \"id\":\"1\",\n" + "\t  \"co2\":\"7.0\",\n" + "\t  \"date\":\"2019/06/24\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"2\",\n" + "\t  \"co2\":\"7.0\",\n" + "\t  \"date\":\"2019/06/25\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"3\",\n" + "\t  \"co2\":\"7.0\",\n" + "\t  \"date\":\"2019/06/26\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"4\",\n" + "\t  \"co2\":\"7.0\",\n" + "\t  \"date\":\"2019/06/26\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"5\",\n" + "\t  \"co2\":\"7.0\",\n" + "\t  \"date\":\"2019/06/27\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"6\",\n" + "\t  \"co2\":\"8.0\",\n" + "\t  \"date\":\"2019/06/27\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"9\",\n" + "\t  \"co2\":\"6.0\",\n" + "\t  \"date\":\"2019/06/27\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"8\",\n" + "\t  \"co2\":\"6.0\",\n" + "\t  \"date\":\"2019/06/27\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"7\",\n" + "\t  \"co2\":\"6.0\",\n" + "\t  \"date\":\"2019/06/28\"\n" + "\t}," +
				"\n" + "\t{\n" + "\t  \"id\":\"10\",\n" + "\t  \"co2\":\"19.0\",\n" + "\t  \"date\":\"2019/06/29\"\n" + "\t}" +
				"\n" + "  \n" + "  \n" + " " +
				" ]\n" + "     \n" + "  \n" + "  \n" + "}";
		//使用fastjson吧json数据转成一个list集合
		//首先将PList填充数据,也就是解析name(我这个有重名的所以需要重新计算之类的)
//		parentEntities = JSON.parseArray( relt, CurrentBean.class );
		//遍历集合 将名字填到Plist中
//		PList = new String[parentEntities.size()];





	}

	//获取当前数据bean
	private void getCurData() {

		Retrofit retrofit = new Retrofit.Builder().baseUrl( "http://192.168.104.155:8080/" ).addConverterFactory( GsonConverterFactory.create() ).build();
		CurDataApi api = retrofit.create( CurDataApi.class );
		Call<CurrentBean> call = api.getCurData();
		call.enqueue( new Callback<CurrentBean>() {
			@Override
			public void onResponse(Call<CurrentBean> call, Response<CurrentBean> response) {
				List<CurrentBean.DataBean> data = response.body().getData();

				//此处需要使用适配器放置数据
				Log.e(TAG, "开始处理数据----------");

				//把数据按名字分类封装
				result = getKindsName( data );
				Set<String> strings1 = result.keySet();
				Iterator<String> iterator = strings1.iterator();

				while (iterator.hasNext()) {
					System.out.println( "所有的不同的名字" + iterator.next());
//					nalis.add( iterator.next());
				}

				MyExtendableListViewAdapter myExtendableListViewAdapter = new MyExtendableListViewAdapter(MainActivity.this,data,nalis );
				mExpandableListView.setAdapter( myExtendableListViewAdapter );
			}

			@Override
			public void onFailure(Call<CurrentBean> call, Throwable t) {
				t.printStackTrace();
			}
		} );

	}
	private static Map<String, List<CurrentBean.DataBean>> getKindsName(List<CurrentBean.DataBean> data) {
		Map<String,List<CurrentBean.DataBean>> resultMap = new HashMap<String, List<CurrentBean.DataBean>>(  );

		//		Map<String,List<String>>  namelist = new HashMap<String, List<String>>(  );
				List<String> kindname = new ArrayList<String>(  );
		for (CurrentBean.DataBean myListBean : data){
			if (resultMap.containsKey( myListBean.getName())){//map中不同名字已存在,将该数据存放到同一个key的map中
				//                namelist.get( myListBean.getName() ).add( myListBean.getName() );//添加种类名字
				resultMap.get( myListBean.getName() ).add( myListBean );
			}else {//map中不存在的,新建一个key,用来存放数据
				kindname.add( myListBean.getName());
				List<CurrentBean.DataBean> newKeyListBean = new ArrayList<CurrentBean.DataBean>(  );
				newKeyListBean.add( myListBean );
				resultMap.put( myListBean.getName(),newKeyListBean );

			}
		}
		return resultMap;
	}
}
