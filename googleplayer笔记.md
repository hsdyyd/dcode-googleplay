#google player项目笔记

###1. 工程创建并使用github来管理
###2. 使用ActionBar

* 引用v7包
* 让当前的Activity继承自ActionBarActivity
* 修改主题:@style/Theme.AppComp.xxx
* ActionBar设置

		/**初始化ActionBar**/
		private void initActionBar()
		{
			mActionBar = getSupportActionBar();
			mActionBar.setTitle("GooglePlay");
			mActionBar.setIcon(R.drawable.ic_launcher);
			mActionBar.setDisplayShowTitleEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}

###3. 常用工具类编写及导入
	<application android:name="com.droid.googleplay.base.BaseApplication">	

###4. tab标签页使用
	*  导入PagerSlidingTabStrip库
	*  布局中使用PagerSlidingTabStrip组件
	
			<com.astuetz.PagerSlidingTabStrip
		        android:id="@+id/main_tabs"
		        android:layout_width="match_parent"
		        android:layout_height="48dp" >
		    </com.astuetz.PagerSlidingTabStrip>

###5. 使用viewpager实现左右滑动
	* 导入v4包
	* v4包源码关联
	* 路径:android_sdk_home/extras/android/support/v4/src/java
	* 操作:把libs下的v4包add to build path
	* 注意:build path中的order and export中v4的位置
	* 布局中使用ViewPager组件
	
			<android.support.v4.view.ViewPager
	        android:id="@+id/main_viewpager"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent" >
	   		</android.support.v4.view.ViewPager>
	
	* 给viewpager设置适配器

###6. pageslidingtabstrip与viewpager绑定	
	mTabs.setViewPager(mViewPager);	

   * 重写PagerAdapter的getPageTitle

		/**必须重写此方法,否则tabs没有标签文本,会报空指针**/
		@Override
		public CharSequence getPageTitle(int position)
		{
			return mMainTitles[position];
		}

###7. 设置PagerSlidingTabStrip的样式
	
	<com.astuetz.PagerSlidingTabStrip
        xmlns:droid="http://schemas.android.com/apk/res-auto"
        android:id="@+id/main_tabs"
        android:layout_width="match_parent"
        android:layout_height="48dp" 
        droid:pstsIndicatorColor="#ff0000"
        droid:pstsIndicatorHeight="8dp"
        >

###8. 使用FragmentPagerAdapter给ViewPager填充数据
	@Override
	public Fragment getItem(int arg0)
	{
		// 创建fragment的工厂
		
		return null;
	}

###9. fragment的创建工厂
	// 根据FragmentPagerAdapter适配器的getItem(int position)来创建对应的fragment
	public static Fragment getFragment(int position)
	
###10. 对viewpager上显示的fragment实现缓存
	SparseArrayCompat<Fragment> cacheFragment = new SparseArrayCompat<Fragment>();
		
	Fragment tempFragment = cacheFragment.get(position);
	if(tempFragment!=null)
	{
		fragment = tempFragment;
		return fragment;
	}

	cacheFragment.put(position, fragment);

###11. fragment普通封装
* 1.继承自fragment
* 2.重写相关常用方法
* 3.onCreateView方法中返回显示视图

###12. fragment中4种常见显示界面
	private void initCommonView()
	{
		mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		this.addView(mLoadingView);
		
		mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		this.addView(mErrorView);
		
		mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		this.addView(mEmptyView);
		
		// 当加载完数据后，根据加载数据的状态来确定界面显示的视图
		refreshUI();
	}

###13. loadingpager基本实现
	private void refreshUI()
	{
		mLoadingView.setVisibility((mCurState==STATE_LOADING)?0:8);
		
		mErrorView.setVisibility((mCurState==STATE_ERROR)?0:8);
		
		mEmptyView.setVisibility((mCurState==STATE_EMPTY)?0:8);
		
		if(mSuccessView==null&&mCurState==STATE_SUCCESS)
		{
			mSuccessView = initSuccessView();
			
			this.addView(mSuccessView);
		}
		
		if(mSuccessView!=null)
		{
			mSuccessView.setVisibility((mCurState==STATE_SUCCESS)?0:8);
		}
	}
	
	
	public void loadData()
	{
		new Thread(new LoadingTask()).start();
	}

	class LoadingTask implements Runnable
	{

		public void run()
		{
			LoadResult tempState = initData();
			
			mCurState = tempState.getState();
			
			UIUtils.postTaskSafely(new Runnable()
			{
				
				public void run()
				{
					refreshUI();
				}
			});
		}
	}
	
	
	public abstract LoadResult initData();
	
	public abstract View initSuccessView();
	
	public enum LoadResult
	{
		ERROR(STATE_ERROR),EMPTY(STATE_EMPTY),SUCCESS(STATE_SUCCESS),LOADING(STATE_LOADING);
		int state;
		
		public int getState()
		{
			return state;
		}

		LoadResult(int state)
		{
			this.state = state;
		}
	}

###14. 触发loadDate方法调用
	mTabs.setOnPageChangeListener(new OnPageChangeListener()
		{
			public void onPageSelected(int position)
			{
				BaseFragment fragment = FragmentFactory.getFragment(position);
				if(fragment!=null)
				{
					LoadingPager loadingPager = fragment.getLoadingPager();
					loadingPager.loadData();
				}
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}
			
			public void onPageScrollStateChanged(int state)
			{
				
			}
		});

###15. loadingpager和basefragment的7个优化
	
###16. 线程池的引入
	TimeUnit unit = TimeUnit.MILLISECONDS;
					BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
					ThreadFactory threadFactory = Executors.defaultThreadFactory();
					RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
					mExecutor = new ThreadPoolExecutor(mCorePoolSize, 
							mMaximumPoolSize, 
							mKeepAliveTime, 
							unit, 
							workQueue, 
							threadFactory, 
							handler);

###17. 创建线程池工厂
	public class ThreadPoolFactory
	{
		static ThreadPoolProxy mNormalPool;
		static ThreadPoolProxy mDownloadPool;
	}

###18. 项目中使用线程池

###19. homefragment中使用listview

###20. homeholder的抽取
	public class HomeHolder
	{
	public View mViewHolder;
	TextView mTvTmp1;
	TextView mTvTmp2;
	private String mData;
	
	public HomeHolder()
	{
		mViewHolder = initView();
		mViewHolder.setTag(this);
	}

	private View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
		mTvTmp1 = (TextView) view.findViewById(R.id.tmp_tv_1);
		mTvTmp2 = (TextView) view.findViewById(R.id.tmp_tv_2);
		
		return view;
	}
	
	public void setDataAndRefreshHolderView(String data)
	{
		mData = data;
		refreshHolderView(mData);
	}
	
	public void refreshHolderView(String data)
	{
		mTvTmp1.setText("我是头: "+data);
		mTvTmp2.setText("我是尾: "+data);
	}
	}

###21. baseholder的抽取
	public abstract class BaseHolder<T>
	{
		public View mViewHolder;
		private T mData;
		
		public BaseHolder()
		{
			mViewHolder = initHolderView();
			mViewHolder.setTag(this);
		}
	
		public void setDataAndRefreshHolderView(T data)
		{
			mData = data;
			refreshHolderView(mData);
		}
		
		public abstract View initHolderView();
		public abstract void refreshHolderView(T data);
	}

	public class HomeHolder extends BaseHolder<String>
	{
		private TextView mTvTmp1;
		private TextView mTvTmp2;
	
		public View initHolderView()
		{
			View view = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
			mTvTmp1 = (TextView) view.findViewById(R.id.tmp_tv_1);
			mTvTmp2 = (TextView) view.findViewById(R.id.tmp_tv_2);
			
			return view;
		}
		
		public void refreshHolderView(String data)
		{
			mTvTmp1.setText("我是头: "+data);
			mTvTmp2.setText("我是尾: "+data);
		}
	}

###22. baseholder与superbaseadapter整合
	public View getView(int position, View convertView, ViewGroup parent)
	{
		BaseHolder holder;
		if(convertView==null)
		{
			holder = getSpecialHolder();
		}
		else
		{
			holder = (BaseHolder) convertView.getTag();
		}
		holder.setDataAndRefreshHolderView(mDataSource.get(position));
		
		return holder.mViewHolder;
	}

	public abstract BaseHolder getSpecialHolder();

###23. 