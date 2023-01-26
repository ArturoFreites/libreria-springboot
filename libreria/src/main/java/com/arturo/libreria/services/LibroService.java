/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.services;

import com.arturo.libreria.entidades.Autor;
import com.arturo.libreria.entidades.Editorial;
import com.arturo.libreria.entidades.Libro;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.repositorio.AutorRepositorio;
import com.arturo.libreria.repositorio.EditorialRepositorio;
import com.arturo.libreria.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisa
 */
@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException {
        
        validarDatos(isbn,titulo,ejemplares,idAutor,idEditorial);
        
        Libro libro = new Libro();
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);

        libro.setAlta(new Date());

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList();

        libros = libroRepositorio.findAll();

        return libros;
    }

    
    @Transactional
    public void modificarLibro(Long isbn, String titulo,Integer ejemplares, String idAutor, String idEditorial) throws MyException {
        
        validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if (respuestaLibro.isPresent())
            {
                Libro libro = respuestaLibro.get();

                libro.setTitulo(titulo);
                libro.setEjemplares(ejemplares);
                libro.setAutor(autor);
                libro.setEditorial(editorial);
                
                libroRepositorio.save(libro);
            }

    }
    
    @Transactional
    public void eliminarLibro(Long isbn){
        Optional <Libro> respuesta = libroRepositorio.findById(isbn);
        if (respuesta.isPresent())
        {
            Libro libro = respuesta.get();
            libroRepositorio.delete(libro);
        }
    }

    private void validarDatos(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MyException {
        
        if (isbn == null)
        {
            throw new MyException("El isbn no puede ser nulo!");
        }
        
        if (titulo.isEmpty() || titulo==null)
        {
            throw new MyException("El titulo no puede ser nulo o estar vacio!");
        }
        
        if (ejemplares == null)
        {
            throw new MyException("Ejemplares no puede ser nulo!");
        }
        
        if (idAutor.isEmpty() ||idAutor == null)
        {
            throw new MyException("El ID del autor no puede ser nulo o estar vacio!");
        }
        
        if (idEditorial.isEmpty() ||idEditorial == null)
        {
            throw new MyException("El ID de la editorial no puede ser nulo o estar vacio!");
        }
        
    }
    
    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }

}
