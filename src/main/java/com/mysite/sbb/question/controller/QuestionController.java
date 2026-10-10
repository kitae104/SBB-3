package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping("/list")
  public String list(Model model) {
    List<Question> questionList = questionService.getList();
    model.addAttribute("questionList", questionList);
    return "question/list";
  }

  @GetMapping("/detail/{id}")
  public String detail(@PathVariable("id") Long id, Model model,
                       AnswerDto answerDto) { // 답변 등록 기능 시 추가
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question/detail";
  }

  @GetMapping("/create")
  public String createQuestion(QuestionDto questionDto) {
    return "question/inputForm";
  }

  @PostMapping("/create")
  public String createQuestion(@RequestParam(value = "subject") String subject,
                               @RequestParam(value = "content") String content) {


    questionService.create(subject, content);

    return "redirect:/question/list";
  }
//  @PostMapping("/create")
//  public String createQuestion(@Valid QuestionDto questionDto, BindingResult bindingResult) {
//
//    if (bindingResult.hasErrors()) {
//      return "question/inputForm";
//    }
//
//    // 필요시 예외 처리 추가
//    questionService.create(questionDto);
//    return "redirect:/question/list";
//  }

}
