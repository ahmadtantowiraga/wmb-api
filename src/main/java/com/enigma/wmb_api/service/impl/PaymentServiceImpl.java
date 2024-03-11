package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.payment_request.PaymentDetailRequest;
import com.enigma.wmb_api.dto.request.payment_request.PaymentItemDetailRequest;
import com.enigma.wmb_api.dto.request.payment_request.PaymentRequest;
import com.enigma.wmb_api.entity.Payment;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.repository.PaymentRepository;
import com.enigma.wmb_api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRespository;
    private final RestClient restClient;
    private final String SECRET_KEY;
    private final String BASE_URL_SNAP;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRespository, RestClient restClient,
                              @Value("${midtrans.api.key}") String secretKey,
                              @Value("${midtrans.api.snap-url}") String baseUrlSnap) {
        this.paymentRespository = paymentRespository;
        this.restClient = restClient;
        SECRET_KEY = secretKey;
        BASE_URL_SNAP = baseUrlSnap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment createPayment(Transaction transaction) {
        long amount=transaction.getTransactionDetail().stream()
                .mapToLong(value -> (value.getPrice()* value.getQty()))
                .reduce(0, Long::sum);

        List<PaymentItemDetailRequest> itemDetailRequestList=transaction.getTransactionDetail()
                .stream().map(transactionDetail -> PaymentItemDetailRequest.builder()
                        .name(transactionDetail.getMenu().getMenuName())
                        .price(transactionDetail.getMenu().getPrice())
                        .quantity(transactionDetail.getQty())
                        .build()).toList();


        PaymentRequest request=PaymentRequest.builder()
                .paymentDetail(PaymentDetailRequest.builder()
                        .orderId(transaction.getId())
                        .amount(amount)
                        .build())
                .paymentItemDetails(itemDetailRequestList)
                .paymentMethod(List.of("shopeepay", "gopay"))
                .build();
        ResponseEntity<Map<String, String>> response = restClient.post().uri(BASE_URL_SNAP)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + SECRET_KEY)
                .retrieve().toEntity(new ParameterizedTypeReference<>() {
                });
        Map<String,String> body=response.getBody();

        Payment payment=Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus("ordered")
                .build();
        paymentRespository.saveAndFlush(payment);
        return payment;
    }
}
