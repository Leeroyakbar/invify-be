package com.invify.services.transaction;

import com.invify.dto.invitation.InvitationRequestDTO;
import com.invify.entities.Invitation;
import com.invify.entities.InvitationMedia;
import com.invify.entities.InvitationTransaction;
import com.invify.entities.User;
import com.invify.enums.SubscriptionPlan;
import com.invify.repositories.InvitationTransactionRepository;
import com.invify.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationTransactionService {


    private final InvitationTransactionRepository transactionRepository;
    private final UserService userService;

    public InvitationTransaction createTransaction(Invitation invitation, InvitationRequestDTO requestDTO) {
        User user = userService.getUserById(requestDTO.getUserId());

        return InvitationTransaction.builder()
                .trxId(UUID.randomUUID())
                .invitation(invitation)
                .trxNo(getTrxNoSequence())
                .user(user)
                .subscriptionPlan(SubscriptionPlan.valueOf(requestDTO.getSubscriptionPlan()))
                .trxAmount(requestDTO.getTrxAmount())
                .activeStatus(1)
                .createdDate(LocalDateTime.now())
                .build();
    }
    
    public InvitationTransaction editTransaction(Invitation invitation, InvitationRequestDTO requestDTO) {
        InvitationTransaction invitationTransaction = transactionRepository.findByInvitation_InvitationId(invitation.getInvitationId());

        invitationTransaction.setSubscriptionPlan(SubscriptionPlan.valueOf(requestDTO.getSubscriptionPlan()));
        invitationTransaction.setTrxAmount(requestDTO.getTrxAmount());
        invitationTransaction.setUpdatedDate(LocalDateTime.now());

        return invitationTransaction;
    }


    public void saveTransaction(InvitationTransaction transaction){
        transactionRepository.save(transaction);
    }


    public String getTrxNoSequence() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long seqValue = transactionRepository.getOrderNoSequence();
        String formattedSeq = String.format("%010d", seqValue);

        return "TRX-" + datePart + formattedSeq;
    }
}
