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
	