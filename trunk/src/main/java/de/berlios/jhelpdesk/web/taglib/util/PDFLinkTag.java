package de.berlios.jhelpdesk.web.taglib.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PDFLinkTag extends TagSupport {

	private static final long serialVersionUID = -2052922583488994937L;
	private static Log log = LogFactory.getLog(PDFLinkTag.class);

	private String url;

	@Override
	public int doStartTag() throws JspException {
		log.debug("doStartTag()");
		StringBuilder sb = new StringBuilder("<a href=\"").append(this.url).append("?format=pdf");
		HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext.getRequest();

		@SuppressWarnings("unchecked") // @see ServletRequest.getParameterNames()
		Enumeration<String> paramNames = httpServletRequest.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String _param = paramNames.nextElement();
			if ("format".equalsIgnoreCase(_param)) {
				continue;
			}
			String[] paramValues = httpServletRequest.getParameterValues(_param);
			for (int i = 0; i < paramValues.length; ++i)
				sb.append("&").append(_param).append("=").append(paramValues[i]);
		}
		sb.append("\">");
		try {
			pageContext.getOut().write(sb.toString());
		} catch (IOException ioex) {
			log.error("Wystapil problem z zapisaem do strumienia.", ioex);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</a>");
		} catch (IOException ioex) {
			log.error("Wystapil problem z zapisaem do strumienia.", ioex);
		}
		return EVAL_PAGE;
	}

	public void setUrl(String url) {
		log.debug("setUrl( String url )");
		this.url = url;
	}
}
