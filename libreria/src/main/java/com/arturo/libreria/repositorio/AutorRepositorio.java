/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.repositorio;

import com.arturo.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisa
 */

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
}
