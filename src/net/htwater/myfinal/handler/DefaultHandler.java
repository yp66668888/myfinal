package net.htwater.myfinal.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jfinal.config.Constants;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;

import net.htwater.myfinal.interceptor.DefaultInterceptor;
import net.htwater.myfinal.util.MyLog;

public class DefaultHandler extends Handler {
	
	private static Logger logger = Logger.getLogger(DefaultHandler.class.getName());

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		// TODO Auto-generated method stub
	    String url = request.getRequestURI();
	    if(PropKit.getBoolean("debug", false)){
	    	logger.info(target);
	    }
		nextHandler.handle(target, request, response, isHandled);
		
	}

}
