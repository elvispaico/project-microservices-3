package com.bank.service.mapper;

import com.bank.model.entity.Wallet;
import com.bank.model.request.WalletSaveRequest;

public class WalletMapper {

    public static Wallet mapWalletSaveRequestToWallet(WalletSaveRequest request) {
        return Wallet.builder()
                .numDocument(request.getNumDocument())
                .numCellphone(request.getNumCellphone())
                .imei(request.getImei())
                .email(request.getEmail())
                .build();
    }
}
