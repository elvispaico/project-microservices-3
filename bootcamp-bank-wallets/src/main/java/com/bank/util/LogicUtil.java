package com.bank.util;

import com.bank.model.bean.Operation;

import java.util.List;
import java.util.Optional;

public class LogicUtil {

    public static String generateNumOperation(Optional<List<Operation>> optOperations) {
        return optOperations.map(operations -> {
            if (operations.isEmpty()) {
                return "1000";
            } else {
                var lastOperation = operations.get(operations.size() - 1);
                var lastNumberOperation = Integer.parseInt(lastOperation.getNumOperation());
                var newNumberOperation = lastNumberOperation + 1;
                return String.valueOf(newNumberOperation);
            }
        }).orElse("1000");
    }
}
