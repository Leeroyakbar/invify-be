package com.invify.utils;

import com.invify.dto.APIResponseDTO;
import com.invify.dto.APIResponsePageDTO;
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

    public static <T> APIResponsePageDTO success(T data, String message, int page, long totalItems, int totalPages) {
        return APIResponsePageDTO.builder()
                .success(true)
                .data(data)
                .message(message)
                .error(null)
                .page(page)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .last(totalItems > page * 10L)
                .build();
    }
}
