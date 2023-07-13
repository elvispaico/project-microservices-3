package com.bank.service;

import com.bank.enums.TypeOperation;
import com.bank.exception.AttributeException;
import com.bank.model.entity.Wallet;
import com.bank.model.request.OperationRequest;
import com.bank.model.request.WalletSaveRequest;
import com.bank.repository.WalletRepository;
import com.bank.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;


    @Test
    @DisplayName("Crear un nuevo monedero con exito")
    public void createWallet(){

        WalletSaveRequest request = new WalletSaveRequest();
        request.setNumCellphone("22446688");
        request.setNumDocument("987654321");
        request.setImei("1234");
        request.setEmail("elvis@gmail.om");

        Mockito.when(walletRepository.findByNumDocumentOrNumCellphone(request.getNumDocument(), request.getNumCellphone()))
                .thenReturn(Mono.empty());

        Wallet walletSave = new Wallet();
        walletSave.setId("12121212121");
        walletSave.setNumCellphone(request.getNumCellphone());
        walletSave.setNumDocument(request.getNumDocument());
        walletSave.setImei(request.getImei());
        walletSave.setEmail(request.getEmail());
        walletSave.setBalance(0.0);

        Mockito.when(walletRepository.save(Mockito.any(Wallet.class)))
                .thenReturn(Mono.just(walletSave));

        Mono<Wallet> result = walletService.createWallet(request);

        StepVerifier.create(result)
                .expectNext(walletSave)
                .verifyComplete();

        Mockito.verify(walletRepository, Mockito.times(1))
                .findByNumDocumentOrNumCellphone(request.getNumDocument(), request.getNumCellphone());

        Mockito.verify(walletRepository,Mockito.times(1))
                .save(Mockito.any(Wallet.class));
    }

    @Test
    @DisplayName("Crear un nueva operacion con exito")
    public void createOperation(){
        String numCellphone = "987654321";
        OperationRequest request = new OperationRequest();
        request.setCodTypeOperation(TypeOperation.PAY.getValue());
        request.setAmount(200.0);

        Wallet wallet = new Wallet();
        wallet.setNumCellphone(numCellphone);
        wallet.setBalance(100.0);

        Mockito.when(walletRepository.findByNumCellphone(numCellphone)).thenReturn(Mono.just(wallet));

        Mono<Wallet> result = walletService.saveOperation(numCellphone, request);

        StepVerifier.create(result)
                .expectError(AttributeException.class)
                .verify();

        Mockito.verify(walletRepository, Mockito.times(1)).findByNumCellphone(numCellphone);
        Mockito.verify(walletRepository, Mockito.never()).save(Mockito.any(Wallet.class));
//        verify(kafkaProducer, never()).send(anyString());
    }

}
