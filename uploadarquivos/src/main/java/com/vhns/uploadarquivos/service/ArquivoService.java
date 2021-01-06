package com.vhns.uploadarquivos.service;

import com.vhns.uploadarquivos.model.DetalhesArquivo;
import com.vhns.uploadarquivos.processarArquivo.FrequenciaPalavras;
import com.vhns.uploadarquivos.processarArquivo.LocalizacaoArmazenamento;
import com.vhns.uploadarquivos.repository.ArquivoRepository;
import com.vhns.uploadarquivos.service.exception.DocumentStorageException;
import com.vhns.uploadarquivos.service.exception.ObjectNotFoundException;

import com.vhns.uploadarquivos.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ArquivoService {

    private final ArquivoRepository _arquivoRepository;

    private final Path fileStorageLocation;

    @Autowired
    public ArquivoService(ArquivoRepository _arquivoRepository, LocalizacaoArmazenamento armazenamento) {
        this._arquivoRepository = _arquivoRepository;
        this.fileStorageLocation = Paths.get(armazenamento.getLocation()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new DocumentStorageException("Não foi possível criar o diretório onde os arquivos carregados serão armazenados.");
        }
    }

    public DetalhesArquivo encontrarArquivo(long id) {
        DetalhesArquivo obj = _arquivoRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + DetalhesArquivo.class.getName());
        }
        return obj;
    }

    public List<DetalhesArquivo> listagemArquivos() {
        return _arquivoRepository.findAll();
    }

    public void salvarInformacoesArquivo(MultipartFile file, String userAgent) throws FileNotFoundException {
        String data = getDataAtual();
        DetalhesArquivo detalhesArquivo = new DetalhesArquivo(file.getOriginalFilename(), file.getContentType(), data, userAgent);
        if(this.salvarArquivo(file)){
            _arquivoRepository.save(detalhesArquivo);
        }else
           throw new StorageException("Erro na tentativa de salvar as informações");

    }

    public List<FrequenciaPalavras> listarFrequenciaPalavras(MultipartFile file) {

        Map<String, Integer> mapPalavras = new HashMap<String, Integer>();
        List<FrequenciaPalavras> listaFrequencia = new ArrayList<>();

        try {
            mapPalavras = contagemFrequenciaPalavras(file);

            for (Map.Entry<String, Integer> entry : mapPalavras.entrySet()) {
                FrequenciaPalavras l = new FrequenciaPalavras(entry.getKey(), entry.getValue());
                listaFrequencia.add(l);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaFrequencia;

    }

    public DetalhesArquivo atualizarArquivo(DetalhesArquivo detalhesArquivo) {
        DetalhesArquivo newDetalhesArquivo = encontrarArquivo(detalhesArquivo.getId());
        updateData(newDetalhesArquivo, detalhesArquivo);
        return _arquivoRepository.save(newDetalhesArquivo);
    }

    public void excluirArquivo(long id) {
        encontrarArquivo(id);
        DetalhesArquivo obj = _arquivoRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + DetalhesArquivo.class.getName());
        }
        _arquivoRepository.deleteById(id);
    }

    private void updateData(DetalhesArquivo newDetalhesArquivo, DetalhesArquivo detalhesArquivo) {
        newDetalhesArquivo.setNome(detalhesArquivo.getNome());
        newDetalhesArquivo.setTipo(detalhesArquivo.getTipo());
        newDetalhesArquivo.setData(detalhesArquivo.getData());
    }

    public boolean salvarArquivo(MultipartFile file) throws FileNotFoundException {

        boolean salvo = false;

        Path arquivoPath = fileStorageLocation.resolve(file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha! Arquivo vazio.");
            }

            file.transferTo(arquivoPath.toFile());
            salvo = true;

            if (!arquivoPath.getParent().equals(this.fileStorageLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException("Não é possível armazenar o arquivo fora do diretório atual.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, arquivoPath,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new StorageException("\n" + "Falha ao armazenar arquivo.", e);
            }

        } catch (IOException e) {
            throw new StorageException("\n" + "Falha ao armazenar arquivo.", e);
        }

        return salvo;

    }


    public String getDataAtual() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    public Page<DetalhesArquivo> encontrarPagina(int page, int linesPages, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPages,Direction.valueOf(direction), orderBy);
        return _arquivoRepository.findAll(pageRequest);
    }

    public Map<String,Integer> contagemFrequenciaPalavras(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        Scanner scanner = new Scanner(inputStream);

        Map<String,Integer> map = new HashMap<String,Integer>();

        while (scanner.hasNext()){

            String linhasDoArquivo = scanner.nextLine();

            String minusculo = linhasDoArquivo.toLowerCase();

            Pattern p = Pattern.compile("(\\d+)|([a-záéíóúçãõôê]+)");
            Matcher m = p.matcher(minusculo);

            while(m.find())
            {
                String token = m.group();
                Integer freq = map.get(token);

                if (freq != null) {
                    map.put(token, freq+1);
                }
                else {
                    map.put(token,1);
                }
            }

        }
        scanner.close();

        return map;
    }

}
