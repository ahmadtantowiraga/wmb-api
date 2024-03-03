package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.entity.TransactionType;

public interface TransactionTypeService {
    TransactionType findById(String id);
    TransactionType update();

}
