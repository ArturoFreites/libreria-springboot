/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturo.libreria.repositorio;

import com.arturo.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisa
 */

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long>{
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo")String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor (@Param("nombre")String nombre);
}
