package net.htwater.myfinal.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import net.htwater.myfinal.util.MyUtil;

public class DefaultInterceptor implements Interceptor {

	private static Logger logger = Logger.getLogger(DefaultInterceptor.class.getName());

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub

		StringBuffer logString = new StringBuffer();

		logString.append("api@");
		logString.append(inv.getController().getRequest().getRemoteAddr() + "@");
		logString.append(inv.getViewPath() + inv.getMethodName() + "@");

		// print all parameters
		HttpServletRequest request = inv.getController().getRequest();
		Enumeration<String> e = request.getParameterNames();
		if (e.hasMoreElements()) {
			logString.append("Parameter   : ");
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String[] values = request.getParameterValues(name);
				if (values.length == 1) {
					logString.append(name).append("=").append(values[0]);
				} else {
					logString.append(name).append("[]={");
					for (int i = 0; i < values.length; i++) {
						if (i > 0)
							logString.append(",");
						logString.append(values[i]);
					}
					logString.append("}");
				}
				logString.append("  ");
			}
		}

		logString.append("@");

		Long startTime = System.currentTimeMillis();

		inv.invoke();
		Long endTime = System.currentTimeMillis();

		logString.append(endTime - startTime + "@");

		logger.info(logString);

	}

}
