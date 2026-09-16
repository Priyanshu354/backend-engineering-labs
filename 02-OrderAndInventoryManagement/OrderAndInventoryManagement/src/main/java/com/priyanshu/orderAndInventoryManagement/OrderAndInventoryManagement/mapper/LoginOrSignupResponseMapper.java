package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.LoginResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginOrSignupResponseMapper {

    LoginResponse LoginORSignupRequestToLoginResponse(User user, String accessToken);
}
