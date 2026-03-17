package com.invify.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageableRequest {
    private int page;
    private int size;
}
