package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.repository.BiometriaRepository;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import br.com.zup.proposta.propostas.controller.request.NovoEnderecoRequest;
import br.com.zup.proposta.propostas.model.Endereco;
import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BiometriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    @BeforeEach
    private void setUp() {
        var proposta = new Proposta(
                "12345678909",
                "Fulano",
                "email@email.com",
                new Endereco(
                        "Logradouro",
                        "Complemento",
                        "Bairro",
                        "60811697",
                        "Cidade",
                        "Estado"
                ),
                new BigDecimal("1000")
        );
        var cartao = new Cartao("1234432156789876", LocalDateTime.now(), "titular");
//        proposta.
//        propostaRepository.save(proposta);
    }

}