package com.example.demo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.io.entity.UserEntity;
import com.example.demo.io.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {
        // email(unique)を条件としてレコードの取得ができた場合、例外をスローする
        if (repository.findUserByEmail(user.getEmail()) != null)
            throw new RuntimeException("record is allready exists, email => " + user.getEmail());

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity); // UserDtoからUserEntityへ(一致する)プロパティをコピーする
        String publicUserId = utils.generateUserId(30); // 30文字のuserIdを生成
        userEntity.setEncryptedPassword(publicUserId);
        userEntity.setUserId("testUserId");

        UserEntity storedUserEntity = repository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, returnValue);

        return returnValue;
    }

}
