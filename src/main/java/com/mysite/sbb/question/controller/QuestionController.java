package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

  private final QuestionService questionService;

//  @GetMapping("/list") - 중복되면 안됨
  public String list(Model model) {
    List<Question> questionList = questionService.getList();
    model.addAttribute("questionList", questionList);
    return "question/list";
  }

  // 페이지 기능 추가
  @GetMapping("/list")
  public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
    log.info("==== page : {}", page);
    Page<Question> paging = questionService.getList(page);
    model.addAttribute("paging", paging);
    log.info("==== paging : {}", paging);
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

  // @PostMapping("/create")
  public String createQuestion(@RequestParam(value = "subject") String subject,
                               @RequestParam(value = "content") String content) {


    questionService.create(subject, content);

    return "redirect:/question/list";
  }

  // Validation 적용
  @PostMapping("/create")
  public String createQuestion(@Valid QuestionDto questionDto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "question/inputForm";
    }

    // 필요시 예외 처리 추가
    questionService.create(questionDto);
    return "redirect:/question/list";
  }

}
