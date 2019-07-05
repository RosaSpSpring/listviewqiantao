package com.ko.listviewqiantao;

import android.nfc.FormatException;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lxm
 * @version 2019/6/25-11:18
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class CurrentBean implements Comparable{


	/**
	 * msg : success
	 * data : [{"id":"1","name":"hela","daici":"P1","caozuo":"结束","pingshu":"17","date":"2019/06/14","operator":"杨"},{"id":"2","name":"hela","daici":"P1","caozuo":"结束","pingshu":"3","date":"2019/06/16","operator":"杨"},{"id":"3","name":"hela","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/17","operator":"杨"},{"id":"4","name":"jczgxb","daici":"P1","caozuo":"结束","pingshu":"5","date":"2019/06/11","operator":"杨"},{"id":"5","name":"jczgxb","daici":"P2","caozuo":"结束","pingshu":"5","date":"2019/06/18","operator":"杨"}]
	 */

	private String msg;
	private List<DataBean> data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	@Override
	public int compareTo(@NonNull Object o) {
		return 0;
	}

	public static class DataBean implements Comparable{
		public DataBean() {
		}

		public DataBean(String id, String name, String date) {
			this.id = id;
			this.name = name;
			this.date = date;
		}

		public DataBean(String id, String name, String daici, String caozuo, String pingshu, String date, String operator, List<ItemBean.DataBean> list_child) {
			this.id = id;
			this.name = name;
			this.daici = daici;
			this.caozuo = caozuo;
			this.pingshu = pingshu;
			this.date = date;
			this.operator = operator;
			this.list_child = list_child;
		}

		/**
		 * id : 1
		 * name : hela
		 * daici : P1
		 * caozuo : 结束
		 * pingshu : 17
		 * date : 2019/06/14
		 * operator : 杨
		 */

		private String id;
		private String name;
		private String daici;
		private String caozuo;
		private String pingshu;
		private String date;
		private String operator;

		private List<ItemBean.DataBean> list_child = new ArrayList<ItemBean.DataBean>();
		public List<ItemBean.DataBean> getList_child() {
			return list_child;
		}

		public void setList_child(List<ItemBean.DataBean> list_child) {
			this.list_child = list_child;
		}//致使父类和子类相关联，致使数据不会混乱排序

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDaici() {
			return daici;
		}

		public void setDaici(String daici) {
			this.daici = daici;
		}

		public String getCaozuo() {
			return caozuo;
		}

		public void setCaozuo(String caozuo) {
			this.caozuo = caozuo;
		}

		public String getPingshu() {
			return pingshu;
		}

		public void setPingshu(String pingshu) {
			this.pingshu = pingshu;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		@Override
		public int compareTo(@NonNull Object o) {
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd" );
			if (o instanceof DataBean) {
				DataBean dataBean = (DataBean) o;
//				String parse = dateFormat.format( this.getDate() );
//				String obj = dateFormat.format( dataBean.getDate() );
				return  - this.getDate().compareTo( dataBean.getDate() );
			}
			throw new RuntimeException( "数据类型不匹配" );

		}
	}
}
