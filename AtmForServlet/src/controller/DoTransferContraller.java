package controller;

import service.AtmService;
import util.MySpring;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("all")
public class DoTransferContraller extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String aname = req.getParameter("aname");
        String money = req.getParameter("money");
        String to = req.getParameter("to");

        AtmService service = MySpring.getBean("service.AtmService");
        String result = service.transfer(aname,to,Float.parseFloat(money));

        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write("<html>");
        out.write("<head>");
        out.write("    <meta charset = \"UTF-8\">");
        out.write("</head>");
        out.write("<body>");
        out.write(result);
        out.write("===========================<br>");
        out.write("欢迎" + aname + "进入zghATM系统<br>");
        out.write("===========================<br>");
        out.write("请输入操作选项：<br>");
        out.write("    <a href = \"query?aname=" + aname + "\">1.查询</a><br>");
        out.write("    <a href = \"deposit?aname=" + aname + "\">2.存款</a><br>");
        out.write("    <a href = \"withdraw?aname=" + aname + "\">3.取款</a><br>");
        out.write("    <a href = \"transfer?aname=" + aname + "\">4.转账</a><br>");
        out.write("</body>");
        out.write("</html>");

        out.flush();
    }
}
