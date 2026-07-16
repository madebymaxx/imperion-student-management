package com.maxx.imperion.repository;

import com.maxx.imperion.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    List<Student> findByDeletedIsFalse();

    Boolean existsByEmail(String emailId);

    Optional<Student> findByIdAndDeletedIsTrue(Long id);

    List<Student> findByNameContainingIgnoreCaseAndDeletedIsFalse(String name);

    List<Student> findByEmailContainingIgnoreCaseAndDeletedIsFalse(String email);

    List<Student> findByCourseContainingIgnoreCaseAndDeletedIsFalse(String course);

    long countByDeletedIsFalse();

    long countByCourseIgnoreCaseAndDeletedIsFalse(String course);

    long countByAgeAndDeletedIsFalse(int age);

}
