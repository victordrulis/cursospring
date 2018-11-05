package com.victor.cursospring.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CategoriaResource {
    @RequestMapping(method=RequestMethod.GET)
    public String listar() {
        return "REST está funcionando e APS.NET Core continua >>> Java Spring";
    }
}
