package com.invify.utils;

import com.invify.dto.APIResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ResponseAPIUtil {

    public static <T> APIResponseDTO success(T data, String message) {
        return APIResponseDTO.builder()
                .success(true)
                .data(data)
                .message(message)
                .error(null).build();
    }
}
