package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTest {

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private QuestionService questionService;

  @Test
  void testSave(){
    Question question = new Question();
    question.setSubject("sbb가 무엇인가요?");
    question.setContent("sbb에 대해서 알고 싶습니다.");
    System.out.println("question = " + question);

    Question savedQuestion = questionRepository.save(question);
    System.out.println("savedQuestion = " + savedQuestion);

//    Question question2 = new Question("test subject", "test content");
    Question question2 = Question.builder()
        .subject("test subject")
        .content("test content")
        .build();
    System.out.println("question2 = " + question2);
    Question savedQuestion2 = questionRepository.save(question2);
    System.out.println("savedQuestion2 = " + savedQuestion2);
    assertNotNull(savedQuestion2.getId());
    assertEquals("sbb가 무엇인가요?", savedQuestion2.getSubject());
  }

  @Transactional
  @Test
  void testFindAll() {
    List<Question> questionList = questionRepository.findAll();
    System.out.println("questionList = " + questionList);
    assertEquals(2, questionList.size());

    Question question = questionList.get(0);
    assertEquals("sbb가 무엇인가요?", question.getSubject());

  }

  @Test
  void testMakeData() {
    for (int i = 1; i <= 300; i++) {
        String subject = String.format("테스트 데이터입니다:[%03d]", i);
        String content = "내용무";
        QuestionDto question = QuestionDto.builder()
            .subject(subject)
            .content(content)
            .build();
        questionService.create(question);
    }
  }
}