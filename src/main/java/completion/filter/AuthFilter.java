package completion.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest Req, ServletResponse Resp, FilterChain Chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) Req;
        HttpServletResponse rep = (HttpServletResponse) Resp;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage") || uri.endsWith("login") || uri.endsWith("registration")) {
            Chain.doFilter(req, rep);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            rep.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Chain.doFilter(req, rep);
    }
}
