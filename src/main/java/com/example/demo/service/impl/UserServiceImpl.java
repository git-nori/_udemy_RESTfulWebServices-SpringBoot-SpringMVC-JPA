package com.example.demo.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        // email(unique)を条件としてレコードの取得ができた場合、例外をスローする
        if (repository.findUserByEmail(user.getEmail()) != null)
            throw new RuntimeException("record is allready exists, email => " + user.getEmail());

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity); // UserDtoからUserEntityへ(一致する)プロパティをコピーする
        String publicUserId = utils.generateUserId(30); // 30文字のuserIdを生成
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserEntity = repository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = repository.findUserByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
         * 認証をするUserをDBから取得し
         * email, password, roleをconstructorの引数に渡して生成したUserオブジェクトのインスタンスを返す
         */
        UserEntity userEntity = repository.findUserByEmail(email); // emailをキーにuserEntityを取得
        if (userEntity == null)
            throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

}
