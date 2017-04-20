package next.web;

import next.container.RequestMapping;
import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        requestMapping = new RequestMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Controller controller = requestMapping.getController( req.getRequestURI() );
            String path = controller.execute( req, resp );

            if( path.startsWith("redirect:") ) {
                resp.sendRedirect( path.replaceFirst("redirect:", "") );
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher( path );
                requestDispatcher.forward( req, resp );
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug( e.getMessage() );
        }
    }

    @Override
    public void destroy() {

    }
}
