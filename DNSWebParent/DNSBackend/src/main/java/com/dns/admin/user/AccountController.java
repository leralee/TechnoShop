package com.dns.admin.user;

import com.dns.admin.FileUploadedUtil;
import com.dns.admin.security.DnsUserDetails;
import com.dns.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author valeriali on {21.01.2024}
 * @project TechnoShopProject
 */

@Controller
public class AccountController {

    @Autowired
    private UserService service;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal DnsUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = service.getByEmail(email);
        model.addAttribute("user", user);
        return "account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal DnsUserDetails loggedUser,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("Имя файла не может быть null");
            }
            String fileName = StringUtils.cleanPath(originalFilename);
            user.setPhotos(fileName);

            User savedUser = service.updateAccount(user);
            String uploadDir = "user-photos/" + savedUser.getId();

            FileUploadedUtil.cleanDir(uploadDir);
            FileUploadedUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            service.updateAccount(user);
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Ваши учетные данные обновлены.");
        return "redirect:/account";
    }
}
