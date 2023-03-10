package com.example.user.controller;

import com.example.user.config.paging.PagingParam;
import com.example.user.dto.ResponseDto;
import com.example.user.dto.ResponseTableDto;
import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("backend/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("list")
    public String list(@PagingParam(path = "user") ResponseTableDto responseTableDto) {
        userService.list(responseTableDto);
        return "/user/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "user/create";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.detail(id));
        return "user/detail";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.detail(id));
        return "user/edit";
    }

    @PostMapping(value = "save",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(@Valid UserDto userDto, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes)
            throws SQLException {
        if (bindingResult.hasErrors()) {
            return "user/create";
        } else if (!userDto.getPassword().equalsIgnoreCase(userDto.getRePassword())) {
            bindingResult.rejectValue("rePassword", "error.userDto", "M???t kh???u kh??ng kh???p");
            return "user/create";
        }
        ResponseDto dto = userService.save(userDto);
        redirectAttributes.addFlashAttribute("message", dto.getMessage());
        return "redirect:/backend/user/list";

//        return "/jsp/user/create.jsp";
    }

    @PostMapping(value = "update",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(UserDto userDto, RedirectAttributes redirectAttributes)
            throws SQLException {
        ResponseDto dto = userService.save(userDto);
        redirectAttributes.addFlashAttribute("message", dto.getMessage());
        return "redirect:/backend/user/list";

//        return "/jsp/user/create.jsp";
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id)
            throws SQLException {
        return userService.delete(id);

//        return "/jsp/user/create.jsp";
    }
    // ????? validate b???ng spring validation th??:
    // B?????c 1: th??m th?? vi???n maven spring-boot-starter-validation
    // B?????c 2: Custom Dto ?????nh ngh??a, rule ????? validate v?? message
    // B?????c 3: Th??m @Valid v?? @ModelAttribute tr??n controller
    // th??m BindingResult ngay sau dto
    // B?????c 4: S??? d???ng taglib form c???a spring
    // B??i t???p: T???o m???i t??i kho???n: n???u ???? t???n t???i t??i kho???n c?? tr??ng email(username) th?? s??? th??ng b??o l???i ra m??n h??nh

}
