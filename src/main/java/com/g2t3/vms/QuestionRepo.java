package com.g2t3.vms;

import com.g2t3.vms.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.*;

public interface QuestionRepo extends MongoRepository<Question, String> {
    
    @Query("{}")
    ArrayList<Question> findAllQuestions();
}
