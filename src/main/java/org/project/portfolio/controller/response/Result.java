package org.project.portfolio.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
  private String resultCode;
  private T resultData;
  
  public static Result OK() {
    return Result.builder()
            .resultCode("SUCCESS")
            .resultData(null)
            .build();
  }
  
  public static <T> Result OK(T data) {
    return Result.builder()
            .resultCode("SUCCESS")
            .resultData(data)
            .build();
  }
  
  public static Result ERROR(String errorCode) {
    return Result.builder()
            .resultCode(errorCode)
            .resultData(null)
            .build();
  }
}
