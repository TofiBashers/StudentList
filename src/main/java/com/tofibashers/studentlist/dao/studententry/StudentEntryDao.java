package com.tofibashers.studentlist.dao.studententry;


import com.tofibashers.studentlist.entity.StudentEntry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Component
public class StudentEntryDao
{

    @PersistenceContext
    private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<StudentEntry> findAll()
	{
		final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<StudentEntry> criteriaQuery = builder.createQuery(StudentEntry.class);

		Root<StudentEntry> root = criteriaQuery.from(StudentEntry.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<StudentEntry> typedQuery = this.entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

    @Transactional(readOnly = true)
    public StudentEntry find(Long id)
    {
        return this.entityManager.find(StudentEntry.class , id);
    }

    @Transactional
    public void delete(Long id)
    {
        if (id == null) {
            return;
        }

        StudentEntry studentEntry = this.find(id);
        if (studentEntry == null) {
            return;
        }

        this.entityManager.remove(studentEntry);
    }

    @Transactional
    public StudentEntry save(StudentEntry studentEntry)
    {
        return this.entityManager.merge(studentEntry);
    }

}
