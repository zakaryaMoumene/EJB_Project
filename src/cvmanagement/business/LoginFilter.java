package cvmanagement.business;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        if (null == ((HttpServletRequest) request).getSession().getAttribute("loggedUser")) {
            
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
            return;
        }
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            ((HttpServletResponse) response).sendRedirect(contextPath+"/errorPage.xhtml");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

}