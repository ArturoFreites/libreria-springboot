/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.controllers;

import com.arturo.libreria.entidades.Autor;
import com.arturo.libreria.entidades.Editorial;
import com.arturo.libreria.entidades.Libro;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.services.AutorService;
import com.arturo.libreria.services.EditorialService;
import com.arturo.libreria.services.LibroService;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn,
            @RequestParam() String titulo,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam() String idAutor, @RequestParam() String idEditorial,
            ModelMap modelo) {

        try
        {
            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cargado exitosamente!");
            return "index.html";
        } catch (MyException ex)
        {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Libro> libros = libroService.listarLibros();
        modelo.addAttribute("libros", libros);

        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroService.getOne(isbn));
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {

        try
        {
            libroService.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:../lista";
        } catch (MyException ex)
        {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
        
            modelo.addAttribute("autores",autores);
            modelo.addAttribute("editoriales",editoriales);
            
            modelo.put("error", ex.getMessage());
            return "libro_modificar.html";
        }
    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, ModelMap modelo) {
        modelo.put("libro", libroService.getOne(isbn));
        return "libro_eliminar";
    }

    @PostMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        libroService.eliminarLibro(isbn);
        return "redirect:../lista";
    }

}
