/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.services;

import com.arturo.libreria.entidades.Prestamo;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoService {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    public void crearPrestamo(String isbnLibro, String idCLiente) throws MyException{
        validar(isbnLibro,idCLiente);
        
        Prestamo prestamo = new Prestamo();
    }
    
    private void validar(String isbnLibro, String idCLiente) throws MyException {
        
        if (isbnLibro.isEmpty() || isbnLibro==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
        if (idCLiente.isEmpty() || idCLiente==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
        
    }
    
}
