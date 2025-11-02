package com.company.hr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryIssue {

    private Long issueId;

    private String issueCode;
    private Integer issueYear;
    private Integer issueMonth;
    private LocalDate issueDate;
    private Long orgId;
    private Integer orgLevel;
    private Double totalBaseAmount;
    private Double totalBonusAmount;
    private Double totalDeductionAmount;
    private Double totalAmount;
    private Integer totalPeople;
    private Boolean containsOperatorData;
    private Boolean requiresSpecialApproval;
    private String status; // draft, pending_review, reviewed, paid, rejected

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Long createdBy;
    private Long reviewedBy;
    private LocalDateTime reviewedTime;
    private String reviewComment;
    private LocalDateTime paidTime;
    private String paymentRef;

    private String orgName;

    private String createdByName;

    private String reviewedByName;
}