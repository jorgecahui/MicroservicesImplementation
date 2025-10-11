package pe.edu.upeu.msproducto.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upeu.msproducto.dto.CategoriaDto;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoria")
    CategoriaDto buscarPorId(@PathVariable("id") Integer id);

    default CategoriaDto fallbackCategoria(Integer id, Throwable e) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(0);
        categoriaDto.setNombre("Servicio de Categoría no disponible");
        categoriaDto.setDescripcion("No se pudo obtener la información de la categoría con id " + id);
        return categoriaDto;
    }

}
