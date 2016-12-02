package com.droid.googleplay.fragment;

import java.util.List;

import com.droid.googleplay.adapter.AppItemAdapter;
import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.bean.HomeBean;
import com.droid.googleplay.factory.ListViewFactory;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.holder.PictureHolder;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.protocol.HomeProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class HomeFragment extends BaseFragment
{

	private List<AppInfoBean> mDatas;
	private List<String> mPictures;
	private HomeAdapter mHomeAdapter;

	@Override
	public LoadResult initData()
	{
//		SystemClock.sleep(5000);
		
//		LoadResult[] res = {LoadResult.SUCCESS,LoadResult.EMPTY,LoadResult.ERROR};
//		Random random = new Random();
//		int index = random.nextInt(res.length);
		
//		mDatas = new ArrayList<String>();
//		for(int i=0;i<100;i++){
//			mDatas.add(i+"");
//		}
		
		// 访问网络
		try
		{
			/*HttpUtils httpUtils = new HttpUtils();
			// http://localhost:8080/GooglePlayServer/home?index=0
			String url = Constants.URLS.BASEURL + "home";
			
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("index", "0");
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
			String readString = responseStream.readString();
			System.out.println(readString);
			
			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(readString, HomeBean.class);*/
			HomeProtocol protocol = new HomeProtocol();
			HomeBean homeBean = protocol.loadData(0);
			
			LoadResult state = checkState(homeBean);
			if(state!=LoadResult.SUCCESS)
			{
				return state;
			}
			
			state = checkState(homeBean.list);
			if(state!=LoadResult.SUCCESS)
			{
				return state;
			}
			
			mDatas = homeBean.list;
			mPictures = homeBean.picture;
			
			return LoadResult.SUCCESS;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadResult.ERROR;
		}
		
	}

	@Override
	public View initSuccessView()
	{
//		TextView tv = new TextView(UIUtils.getContext());
//		tv.setText(this.getClass().getSimpleName());
		
		ListView lv = ListViewFactory.createListView();
		// 设置轮播图
		PictureHolder pictureHolder = new PictureHolder();
		
		pictureHolder.setDataAndRefreshHolderView(mPictures);
		View viewHolder = pictureHolder.getViewHolder();
		
		lv.addHeaderView(viewHolder);
		
		mHomeAdapter = new HomeAdapter(lv,mDatas);
		lv.setAdapter(mHomeAdapter);
		return lv;
	}
	
	private List<AppInfoBean> loadMore(int index) throws Exception
	{
		
		/*HttpUtils httpUtils = new HttpUtils();
		// http://localhost:8080/GooglePlayServer/home?index=0
		String url = Constants.URLS.BASEURL + "home";
		
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("index", index+"");
		ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
		String readString = responseStream.readString();
		System.out.println(readString);
		
		Gson gson = new Gson();
		HomeBean homeBean = gson.fromJson(readString, HomeBean.class);*/
		HomeProtocol protocol = new HomeProtocol();
		HomeBean homeBean = protocol.loadData(index);
		
		if(homeBean==null)
		{
			return null;
		}
		
		if(homeBean.list==null||homeBean.list.size()==0)
		{
			return null;
		}
		
		return homeBean.list;
	}
	
	class HomeAdapter extends AppItemAdapter
	{
		public HomeAdapter(AbsListView absListView, List<AppInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception
		{
			return loadMore(mDatas.size());
		}


//		public View getView(int position, View convertView, ViewGroup parent)
//		{
//			HomeHolder holder;
//			if(convertView==null)
//			{
//				holder = new HomeHolder();
////				convertView = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
////				holder.mTvTmp1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
////				holder.mTvTmp2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);
////				convertView.setTag(holder);
//			}
//			else
//			{
//				holder = (HomeHolder) convertView.getTag();
//			}
//			String data = mDatas.get(position);
//			holder.setDataAndRefreshHolderView(data);
////			
////			holder.mTvTmp1.setText("我是头: "+data);
////			holder.mTvTmp2.setText("我是尾: "+data);
//			return holder.mViewHolder;
//		}
		
//		class ViewHolder
//		{
//			TextView mTvTmp1;
//			TextView mTvTmp2;
//		}
		
	}
	
	@Override
	public void onResume()
	{
		if(mHomeAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mHomeAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().addObserver(appItemHolder);//删除
			}
			// 手动刷新-->重新获取状态,然后更新ui
			mHomeAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		if(mHomeAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mHomeAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);//删除
			}
		}
		super.onPause();
	}
}
