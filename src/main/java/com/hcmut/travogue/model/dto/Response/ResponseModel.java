package com.hcmut.travogue.model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel<T> {
    private boolean isSuccess;
    private T data;
    private List<ErrorDTO> errors;
}
