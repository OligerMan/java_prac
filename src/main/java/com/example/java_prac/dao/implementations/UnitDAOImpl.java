package com.example.java_prac.dao.implementations;

import com.example.java_prac.HibernateInstance;
import com.example.java_prac.dao.interfaces.UnitDAO;
import com.example.java_prac.entities.EmployeeEntity;
import com.example.java_prac.entities.UnitEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UnitDAOImpl implements UnitDAO {
    @Getter
    @Setter
    public class Filter {
        long id = -1;
        long directorId = -1;
        long higherUnitId = -1;
        String unitName = null;
    }

    @Override
    public void addUnit(UnitEntity unit) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(unit);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteUnit(UnitEntity unit) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(unit);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<EmployeeEntity> getUnitListByFilter(Filter filter) {
        try (Session session = HibernateInstance.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeEntity> criteriaQuery = builder.createQuery(EmployeeEntity.class);
            Root<EmployeeEntity> root = criteriaQuery.from(EmployeeEntity.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getId() != -1) {
                predicates.add(builder.equal(root.get("unitId"), filter.getId()));
            }
            if (filter.getDirectorId() != -1) {
                predicates.add(builder.equal(root.get("directorId"), filter.getDirectorId()));
            }
            if (filter.getHigherUnitId() != -1) {
                predicates.add(builder.equal(root.get("higherUnitId"), filter.getHigherUnitId()));
            }
            if (filter.getUnitName() != null) {
                String pattern = "%" + filter.getUnitName() + "%";
                predicates.add(builder.like(root.get("unitName"), pattern));
            }

            if (predicates.size() != 0)
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public UnitEntity getUnitByID(long unit_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<UnitEntity> criteria_query = criteria_builder.createQuery(UnitEntity.class);
        Root<UnitEntity> root = criteria_query.from(UnitEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("unitId"), unit_id));

        List<UnitEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public UnitEntity getUnitByDirectorID(long director_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<UnitEntity> criteria_query = criteria_builder.createQuery(UnitEntity.class);
        Root<UnitEntity> root = criteria_query.from(UnitEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("directorId"), director_id));

        List<UnitEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res.get(0);
    }

    @Override
    public List<UnitEntity> getUnitListByDirectorID(long director_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<UnitEntity> criteria_query = criteria_builder.createQuery(UnitEntity.class);
        Root<UnitEntity> root = criteria_query.from(UnitEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("directorId"), director_id));

        List<UnitEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res;
    }

    @Override
    public List<UnitEntity> getUnitListByHigherUnitID(long higher_unit_id) {
        Session session = HibernateInstance.getSessionFactory().openSession();
        CriteriaBuilder criteria_builder = session.getCriteriaBuilder();
        CriteriaQuery<UnitEntity> criteria_query = criteria_builder.createQuery(UnitEntity.class);
        Root<UnitEntity> root = criteria_query.from(UnitEntity.class);

        criteria_query.select(root).where(criteria_builder.equal(root.get("higherUnitId"), higher_unit_id));

        List<UnitEntity> res = session.createQuery(criteria_query).getResultList();
        return res.size() == 0 ? null : res;
    }
}
