/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.services;

import com.arturo.libreria.entidades.Autor;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.repositorio.AutorRepositorio;
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
public class AutorService {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MyException{
        
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }
    
    @Transactional
    public void modificarAutor(String nombre, String id) throws MyException{
        
        Optional <Autor> respuesta = autorRepositorio.findById(id);
        validar(nombre);
        if (respuesta.isPresent())
        {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        }
    }
    
    @Transactional
    public void eliminarAutor(String id, String nombre){
        Optional <Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent())
        {
            Autor autor = respuesta.get();
            autorRepositorio.delete(autor);
        }
        
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }

    private void validar(String nombre) throws MyException {
        if (nombre.isEmpty() || nombre==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
    }
    
    public List<Autor> listarAutores(){
        
        return autorRepositorio.findAll();
    }
    
    
}
