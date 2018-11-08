/**
 * 
 * @author Paulo Meireles
 */

package com.enderecoapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.enderecoapp.vo.EnderecoVO;


@RestController
@RequestMapping("/endereco")
public class EnderecoAppController {

	List listaEnderecos;

	public EnderecoAppController() {
		listaEnderecosInicial();
	}

	public List getListaEnderecos() {
		return listaEnderecos;
	}

	/**
	 * Este metodo adiciona as seguintes listas de endereços no serviço de
	 * localização.
	 */
	private void listaEnderecosInicial() {
		listaEnderecos = new ArrayList();

		EnderecoVO endereco1 = new EnderecoVO();
		endereco1.setEndereco("Av Paulista");
		endereco1.setBairro("Centro");
		endereco1.setCidade("São Paulo");
		endereco1.setEstado("SP");
		endereco1.setPais("Brasil");
		endereco1.setCep("1234");

		listaEnderecos.add(endereco1);

		EnderecoVO endereco2 = new EnderecoVO();
		endereco2.setEndereco("Av Sumare");
		endereco2.setBairro("Centro");
		endereco2.setCidade("São Paulo");
		endereco2.setEstado("SP");
		endereco2.setPais("Brasil");
		endereco2.setCep("12345");

		listaEnderecos.add(endereco2);

		EnderecoVO endereco3 = new EnderecoVO();
		endereco3.setEndereco("Av Pacaembu");
		endereco3.setBairro("Centro");
		endereco3.setCidade("São Paulo");
		endereco3.setEstado("SP");
		endereco3.setPais("Brasil");
		endereco3.setCep("123456");

		listaEnderecos.add(endereco3);
	}

	/**
	 * Este metodo efetua uma requicisão para o serviço de localização, retornando
	 * os endereços que possuem listados.
	 */
	@GetMapping
	public ResponseEntity<?> listaEndereco() {
		return new ResponseEntity<List>(getListaEnderecos(), HttpStatus.OK);
	}

	/** Este metodo adiciona mais um endereço em nosso serviço de localização. */
	@PostMapping
	public ResponseEntity<?> adicionaEndereco(@RequestBody EnderecoVO enderecoVO) {
		if (getListaEnderecos().add(enderecoVO)) {

			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Mensagem de erro", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Este metodo exclui um endereço do nosso serviço de localização, desde que
	 * seja informado o CEP
	 */
	@DeleteMapping("/{cep}")
	public ResponseEntity<?> deletaEndereco(@PathVariable String cep) {
		for (int i = 0; i < getListaEnderecos().size(); i++) {
			EnderecoVO enderecoVO = (EnderecoVO) getListaEnderecos().get(i);
			if (enderecoVO.getCep().equals(cep)) {
				getListaEnderecos().remove(i);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<>("Mensagem de erro", HttpStatus.NOT_FOUND);
	}

	/**
	 * Este metodo efetua uma alteração no endereço desejado, especificando
	 * corretamente em qual campo deseja atualizar.
	 */
	@PutMapping("/{cep}")
	public ResponseEntity<?> atualizaEndereco(@RequestBody EnderecoVO enderecoVO, @PathVariable String cep) {
		for (int i = 0; i < getListaEnderecos().size(); i++) {
			EnderecoVO ev = (EnderecoVO) getListaEnderecos().get(i);
			if (ev.getCep().equals(cep)) {
				getListaEnderecos().remove(i);
				getListaEnderecos().add(enderecoVO);
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>("Mensagem de erro", HttpStatus.NOT_FOUND);
	}

	/**
	 * Este metodo efetua a busca de uma localização utilizando somente o cep
	 * informado.
	 */
	@GetMapping("/{cep}")
	public ResponseEntity<?> buscaPorCep(@PathVariable String cep) {
		for (int i = 0; i < getListaEnderecos().size(); i++) {
			EnderecoVO enderecoVO = (EnderecoVO) getListaEnderecos().get(i);
			if (enderecoVO.getCep().equals(cep)) {

				return new ResponseEntity<>(enderecoVO, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Mensagem de Erro", HttpStatus.NOT_FOUND);
	}

	/**
	 * Este metodo é somente para verificar o status do nosso serviço de aplicação.
	 */
	@GetMapping("/status")
	public ResponseEntity<?> status() {
		return new ResponseEntity("ATIVO", HttpStatus.OK);
	}

}
