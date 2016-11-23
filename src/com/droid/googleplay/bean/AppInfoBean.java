/**
 * @auth yidong
 * @date 2016年9月18日下午8:24:11
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.bean;

import java.util.List;

public class AppInfoBean
{
	public String des; 		// 应用描述
	public String downloadUrl; // 应用下载地址
	public String iconUrl; 	// 应用图片下载地址
	public long id; 			// 应用id
	public String name; 		// 应用名称
	public String packageName; // 应用包名
	public long size; 			// 应用大小
	public float stars; 		// 应用评分
	
	/*-------------------- 添加详情页字段 --------------------*/
	public String author;
	public String date;
	public String downloadNum;
	public String version;
	
	public List<String> screen;
	public List<DetailSafe> safe;
	class DetailSafe{
		public String safeDes;
		public int safeDesColor;
		public String safeDesUrl;
		public String safeUrl;
	}
	@Override
	public String toString()
	{
		return "AppInfoBean [des=" + des + ", downloadUrl=" + downloadUrl + ", iconUrl=" + iconUrl + ", id=" + id
				+ ", name=" + name + ", packageName=" + packageName + ", size=" + size + ", stars=" + stars
				+ ", author=" + author + ", date=" + date + ", downloadNum=" + downloadNum + ", version=" + version
				+ ", screen=" + screen + ", safe=" + safe + "]";
	}
	
}
