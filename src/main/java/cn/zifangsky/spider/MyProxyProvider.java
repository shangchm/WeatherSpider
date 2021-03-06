package cn.zifangsky.spider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

/**
 * A simple ProxyProvider. Provide proxy as round-robin without heartbeat and error check. It can be used when all proxies are stable.
 * @author code4crafter@gmail.com
 *         Date: 17/4/16
 *         Time: 10:18
 * @since 0.7.0
 */
public class MyProxyProvider implements ProxyProvider {

    private final List<Proxy> proxies;

    private final AtomicInteger pointer;
    
    
    private static Map<String,Integer> addessMap = new HashMap<String,Integer>();
    

    public MyProxyProvider(List<Proxy> proxies) {
        this(proxies, new AtomicInteger(-1));
    }

    private MyProxyProvider(List<Proxy> proxies, AtomicInteger pointer) {
        this.proxies = proxies;
        this.pointer = pointer;
    }

    public static MyProxyProvider from(Proxy... proxies) {
        List<Proxy> proxiesTemp = new ArrayList<Proxy>(proxies.length);
        for (Proxy proxy : proxies) {
            proxiesTemp.add(proxy);
        }
        return new MyProxyProvider(Collections.unmodifiableList(proxiesTemp));
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
    	  if(!page.isDownloadSuccess()){
    		 System.out.println("bad proxy:"+proxy.getHost()+":"+proxy.getPort());
    		 if(addessMap.containsKey(proxy.getHost()+":"+proxy.getPort())){
    		    int cishu = addessMap.get(proxy.getHost()+":"+proxy.getPort());
    		    addessMap.put(proxy.getHost()+":"+proxy.getPort(), cishu+1);
    		 }else{
    			addessMap.put(proxy.getHost()+":"+proxy.getPort(), 1); 
    		 }
    	  }
    }

    @Override
    public Proxy getProxy(Task task) {
    	Proxy proxy = proxies.get(incrForLoop());
    	String ip = proxy.getHost()+":"+proxy.getPort();
    	while(addessMap.containsKey(ip)&&addessMap.get(ip)>=3){
    		System.out.println("禁用ip:"+ip);
    		proxy = proxies.get(incrForLoop());
    		ip = proxy.getHost()+":"+proxy.getPort();
    	}
    	
        return proxy;
    }

    private int incrForLoop() {
        int p = pointer.incrementAndGet();
        int size = proxies.size();
        if (p < size) {
            return p;
        }
        while (!pointer.compareAndSet(p, p % size)) {
            p = pointer.get();
        }
        return p % size;
    }
}
