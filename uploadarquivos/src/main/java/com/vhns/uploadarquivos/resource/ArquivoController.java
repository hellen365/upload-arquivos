package com.vhns.uploadarquivos.resource;

import com.vhns.uploadarquivos.model.DetalhesArquivo;
import com.vhns.uploadarquivos.service.ArquivoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ArquivoController {

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService){
        this.arquivoService = arquivoService;
    }

    @GetMapping(value = "/arquivo/{id}")
    public ResponseEntity<DetalhesArquivo> encontrarArquivo(@PathVariable(value = "id") long id){
        DetalhesArquivo obj  = arquivoService.encontrarArquivo(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/arquivos")
    public ResponseEntity<List<DetalhesArquivo>> listagemArquivos(){
        List<DetalhesArquivo> list  = arquivoService.listagemArquivos();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/arquivo")
    public ResponseEntity<?>
    salvarInformacoesArquivo (@RequestParam MultipartFile file,
                              @RequestHeader(value="Usuario-Agent") String userAgent) throws FileNotFoundException {
        arquivoService.salvarInformacoesArquivo(file,userAgent);
        return ResponseEntity.status(201).body(arquivoService.listarFrequenciaPalavras(file));
    }

    @PutMapping(value = "/arquivo/{id}")
    public ResponseEntity<Void> atualizarAruivo(@RequestBody DetalhesArquivo detalhesArquivo, @PathVariable long id){
        arquivoService.atualizarArquivo(detalhesArquivo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/arquivo/{id}")
    public ResponseEntity<Void> excluirArquivo(@PathVariable(value = "id") long id){
        arquivoService.excluirArquivo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/arquivos/page")
    public ResponseEntity<Page<DetalhesArquivo>> encontrarPagina(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "linesPages", defaultValue = "24") int linesPages,
                                                                 @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                                 @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<DetalhesArquivo> list  = arquivoService.encontrarPagina(page, linesPages, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
