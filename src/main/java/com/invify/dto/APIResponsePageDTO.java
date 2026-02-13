package com.invify.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponsePageDTO {

    private boolean success;
    private String message;
    private String error;
    private Object data;
    private int page;
    private long totalItems;
    private int totalPages;
    private boolean last;

}
