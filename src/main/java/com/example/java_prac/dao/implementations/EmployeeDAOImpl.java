package com.example.java_prac.dao.implementations;

import com.example.java_prac.HibernateInstance;
import com.example.java_prac.dao.interfaces.EmployeeDAO;
import com.example.java_prac.entities.EmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Getter
    @Setter
    public class Filter {
        long id = -1;
        String name = null;
        long currentPositionId = -1;
    }

    @Override
    public void addEmployee(EmployeeEntity employee) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteEmployee(EmployeeEntity employee) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<EmployeeEntity> getEmployeeListByFilter(Filter filter) {
        try (Session session = HibernateInstance.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeEntity> criteriaQuery = builder.createQuery(EmployeeEntity.class);
            Root<EmployeeEntity> root = criteriaQuery.from(EmployeeEntity.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getId() != -1) {
                predicates.add(builder.equal(root.get("empId"), filter.getId()));
            }
            if (filter.getName() != null) {
                String pattern = "%" + filter.getName() + "%";
                predicates.add(builder.like(root.get("empName"), pattern));
            }
            if (filter.getCurrentPositionId() != -1) {
                predicates.add(builder.equal(root.get("currentPositionId"), filter.getCurrentPositionId()));
            }

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public EmployeeEntity getEmployeeByID(long employee_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteria_query = criteria_builder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = criteria_query.from(EmployeeEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("empId"), employee_id));

        List<EmployeeEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public EmployeeEntity getEmployeeByName(String employee_name) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteria_query = criteria_builder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = criteria_query.from(EmployeeEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("empName"), employee_name));

        List<EmployeeEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public List<EmployeeEntity> getEmployeeListByName(String employee_name) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteria_query = criteria_builder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = criteria_query.from(EmployeeEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("empName"), employee_name));

        List<EmployeeEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res;
    }

    @Override
    public EmployeeEntity getEmployeeByCurrentPositionID(long cur_pos_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> criteria_query = criteria_builder.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = criteria_query.from(EmployeeEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("currentPositionId"), cur_pos_id));

        List<EmployeeEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }
}
