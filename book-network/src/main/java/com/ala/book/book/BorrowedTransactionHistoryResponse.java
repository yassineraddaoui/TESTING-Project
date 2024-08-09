package com.ala.book.History;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowedTransactionHistoryResponse {
    private boolean returned;
    private boolean returnApproved;
}
