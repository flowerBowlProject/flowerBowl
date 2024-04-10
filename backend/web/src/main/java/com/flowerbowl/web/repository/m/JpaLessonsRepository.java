package com.flowerbowl.web.repository.m;

import com.flowerbowl.web.domain.Lesson;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaLessonsRepository implements LessonsRepository{
    private final EntityManager em;
    public JpaLessonsRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public void LessonCreate(Lesson lesson) {
        em.persist(lesson);
    }

    @Override
    public Lesson findByLesson_no(Long lesson_no) {
        return em.createQuery("select l from Lesson l where l.lessonNo = :lesson_no", Lesson.class)
                .setParameter("lesson_no", lesson_no)
                .getSingleResult();
    }
    @Override
    public List<Lesson> findAllUser(Long user_no){
        return em.createQuery("select * from Lesson l left join  LessonLike ll on l.lessonNo = ll.lesson.lessonNo where ll.user.userNo = :user_no", Lesson.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

    public List<Lesson> findAll(){
        return em.createQuery("select * from Lesson")
                .getResultList();
    }
}
