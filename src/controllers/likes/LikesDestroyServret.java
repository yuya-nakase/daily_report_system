package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
 * Servlet implementation class LikesDestroyServret
 */
@WebServlet("/likes/destroy")
public class LikesDestroyServret extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesDestroyServret() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class,Integer.parseInt(request.getParameter("report.id")));
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Like l = new Like();

        try{
            l = em.createNamedQuery("getLikedDecide",Like.class)
                  .setParameter("report",r)
                  .setParameter("employee",login_employee)
                  .getSingleResult();
            }catch(NoResultException e){
               l = null;
            }

        em.getTransaction().begin();
        em.remove(l);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}
