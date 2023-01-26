/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.controllers;

import com.arturo.libreria.entidades.Editorial;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.services.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    EditorialService editorialService;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        
        try
        {
            editorialService.crearEditorial(nombre);
            modelo.put("exito","La editorial fue cargada exitosamente!");
            return "index.html";
        } catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Editorial> editoriales = editorialService.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialService.getOne(id));
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre ,ModelMap modelo){
        try
        {
            editorialService.modificarEditorial(nombre,id);
            
            return "redirect:../lista";
        } catch (MyException ex)
        {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id,ModelMap modelo){
        modelo.put("editorial",editorialService.getOne(id));
        return "editorial_eliminar";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, String nombre,ModelMap modelo){
        editorialService.eliminarEditorial(id, nombre);
        return "redirect:../lista";
    }
    
}
