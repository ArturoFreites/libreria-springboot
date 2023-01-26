/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.controllers;

import com.arturo.libreria.entidades.Autor;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.services.AutorService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author luisa
 */
@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    AutorService autorService;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            autorService.crearAutor(nombre);
            modelo.put("exito","El autor fue cargada exitosamente!");
            return "index.html";
            
        } catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Autor> autores = autorService.listarAutores();
        modelo.addAttribute("autores", autores);
        
        return "autor_list.html";
        
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorService.getOne(id));
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre ,ModelMap modelo){
        try
        {
            autorService.modificarAutor(nombre,id);
            
            return "redirect:../lista";
        } catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorService.getOne(id));
        return "autor_eliminar.html";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id,String nombre, ModelMap modelo){
        autorService.eliminarAutor(id,nombre);
        return "redirect:../lista";
    }
    
}
