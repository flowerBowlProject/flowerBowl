package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Lesson;
import com.flowerbowl.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO " +
            "   user (user_id, user_role) " + // , user_wd_status
            "SELECT " +
            "   :userId, :role " + // , FALSE
            "WHERE " +
            "   NOT EXISTS (SELECT 1 FROM user WHERE user_id = :userId)", nativeQuery = true)
    void insertIfNotExists(@Param("userId") String userId, @Param("role") String role);

    User findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserNickname(String userNickname);

    @Query(value = "SELECT " +
            "   l.lesson_no, " +
            "   l.lesson_title " +
            "FROM " +
            "   user u" +
            "INNER JOIN " +
            "   review_enable r ON u.user_no = r.user_no " +
            "INNER JOIN " +
            "   lesson l ON r.lesson_no = l.lesson_no " +
            "WHERE " +
            "   u.user_id = :userId AND r.review_enable = TRUE ", nativeQuery = true)
    List<Lesson> findAvailableReviewListByUserId(@Param("userId") String userId);

    @Query(value = "SELECT " +
            "    lr.lesson_rv_score," +
            "    DATE_FORMAT(lr.lesson_rv_date, '%Y-%m-%d'), " +
            "    lr.lesson_no, " +
            "    l.lesson_title, " +
            "    l.lesson_writer " +
            "FROM " +
            "    lesson_rv lr " +
            "INNER JOIN " +
            "    user u ON lr.user_no = u.user_no " +
            "INNER JOIN  " +
            "    lesson l ON lr.lesson_no = l.lesson_no " +
            "WHERE  " +
            "    u.user_id = :userId ", nativeQuery = true)
    List<Object[]> findReviewsByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user " +
            "SET " +
            "   user_wd_status = TRUE   " +
            "WHERE  " +
            "   user_id = :userId ", nativeQuery = true)
    void dateWd(@Param("userId") String userId);

    @Query(value = "SELECT " +
            "    DATE_FORMAT(p.pay_date, '%Y-%m-%d') AS pay_date, " +
            "    u.user_nickname, " +
            "    u.user_phone, " +
            "    l.lesson_title " +
            "FROM " +
            "    pay p " +
            "    INNER JOIN user u ON p.user_no = u.user_no " +
            "    INNER JOIN lesson l ON p.lesson_no = l.lesson_no " +
            "WHERE " +
            "    p.lesson_no IN ( " +
            "        SELECT " +
            "            l.lesson_no " +
            "        FROM " +
            "            user u " +
            "            INNER JOIN lesson l ON u.user_no = l.user_no " +
            "        WHERE " +
            "            u.user_id = :userId " +
            "    ) " +
            "ORDER BY " +
            "    p.pay_date DESC", nativeQuery = true)
    List<Object[]> findPurchaser(@Param("userId") String userId);

    @Query(value = "SELECT " +
            "    DATE_FORMAT(p.pay_date, '%Y-%m-%d') AS pay_date, " +
            "    p.pay_price, " +
            "    l.lesson_title, " +
            "    l.lesson_writer " +
            "FROM " +
            "    pay p " +
            "    INNER JOIN lesson l ON p.lesson_no = l.lesson_no " +
            "WHERE " +
            "    p.lesson_no IN ( " +
            "        SELECT " +
            "            p2.lesson_no " +
            "        FROM " +
            "            pay p2 " +
            "            INNER JOIN user u ON p2.user_no = u.user_no " +
            "        WHERE " +
            "            u.user_id = :userId)" +
            "ORDER BY " +
            "    p.pay_date DESC", nativeQuery = true)
    List<Object[]> findPaysByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pay " +
            "WHERE user_no = ( " +
            "    SELECT " +
            "       user_no  " +
            "    FROM " +
            "       USER  " +
            "    WHERE " +
            "       user_id = :userId) " +
            "       AND pay_no = :payNo ", nativeQuery = true)
    int deletePayByUser(@Param("userId") String userId, @Param("payNo") Long payNo);


    @Query(value = "SELECT " +
            "    DATE_FORMAT(r.recipe_date, '%Y-%m-%d') AS recipe_date, " +
            "    r.recipe_title, " +
            "    COUNT(l.user_no) AS bookmark_cnt, " +
            "    (SELECT COUNT(*) FROM comment c WHERE c.recipe_no = r.recipe_no) AS comment_cnt " +
            "FROM " +
            "    recipe r " +
            "LEFT JOIN " +
            "    recipe_like l ON r.recipe_no = l.recipe_no " +
            "INNER JOIN " +
            "    user u ON r.user_no = u.user_no " +
            "WHERE " +
            "    u.user_id = :userId " +
            "GROUP BY " +
            "    r.recipe_date, " +
            "    r.recipe_title " +
            "ORDER BY " +
            "    r.recipe_date DESC", nativeQuery = true)
    List<Object[]> findAllRecipeByUser(@Param("userId") String userId);


    @Query(value = "SELECT  " +
            "    DATE_FORMAT(l.lesson_date, '%Y-%m-%d') AS lesson_date, " +
            "    l.lesson_title, " +
            "    COUNT(ll.user_no) AS bookmark_cnt, " +
            "    (SELECT COUNT(*) FROM lesson_rv lr WHERE lr.lesson_no = l.lesson_no) AS review_cnt " +
            "FROM  " +
            "    lesson l " +
            "LEFT JOIN  " +
            "    lesson_like ll ON l.lesson_no = ll.lesson_no " +
            "INNER JOIN  " +
            "    user u ON l.user_no = u.user_no " +
            "WHERE  " +
            "    u.user_id = :userId " +
            "GROUP BY  " +
            "    l.lesson_date, " +
            "    l.lesson_title " +
            "ORDER BY  " +
            "    l.lesson_date DESC ", nativeQuery = true)
    List<Object[]> findAllLessonByUser(@Param("userId") String userId);

    @Query(value = "SELECT " +
            "    DATE_FORMAT(p.pay_date, '%Y-%m-%d') AS pay_date, " +
            "    l.lesson_title, " +
            "    l.lesson_writer, " +
            "    COALESCE(MAX(lr.lesson_rv_score), 0) AS lesson_rv_score " +
            "FROM " +
            "    pay p " +
            "    INNER JOIN lesson l ON p.lesson_no = l.lesson_no " +
            "    LEFT JOIN lesson_rv lr ON l.lesson_no = lr.lesson_no " +
            "    INNER JOIN user u ON p.user_no = u.user_no " +
            "WHERE " +
            "    u.user_id = :userId " +
            "GROUP BY " +
            "    p.pay_date, l.lesson_title, l.lesson_writer " +
            "ORDER BY " +
            "    p.pay_date DESC", nativeQuery = true)
    List<Object[]> findAllPayLesson(@Param("userId") String userId);

    User findByUserEmail(String userEmail);

    @Query(value = "SELECT " +
            "   * " +
            "FROM " +
            "   user u " +
            "WHERE " +
            "   user_id = :userId AND user_email = :userEmail ", nativeQuery = true)
    User findPwByIdAndEmail(@Param("userId") String userId, @Param("userEmail") String userEmail);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user " +
            "SET  " +
            "   user_pw = :randomPw," +
            "   user_pw_changed = true " +
            "WHERE " +
            "   user_email = :userEmail ", nativeQuery = true)
    void updatePw(@Param("randomPw") String randomPw, @Param("userEmail") String userEmail);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pay p " +
            "WHERE  " +
            "   p.lesson_no IN ( " +
            "   SELECT l.lesson_no  " +
            "   FROM  " +
            "       lesson l " +
            "       INNER JOIN user u ON u.user_no = l.user_no  " +
            "   WHERE  " +
            "       user_id = :userId) AND p.pay_no = :payNo ", nativeQuery = true)
    int deletePayByChef(@Param("userId") String userId, @Param("payNo") Long payNo);

    @Query(value = "SELECT " +
            "   l.lesson_no, " +
            "   DATE_FORMAT(l.lesson_date, '%Y-%m-%d') as lesson_date, " +
            "   l.lesson_title, " +
            "   l.lesson_oname, " +
            "   l.lesson_sname, " +
            "   (SELECT COUNT(*) FROM lesson_like ll WHERE l.lesson_no = ll.lesson_no) AS lesson_like_cnt " +
            "FROM  " +
            "   lesson l " +
            "WHERE  " +
            "   lesson_no IN (SELECT  " +
            "       lesson_no " +
            "   FROM  " +
            "       lesson_like " +
            "   WHERE  " +
            "       user_no IN (SELECT  " +
            "           user_no  " +
            "       FROM  " +
            "           USER " +
            "       WHERE  " +
            "           user_id = :userId))", nativeQuery = true)
    List<Object[]> findAllLikeLesson(@Param("userId") String userId);

    @Query(value = "SELECT  " +
            "   r.recipe_no, " +
            "   r.recipe_title, " +
            "   r.recipe_sname, " +
            "   r.recipe_oname, " +
            "   DATE_FORMAT(r.recipe_date, '%Y-%m-%d') as recipe_date, " +
            "   (SELECT COUNT(*) FROM recipe_like rl WHERE r.recipe_no = rl.recipe_no) AS recipe_like_cnt, " +
            "   (SELECT COUNT(*) FROM comment c WHERE c.recipe_no = r.recipe_no) AS comment_cnt " +
            "FROM " +
            "   recipe r " +
            "WHERE  " +
            "   r.recipe_no IN ( " +
            "       SELECT " +
            "           recipe_no " +
            "       FROM " +
            "           recipe_like " +
            "       WHERE  " +
            "           user_no IN (SELECT " +
            "               user_no " +
            "           FROM " +
            "               USER " +
            "           WHERE  " +
            "               user_id = :userId))", nativeQuery = true)
    List<Object[]> findAllLikeRecipe(@Param("userId") String userId);
}

