package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class,Integer.parseInt(request.getParameter("id")));
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        //中間テーブルより該当する日報のいいねの数を取得
        long likes_count = (long)em.createNamedQuery("getLikeCount",Long.class)
                                   .setParameter("report",r)
                                   .getSingleResult();

        //ログインしている社員が該当する日報にいいねしているかを判断
        Like l = new Like();

        try{
        l = em.createNamedQuery("getLikedDecide",Like.class)
              .setParameter("report",r)
              .setParameter("employee",login_employee)
              .getSingleResult();
        }catch(NoResultException e){
           l = null;
        }
        em.close();

        request.setAttribute("report",r);
        request.setAttribute("likes_count",likes_count);
        request.setAttribute("like",l);
        request.setAttribute("_token",request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
