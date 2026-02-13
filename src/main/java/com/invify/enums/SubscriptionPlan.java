package com.invify.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum SubscriptionPlan {
    BASIC,
    PREMIUM,
    CUSTOM
}
