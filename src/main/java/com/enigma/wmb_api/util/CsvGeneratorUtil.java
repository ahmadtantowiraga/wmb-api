package com.enigma.wmb_api.util;

import com.enigma.wmb_api.dto.response.TransactionResponse;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvGeneratorUtil {
    private static final String CSV_HEADER="TransactionId, Date, CustomerId, TableId, TransactionTypeId, TransactionDetailId, MenuId, qty, price, PaymentStatus\n";

    public String generateCsv(List<Transaction> transactionList){
        StringBuilder csvContent=new StringBuilder();
        csvContent.append(CSV_HEADER);
        for (Transaction transaction: transactionList){
            for (TransactionDetail transactionDetail: transaction.getTransactionDetail()){
                String paymentStatus=transaction.getPayment()==null ? null : transaction.getPayment().getTransactionStatus();
                String tableId=transaction.getTables()==null ? null : transaction.getTables().getId();

                csvContent.append(transaction.getId()).append(",")
                        .append(transaction.getDate()).append(",")
                        .append(transaction.getCustomer().getId()).append(",")
                        .append(tableId).append(",")
                        .append(transaction.getTransactionType().getId()).append(",")
                        .append(transactionDetail.getId()).append(",")
                        .append(transactionDetail.getMenu().getId()).append(",")
                        .append(transactionDetail.getQty()).append(",")
                        .append(transactionDetail.getPrice()).append(",")
                        .append(paymentStatus).append("\n");
            }
        }
        return csvContent.toString();
    }
}
