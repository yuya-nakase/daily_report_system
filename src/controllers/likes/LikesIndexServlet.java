package controllers.likes;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class LikesIndexServlet
 */
@WebServlet("/likes/index")
public class LikesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //該当する日報のidを取得
        Report r = em.find(Report.class,Integer.parseInt(request.getParameter("id")));

        //中間テーブルより該当する日報のレコードを取得
        List<Like> likes = em.createNamedQuery("getLikedEmployees",Like.class)
                             .setParameter("report",r)
                             .getResultList();

        //中間テーブルより該当する日報のいいねの数を取得
        long likes_count = (long)em.createNamedQuery("getLikeCount",Long.class)
                                   .setParameter("report",r)
                                   .getSingleResult();
        em.close();

        request.setAttribute("likes",likes);
        request.setAttribute("likes_count",likes_count);
        request.setAttribute("report",r);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/likes/index.jsp");
        rd.forward(request,response);
    }

}
