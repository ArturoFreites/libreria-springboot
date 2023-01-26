/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.services;

import com.arturo.libreria.entidades.Cliente;
import com.arturo.libreria.exceptions.MyException;
import com.arturo.libreria.repositorio.ClienteRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisa
 */

@Service
public class ClienteService {
    
    
    @Autowired
    ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public void crearCliente(Long documento, String nombre,String apellido,String telefono) throws MyException{
        validar(documento, nombre, apellido, telefono);
        Cliente cliente = new Cliente();
        
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        clienteRepositorio.save(cliente);
    }
    
    @Transactional
    public void modificarCliente(String id, Long documento, String nombre,String apellido,String telefono) throws MyException{
        Optional <Cliente> respuesta = clienteRepositorio.findById(id);
        validar(documento, nombre, apellido, telefono);
        if (respuesta.isPresent())
        {
            Cliente cliente = respuesta.get();
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            
            clienteRepositorio.save(cliente);
        }
    }
    
    public Cliente getOne(String id){
        return clienteRepositorio.getOne(id);
    }
    
    
    private void validar(Long documento, String nombre,String apellido,String telefono) throws MyException {
        if (documento==null){
            throw new MyException("El documento no puede ser nulo!");
        }
        if (nombre.isEmpty() || nombre==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
        if (apellido.isEmpty() || apellido==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
        if (telefono.isEmpty() || telefono==null){
            throw new MyException("El nombre no puede ser nulo!");
        }
        
    }
    
}
