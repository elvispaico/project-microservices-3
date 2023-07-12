package com.bank.util;

import com.bank.enums.TypeCustomer;
import com.bank.enums.TypeService;
import com.bank.model.bean.Debt;
import com.bank.model.entity.Product;

import java.util.Objects;

public class BussinessLogic {

    public static boolean isCustomerPersonalVip(String codTypeCustomer, String codPerfil) {
        if (codTypeCustomer == null || codPerfil == null) return false;
        return codTypeCustomer.equals("01") && codPerfil.equals("02");
    }

    public static boolean isCustomerEmpresarialPyme(String codTypeCustomer, String codPerfil){
        if (codTypeCustomer == null || codPerfil == null) return false;
        return codTypeCustomer.equals(TypeCustomer.BUSSINESS.getValue())
                && codPerfil.equals("02");
    }

    public static boolean isServiceAccountSaving(String codService) {
        return codService.equals(TypeService.SAVING.getValue());
    }

    public static boolean isServiceAccountCurrent(String codService) {
        return codService.equals(TypeService.CURRENT.getValue());
    }

    public static boolean flProductHaveExpiredDebts(Product product){
        return !Objects.isNull(product.getDebts()) && product.getDebts().stream()
                .anyMatch(Debt::isExpired);
    }
}
