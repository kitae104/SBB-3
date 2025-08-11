package com.mysite.sbb.answer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerFormDto {

  @NotEmpty(message = "내용은 필수 항목입니다.")
  private String content;
}
