package com.xcy.hot;

/**
 * 封装加载类的信息
 * @author liuyazhuang
 *
 */
public class LoadInfo {
	//自定义的类加载
	private MyClassLoader myLoader;
	//记录要加载的类的时间戳-->加载的时间
	private long loadTime;
	private BaseManager manager;
	
	public LoadInfo(MyClassLoader myLoader, long loadTime) {
		super();
		this.myLoader = myLoader;
		this.loadTime = loadTime;
	}

	public MyClassLoader getMyLoader() {
		return myLoader;
	}

	public void setMyLoader(MyClassLoader myLoader) {
		this.myLoader = myLoader;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public BaseManager getManager() {
		return manager;
	}

	public void setManager(BaseManager manager) {
		this.manager = manager;
	}
}
