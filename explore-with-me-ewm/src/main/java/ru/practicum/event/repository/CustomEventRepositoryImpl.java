package ru.practicum.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean isOnlyAvailable, PageRequest pageRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteria = cb.createQuery(Event.class);
        Root<Event> event = criteria.from(Event.class);
        criteria.select(event).where(
                cb.and(
                        cb.or(
                                cb.like(cb.upper(event.get("title")), "%" + text.toUpperCase() + "%"),
                                cb.like(cb.upper(event.get("annotation")), "%" + text.toUpperCase() + "%")
                        ),

                        cb.or(
                                cb.like(cb.upper(item.get("name")), "%" + pattern.toUpperCase() + "%"),
                                cb.like(cb.upper(item.get("description")), "%" + pattern.toUpperCase() + "%")
                        ),
                        cb.equal(item.get("available"), true)
                )
        );
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Event> findAllByParamsEventDateSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                    Boolean isOnlyAvailable, PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Event> findAllByParamsViewsSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                Boolean isOnlyAvailable, PageRequest pageRequest) {
        return null;
    }
}
