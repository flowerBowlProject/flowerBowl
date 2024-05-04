package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.LessonFile;
import com.flowerbowl.web.domain.RecipeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonFileRepository extends JpaRepository<LessonFile, Long> {
    List<LessonFile> findLessonFilesByLesson_LessonNo(Long lesson_no);

    Boolean existsLessonFileByLessonFileOname(String fileOname);
    void deleteLessonFileByLessonFileOname(String fileOname);
}
