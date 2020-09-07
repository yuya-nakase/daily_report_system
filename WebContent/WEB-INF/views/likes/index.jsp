<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div>
          <c:choose>
                <c:when test="${fn:length(likes) > 0}">
                    <p>${likes_count} 人の社員がいいね！しました</p>
                         <c:forEach var="like" items="${likes}">
                            <ul>
                                <li><c:out value="${like.employee.name}" /></li>
                            </ul>
                         </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>いいね！をした社員はいません。</p>
                </c:otherwise>
            </c:choose>
        </div>
        <p><a href="<c:url value='/reports/show?id=${report.id}' />">日報の詳細へ戻る</a></p>
        </c:param>
</c:import>