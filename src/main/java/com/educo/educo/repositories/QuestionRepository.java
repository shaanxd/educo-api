package com.educo.educo.repositories;

import com.educo.educo.entities.Category;
import com.educo.educo.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {
    Page<Question> findByCategory(Category category, Pageable pageable);
}
