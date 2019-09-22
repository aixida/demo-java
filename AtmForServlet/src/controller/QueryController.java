package controller;

import service.AtmService;
import util.MySpring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class QueryController extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String aname = req.getParameter("aname");

        AtmService service = MySpring.getBean("service.AtmService");
        float balance = service.query(aname);

        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write("<html>");
        out.write("<head>");
        out.write("    <meta charset = \"UTF-8\">");
        out.write("    <script type=\"text/javascript\">");
        out.write("          window.onload=function(){");
        out.write("              var inputElement = document.getElementById(\"back\");");
        out.write("              inputElement.onclick = function(){");
        out.write("                  window.history.back();");
        out.write("              }");
        out.write("          }");
        out.write("    </script>");
        out.write("</head>");
        out.write("<body>");
        out.write("    尊敬的"+aname+"您的可用余额为:" + balance+"<br>");
        out.write("    <input id=\"back\" type=\"button\" value=\"back\">");
        out.write("</body>");
        out.write("</html>");

        out.flush();

    }

}
