package controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class WithdrawController extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String aname = req.getParameter("aname");

        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write("<!DOCTYPE html>");
        out.write("<html>");
        out.write("<head>");
        out.write("   <meta charset=\"UTF-8\">");
        out.write("   <title>withdraw</title>");
        out.write("</head>");
        out.write("<body>");
        out.write("    <form action=\"doWithdraw\" method=\"post\">");
        out.write("        请输入取款金额：<input type=\"text\" name=\"money\" value=\"\"><br>");
        out.write("        <input type=\"submit\" value=\"确定\"><br>");
        out.write("        <input type=\"hidden\" name=\"aname\" value=\""+aname+"\">");
        out.write("    </form>");
        out.write("</body>");
        out.write("</html>");

        out.flush();
    }

}
