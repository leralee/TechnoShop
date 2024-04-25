package com.dns.admin.brand;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Бренд не найден")
public class BrandNotFoundRestException extends Exception {

}
