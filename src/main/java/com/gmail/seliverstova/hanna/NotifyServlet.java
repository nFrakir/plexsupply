package com.gmail.seliverstova.hanna;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "NotifyServlet", urlPatterns = "/notify")
public class NotifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h3>Hello World!</h3>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileNames = req.getParameter("names");
        resp.setContentType("text/html");

        try {
            Properties properties = new Properties();
            properties.load(getServletContext().getResourceAsStream("/resources/config.properties"));
            ProcessFiles pf = new ProcessFiles(properties);
            pf.read(fileNames);

            PrintWriter out = resp.getWriter();
            out.println("<h3>All files were sent correctly.</h3>");
        } catch (IOException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "There are errors.");
        }
    }
}
