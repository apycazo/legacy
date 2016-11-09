package com.github.apycazo.pylon.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class SecuredController
{
    @GetMapping("secured/api")
    public String securedPath ()
    {
        return "secured:"+System.currentTimeMillis();
    }

    @GetMapping("public/api")
    public String publicPath ()
    {
        return "public:"+System.currentTimeMillis();
    }
}
