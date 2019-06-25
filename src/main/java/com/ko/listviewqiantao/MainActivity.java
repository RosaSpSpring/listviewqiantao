package com.ko.listviewqiantao;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = MainActivity.class.getSimpleName();
    private ExpandableListView mExpandableListView;
    String[] groupString = {"sheshou" , "fuzhu" , "tanke" , "fashi"};
    String[][] childString  = {
			{"孙尚香","后羿","马可波罗","狄仁杰"},
			{"孙膑","蔡文姬","鬼谷子","杨玉环"},
			{"张飞","廉颇","牛魔","项羽"},
			{"诸葛亮","王昭君","安琪拉","干将"}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		mExpandableListView = findViewById(R.id.lv_external);
		//設置分組監聽
		mExpandableListView.setOnGroupClickListener( new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Toast.makeText(MainActivity.this,groupString[groupPosition], Toast.LENGTH_SHORT).show();
				return false;
			}
		} );
		//设置子项布局监听
		mExpandableListView.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Toast.makeText(MainActivity.this,childString[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
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
   //获取当前数据bean
	private void getCurData() {

		Retrofit retrofit = new Retrofit.Builder().baseUrl( "http://192.168.104.175:8080/" ).addConverterFactory( GsonConverterFactory.create() ).build();
		CurDataApi api = retrofit.create( CurDataApi.class );
		Call<CurrentBean> call = api.getCurData();
		call.enqueue( new Callback<CurrentBean>() {
			@Override
			public void onResponse(Call<CurrentBean> call, Response<CurrentBean> response) {
				List<CurrentBean.DataBean> data = response.body().getData();
				MyExtendableListViewAdapter myExtendableListViewAdapter = new MyExtendableListViewAdapter(MainActivity.this,data);
				mExpandableListView.setAdapter( myExtendableListViewAdapter );
			}

			@Override
			public void onFailure(Call<CurrentBean> call, Throwable t) {

			}
		} );

	}
}
