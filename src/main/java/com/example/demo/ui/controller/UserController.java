package com.example.demo.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import com.example.demo.ui.model.request.UserDetailsRequestModel;
import com.example.demo.ui.model.response.OperationStatusModel;
import com.example.demo.ui.model.response.RequestOperationStatus;
import com.example.demo.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        // URL内に指定されているRequestParamを取得し、すべてのユーザーを条件を用いて取得し返す
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public UserRest getUser(@PathVariable("id") String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, // リクエストヘッダのContent-Typeをマッピング条件として絞り込み、リクエストから受け取るContent-Typeを指定
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } // リクエストヘッダのAcceptをマッピング条件として絞り込む+返す形式の(レスポンスのMediaTypeを)指定
    )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        // @RequestBody => POST送信のbodyと指定したクラスをマッピングしJavaオブジェクトに変換する
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public UserRest updateUser(@PathVariable("id") String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel deleteUser(@PathVariable("id") String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name()); // このメソッドを呼ぶことで使用する機能名を格納

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }
}
