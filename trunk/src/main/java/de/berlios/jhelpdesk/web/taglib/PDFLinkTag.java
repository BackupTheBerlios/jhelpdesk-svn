/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.web.taglib;

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
        StringBuilder sb =
                new StringBuilder("<a href=\"").append(this.url).append("?format=pdf");
        HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext.getRequest();

        Enumeration<String> paramNames = httpServletRequest.getParameterNames();

        while (paramNames.hasMoreElements()) {
            String _param = paramNames.nextElement();
            if ("format".equalsIgnoreCase(_param)) {
                continue;
            }
            String[] paramValues = httpServletRequest.getParameterValues(_param);
            for (int i = 0; i < paramValues.length; ++i) {
                sb.append("&").append(_param).append("=").append(paramValues[i]);
            }
        }
        sb.append("\">");
        try {
            pageContext.getOut().write(sb.toString());
        } catch (IOException ioex) {
            log.error("Wystapil problem z zapisem do strumienia.", ioex);
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
        log.debug("setUrl(String url)");
        this.url = url;
    }
}
