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
            bindingResult.rejectValue("rePassword", "error.userDto", "Mật khẩu không khớp");
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
    // để validate bằng spring validation thì:
    // Bước 1: thêm thư viện maven spring-boot-starter-validation
    // Bước 2: Custom Dto định nghĩa, rule để validate và message
    // Bước 3: Thêm @Valid và @ModelAttribute trên controller
    // thêm BindingResult ngay sau dto
    // Bước 4: Sử dụng taglib form của spring
    // Bài tập: Tạo mới tài khoản: nếu đã tồn tại tài khoản có trùng email(username) thì sẽ thông báo lỗi ra màn hình

}
