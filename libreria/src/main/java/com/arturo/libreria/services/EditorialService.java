/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.services;

import com.arturo.libreria.entidades.Editorial;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.repositorio.EditorialRepositorio;
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
public class EditorialService {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MyException{
        validar(nombre);
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
    
    private void validar(String nombre) throws MyException {
        if (nombre.isEmpty() || nombre==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
    }
    
    public List<Editorial> listarEditoriales(){
        
        return editorialRepositorio.findAll();
    }
    
    @Transactional
    public void eliminarEditorial(String id, String nombre){
        Optional <Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent())
        {
            Editorial editorial = respuesta.get();
            editorialRepositorio.delete(editorial);
        }
        
    }
    
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
    
    @Transactional
    public void modificarEditorial(String nombre, String id) throws MyException{
        
        Optional <Editorial> respuesta = editorialRepositorio.findById(id);
        validar(nombre);
        if (respuesta.isPresent())
        {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
        }
    }
    
}
