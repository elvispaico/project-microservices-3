package com.bank.mapper;

import com.bank.model.bean.ParameterDetail;
import com.bank.model.bean.Profile;
import com.bank.model.entity.Customer;
import com.bank.openapi.model.dto.CustomerResponse;
import com.bank.openapi.model.dto.CustomerSaveRequest;
import com.bank.openapi.model.dto.ProfileCustomer;
import com.bank.util.ParametrosDataDummy;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerResponse mapCustomerToCustomerResponse(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setNumDocument(customer.getNumDocument());
        customerResponse.setCodTypeCustomer(customer.getCodTypeCustomer());
        customerResponse.setDesTypeCustomer(customer.getDesTypeCustomer());

        ProfileCustomer profileCustomer = new ProfileCustomer();
        profileCustomer.setCodProfile(customer.getProfile().getCodPerfil());
        profileCustomer.setDesProfile(customer.getProfile().getDesPerfil());

        customerResponse.setProfile(profileCustomer);
        return customerResponse;
    }

    public static Customer mapRequestToEntity(CustomerSaveRequest request) {

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setNumDocument(request.getNumDocument());
        customer.setCodTypeCustomer(request.getCodTypeCustomer());
        customer.setDesTypeCustomer(ParametrosDataDummy.getData().get("01")
                .stream()
                .filter(param -> param.getCode().equals(request.getCodTypeCustomer()))
                .map(ParameterDetail::getDescription)
                .findFirst()
                .orElse(""));
        customer.setProfile(Profile.builder()
                .codPerfil(request.getProfile().getCodProfile())
                .desPerfil(request.getProfile().getDesProfile())
                .build());
        return customer;

    }


    public static List<CustomerResponse> mapListCustomerToLisCustomerResponse(List<Customer> listCustomers) {
        return listCustomers
                .stream()
                .map(CustomerMapper::mapCustomerToCustomerResponse)
                .collect(Collectors.toList());

    }

}
