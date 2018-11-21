package juliano.agendadecontato.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import juliano.agendadecontato.AgendaException;
import juliano.agendadecontato.Contato;

public class ArquivoAgenda {

	/**
	 * Armazena uma inst�ncia de ArquivoAgenda.
	 */
	private static ArquivoAgenda instance;

	private static final String ARQUIVO_AGENDA = "agenda.txt";

	/**
	 * Garante que a classe n�o vai ser instanciada pelo operador new.
	 */
	private ArquivoAgenda() {
	}

	/**
	 * Garante que a aplica��o tenha apenas uma inst�ncia de ArquivoAgenda.
	 * 
	 * @return Retorna a inst�ncia de ArquivoAgenda.
	 */
	public static ArquivoAgenda getInstance() {
		if (instance == null) {
			instance = new ArquivoAgenda();
		}
		return instance;
	}

	/**
	 * Grava os contatos (nome e telefone) no arquivo.
	 * 
	 * @param contatos Contatos criado pelo usu�rio.
	 * @throws AgendaException
	 */
	public void gravarContatos(Collection<Contato> contatos) throws AgendaException {
		try (PrintWriter arquivo = new PrintWriter(new FileWriter(ARQUIVO_AGENDA))) {
			for (Contato contato : contatos) {
				// Grava os dados no padr�o: nome;telefone.
				arquivo.println(contato.formatarNomeTelefone());
			}

		} catch (IOException e) {
			throw new AgendaException("Erro ao gravar os dados no arquivo", e);
		}
	}

	/**
	 * Carrega os contatos do arquivo para dentro da aplica��o.
	 * 
	 * @return Retorna as lista de contatos gravados no arquivo.
	 * @throws AgendaException
	 */
	public List<Contato> leContatos() throws AgendaException {
		List<Contato> contatos = new ArrayList<>();

		try (BufferedReader arquivo = new BufferedReader(new FileReader(ARQUIVO_AGENDA))) {
			String linha = "";

			while ((linha = arquivo.readLine()) != null) {
				// Separa os dados em tokens. Primeiro dado � o nome e o segundo o telefone.
				String[] token = linha.split(";");

				Contato contato = new Contato(token[0], token[1]);
				contatos.add(contato);
			}
		} catch (IOException e) {
			throw new AgendaException("Erro ao ler os contatos do arquivo", e);
		}

		return contatos;
	}
}
