package com.api.gastos.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.api.gastos.model.Deputado;
import com.api.gastos.model.Despesa;
import com.api.gastos.repository.DeputadoRepository;
import com.api.gastos.repository.DespesaRepository;
import com.api.gastos.representation.DeputadoDespesaCsvRepresentation;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

@Service
public class CsvService {

    private final DeputadoRepository deputadoRepository;
    private final DespesaRepository despesaRepository;

    public CsvService(DeputadoRepository deputadoRepository, DespesaRepository despesaRepository) {
        this.deputadoRepository = deputadoRepository;
        this.despesaRepository = despesaRepository;
    }

    @Transactional
    public void processarCsv(MultipartFile arquivo, String uf) throws IOException {
        try (Reader leitor = new BufferedReader(new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            leitor.mark(1);
            if (leitor.read() != '\ufeff') {
                leitor.reset();
            }

            HeaderColumnNameMappingStrategy<DeputadoDespesaCsvRepresentation> estrategia = new HeaderColumnNameMappingStrategy<>();
            estrategia.setType(DeputadoDespesaCsvRepresentation.class);

            CsvToBean<DeputadoDespesaCsvRepresentation> csvToBean = new CsvToBeanBuilder<DeputadoDespesaCsvRepresentation>(leitor)
                    .withMappingStrategy(estrategia)
                    .withSeparator(';')
                    .withQuoteChar('"')
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<DeputadoDespesaCsvRepresentation> representacoes = csvToBean.parse();
            Map<String, Deputado> cacheDeputado = new HashMap<>();

            representacoes.stream()
                    .filter(representacao -> (uf == null || uf.equals(representacao.getUf())) && !representacao.getUf().equals("NA"))
                    .forEach(representacao -> {
                        Deputado deputado = cacheDeputado.computeIfAbsent(representacao.getCpf(),
                                cpf -> deputadoRepository.findByCpf(cpf).orElseGet(() -> {
                                    Deputado novoDeputado = Deputado.builder()
                                            .nome(representacao.getNome())
                                            .uf(representacao.getUf())
                                            .cpf(representacao.getCpf())
                                            .partido(representacao.getPartido())
                                            .build();
                                    return deputadoRepository.save(novoDeputado);
                                }));

                        LocalDateTime dataEmissao = null;
                        if (!representacao.getDataEmissao().isEmpty()) {
                            dataEmissao = LocalDateTime.parse(representacao.getDataEmissao());
                        }

                        Despesa despesa = Despesa.builder()
                                .deputado(deputado)
                                .dataEmissao(dataEmissao)
                                .fornecedor(representacao.getFornecedor())
                                .urlDocumento(representacao.getUrlDocumento())
                                .valorLiquido(representacao.getValorLiquido())
                                .build();
                        despesaRepository.save(despesa);
                    });
        }
    }

    @Transactional
    public void limpar() {
        deputadoRepository.resetarTabela();
        despesaRepository.resetarTabela();
    }
}
