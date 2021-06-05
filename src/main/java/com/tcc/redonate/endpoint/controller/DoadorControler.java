package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.model.Doador;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/listarDoadores")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorControler {
    private final DoadorService doadorService;

}
