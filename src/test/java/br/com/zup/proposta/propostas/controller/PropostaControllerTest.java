package br.com.zup.proposta.propostas.controller;

import br.com.zup.proposta.propostas.controller.request.NovaPropostaRequest;
import br.com.zup.proposta.propostas.controller.request.NovoEnderecoRequest;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropostaRepository propostaRepository;

    @Test
    @DisplayName("Deveria criar uma nova proposta e o status deve ser ELEGIVEL")
    public void deveriaCriarUmaNovaProposta() throws Exception {
        var request = new NovaPropostaRequest(
                "12345678909",
                "Fulano",
                "email@email.com",
                new NovoEnderecoRequest(
                        "Logradouro",
                        "Complemento",
                        "Bairro",
                        "60811697",
                        "Cidade",
                        "Estado"
                ),
                new BigDecimal("1000")
        );
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/propostas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        var optional = propostaRepository.findByDocumento(request.getDocumento());
        Assertions.assertTrue(optional.isPresent());
        var proposta = optional.get();
        Assertions.assertEquals(PropostaStatus.ELEGIVEL, proposta.getStatus());
    }

    @Test
    @DisplayName("Deveria criar uma nova proposta e o status deve ser NAO_ELEGIVEL")
    public void deveriaCriarUmaNovaPropostaNaoElegivel() throws Exception {
        var request = new NovaPropostaRequest(
                "37924849009",
                "Fulano",
                "email@email.com",
                new NovoEnderecoRequest(
                        "Logradouro",
                        "Complemento",
                        "Bairro",
                        "60811697",
                        "Cidade",
                        "Estado"
                ),
                new BigDecimal("1000")
        );
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/propostas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        var optional = propostaRepository.findByDocumento(request.getDocumento());
        Assertions.assertTrue(optional.isPresent());
        var proposta = optional.get();
        Assertions.assertEquals(PropostaStatus.NAO_ELEGIVEL, proposta.getStatus());
    }

    @Test
    public void deveriaRetornarStatus422ComDocumentoDuplicado() throws Exception {
        var request = new NovaPropostaRequest(
                "12345678909",
                "Fulano",
                "email@email.com",
                new NovoEnderecoRequest(
                        "Logradouro",
                        "Complemento",
                        "Bairro",
                        "60811697",
                        "Cidade",
                        "Estado"
                ),
                new BigDecimal("1000")
        );
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/propostas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        );
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/propostas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        Assertions.assertEquals(1, propostaRepository.count());
    }

    @Test
    public void deveriaRetornarStatus400SeDocumentoNaoForInformado() throws Exception {
        var request = new NovaPropostaRequest(
                null,
                "Fulano",
                "email@email.com",
                new NovoEnderecoRequest(
                        "Logradouro",
                        "Complemento",
                        "Bairro",
                        "60811697",
                        "Cidade",
                        "Estado"
                ),
                new BigDecimal("1000")
        );
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/propostas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(0, propostaRepository.count());
    }


}