package ru.practicum.event.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomEventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean isOnlyAvailable, Integer from, Integer size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = this.createPredicates(event, cb, text, categoriesIds, isPaid, rangeStart, rangeEnd,
                isOnlyAvailable, from, size);
        return entityManager
                .createQuery(query.select(event).where(cb.and(predicates.toArray(Predicate[]::new))))
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Event> findAllByParamsEventDateSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                    Boolean isOnlyAvailable, Integer from, Integer size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = this.createPredicates(event, cb, text, categoriesIds, isPaid, rangeStart, rangeEnd,
                isOnlyAvailable, from, size);
        return entityManager
                .createQuery(query.select(event).where(cb.and(predicates.toArray(Predicate[]::new)))
                        .orderBy(cb.desc(event.get("eventDate"))))
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Event> findAllByParamsViewsSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                Boolean isOnlyAvailable, Integer from, Integer size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = this.createPredicates(event, cb, text, categoriesIds, isPaid, rangeStart, rangeEnd,
                isOnlyAvailable, from, size);
        return entityManager
                .createQuery(query.select(event).where(cb.and(predicates.toArray(Predicate[]::new)))
                        .orderBy(cb.desc(event.get("views"))))
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Event> findAllByParams(List<Long> usersIds, List<State> states, List<Long> categories,
                                       LocalDateTime start, LocalDateTime finish, Integer from, Integer size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (usersIds != null && !usersIds.isEmpty()) {
            predicates.add(cb.in(event.get("initiator").get("id")).value(usersIds));
        }
        if (states != null && !states.isEmpty()) {
            predicates.add(cb.in(event.get("state")).value(states));
        }
        if (categories != null && !categories.isEmpty()) {
            predicates.add(cb.in(event.get("category").get("id")).value(categories));
        }
        predicates.add(cb.greaterThan(event.get("eventDate"), LocalDateTime.now()));
        predicates.add(cb.lessThan(event.get("eventDate"), finish));

        return entityManager.createQuery(query.select(event).where(cb.and(predicates.toArray(Predicate[]::new))))
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    private List<Predicate> createPredicates(Root<Event> event, CriteriaBuilder cb, String text,
                                             List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd, Boolean isOnlyAvailable, Integer from,
                                             Integer size) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(event.get("state"), State.PUBLISHED));
        predicates.add(cb.greaterThan(event.get("eventDate"), rangeStart));
        predicates.add(cb.lessThan(event.get("eventDate"), rangeEnd));
        if (text != null) {
            predicates.add(cb.or(
                    cb.like(cb.upper(event.get("title")), "%" + text.toUpperCase() + "%"),
                    cb.like(cb.upper(event.get("annotation")), "%" + text.toUpperCase() + "%")));
        }
        if (categoriesIds != null && !categoriesIds.isEmpty()) {
            predicates.add(cb.in(event.get("categories").get("id")).value(categoriesIds));
        }
        if (isPaid != null) {
            predicates.add(cb.equal(event.get("isPaid"), isPaid));
        }
        return predicates;
        }
}
