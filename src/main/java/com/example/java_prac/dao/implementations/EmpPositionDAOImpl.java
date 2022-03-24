package com.example.java_prac.dao.implementations;

import com.example.java_prac.HibernateInstance;
import com.example.java_prac.dao.interfaces.EmpPositionDAO;
import com.example.java_prac.entities.EmpPositionEntity;
import com.example.java_prac.entities.EmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpPositionDAOImpl implements EmpPositionDAO {
    @Getter
    @Setter
    public class Filter {
        long id = -1;
        long unitId = -1;
        long empId = -1;
        String posName = null;
        Date startTimestamp = null;
        Date endTimestamp = null;
    }

    @Override
    public void addEmpPosition(EmpPositionEntity emp_position) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(emp_position);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteEmpPosition(EmpPositionEntity emp_position) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(emp_position);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<EmployeeEntity> getEmpPositionListByFilter(Filter filter) {
        try (Session session = HibernateInstance.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeEntity> criteriaQuery = builder.createQuery(EmployeeEntity.class);
            Root<EmployeeEntity> root = criteriaQuery.from(EmployeeEntity.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getId() != -1) {
                predicates.add(builder.equal(root.get("posId"), filter.getId()));
            }
            if (filter.getUnitId() != -1) {
                predicates.add(builder.equal(root.get("unitId"), filter.getUnitId()));
            }
            if (filter.getEmpId() != -1) {
                predicates.add(builder.equal(root.get("empId"), filter.getEmpId()));
            }
            if (filter.getPosName() != null) {
                String pattern = "%" + filter.getPosName() + "%";
                predicates.add(builder.like(root.get("posName"), pattern));
            }
            if (filter.getStartTimestamp() != null) {
                predicates.add(builder.equal(root.get("startTimestamp"), filter.getStartTimestamp()));
            }
            if (filter.getEndTimestamp() != null) {
                predicates.add(builder.equal(root.get("endTimestamp"), filter.getEndTimestamp()));
            }

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public EmpPositionEntity getEmpPositionByID(long pos_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmpPositionEntity> criteria_query = criteria_builder.createQuery(EmpPositionEntity.class);
        Root<EmpPositionEntity> root = criteria_query.from(EmpPositionEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("posId"), pos_id));

        List<EmpPositionEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public List<EmpPositionEntity> getEmpPositionListByUnitID(long unit_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmpPositionEntity> criteria_query = criteria_builder.createQuery(EmpPositionEntity.class);
        Root<EmpPositionEntity> root = criteria_query.from(EmpPositionEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("unitId"), unit_id));

        List<EmpPositionEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res;
    }

    @Override
    public List<EmpPositionEntity> getEmpPositionListByEmpID(long emp_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<EmpPositionEntity> criteria_query = criteria_builder.createQuery(EmpPositionEntity.class);
        Root<EmpPositionEntity> root = criteria_query.from(EmpPositionEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("empId"), emp_id));

        List<EmpPositionEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res;
    }
}
