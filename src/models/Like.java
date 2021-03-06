package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "likes")
//"getLikeCount"=該当する日報のいいねの数,"getLikedEmployees"=いいねを押した社員の一覧,"getLikedDecide"=いいね済みか判断
@NamedQueries({
    @NamedQuery(
            name = "getLikeCount",
            query = "SELECT COUNT(l) FROM Like AS l WHERE l.report = :report"
            ),
    @NamedQuery(
            name = "getLikedEmployees",
            query = "SELECT l FROM Like AS l WHERE l.report = :report ORDER BY l.id DESC"
            ),
    @NamedQuery(
            name = "getLikedDecide",
            query = "SELECT l FROM Like AS l WHERE l.report = :report AND l.employee = :employee"
            )
})
@Entity
public class Like {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id",nullable = false)
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
