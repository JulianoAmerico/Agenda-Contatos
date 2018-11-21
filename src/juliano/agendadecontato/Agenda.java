package juliano.agendadecontato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juliano.agendadecontato.io.ArquivoAgenda;

/**
 * Classe que representa a agenda.
 * 
 * @author Juliano R. Américo
 *
 */
public class Agenda {

	/**
	 * Mapeia o nome do contato como chave, e o valor são as informações do contato.
	 */
	Map<String, Contato> contatoMap = new TreeMap<>();

	/**
	 * Mapeia os contatos pela primeira letra, para realizar a busca rápida dos
	 * contatos que contém a primeira letra igual.
	 */
	Map<Character, List<Contato>> contatosPorLetraMap = new TreeMap<>();

	/**
	 * Classe que gerencia os contatos no arquivo.
	 */
	ArquivoAgenda arquivoAgenda = ArquivoAgenda.getInstance();

	public Agenda() {
		carregarContatos();
	}

	/**
	 * Carrega os contatos do arquivo para a coleção map.
	 */
	private void carregarContatos() {
		try {
			List<Contato> contatos = arquivoAgenda.leContatos();
			for (Contato contato : contatos) {
				criarContato(contato);
			}
		} catch (AgendaException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cria o novo contato no map e no arquivo.
	 * 
	 * @param contato
	 * @throws AgendaException
	 */
	public void criarContato(Contato contato) throws AgendaException {
		if (contatoMap.containsKey(contato.getNome())) {
			throw new AgendaException("Contato já existe!");
		}

		contato.validarDados();

		contatoMap.put(contato.getNome(), contato);

		// Grava coleção na busca rápida.
		gravarContatosPorLetra(contato);

		// Grava os contatos no arquivo.
		arquivoAgenda.gravarContatos(contatoMap.values());
	}

	/**
	 * É mapeado na nova coleção os contatos separados por letra. Exemplo: todos os
	 * contatos que começam com a letra 'a' são colocados juntos com a chave 'a' no
	 * mapa, isto é feito para facilitar a busca rápida pela primeira letra do nome.
	 * Este método deve ser sempre chamado ao criar um novo contato.
	 * 
	 * @param contato Novo contato
	 */
	private void gravarContatosPorLetra(Contato contato) {
		char primeiraLetra = Character.toUpperCase(contato.getNome().charAt(0));
		List<Contato> contatos = contatosPorLetraMap.get(primeiraLetra);

		if (contatos == null) {
			contatos = new ArrayList<>();
			contatosPorLetraMap.put(primeiraLetra, contatos);
		}

		// Referência o mesmo objeto na memória, de que foi gravado na coleção.
		contatos.add(contato);

	}

	/**
	 * Exclui o contato da agenda
	 * 
	 * @param contato
	 * @throws AgendaException
	 */
	public void excluirContato(Contato contato) throws AgendaException {
		char primeiraLetra = Character.toUpperCase(contato.getNome().charAt(0));
		List<Contato> contatos = contatosPorLetraMap.get(primeiraLetra);

		// Exclui o contato da coleção de busca rápida.
		for (Contato contato2 : contatos) {
			if (contato2.getNome().equals(contato.getNome())) {
				contatos.remove(contato2);
			}
		}

		contatoMap.remove(contato.getNome());

		arquivoAgenda.gravarContatos(contatoMap.values());
	}

	/**
	 * Edita o contato, se existir.
	 * 
	 * @param contato Novas informações do contato para editar.
	 * @throws AgendaException Lança execeção se o contato não existe.
	 */
	public void editarContato(Contato contato) throws AgendaException {
		contato.validarDados();

		// Senão existir é lançada exceção.
		verificaExistenciaContato(contato);

		// Grava os contatos no arquivo, após edição do contato.
		arquivoAgenda.gravarContatos(contatoMap.values());
	}

	/**
	 * Busca os contatos que contém a primeira letra que o usuário solicitou. Por
	 * exemplo: 'j' e será listado todos os contatos que começam com a letra 'j'.
	 * 
	 * @param primeiraLetra Representa a primeira letra do nome do contato.
	 */
	public List<Contato> buscarContatosComLetra(char primeiraLetra) {
		// Autoboxing
		List<Contato> contatos = contatosPorLetraMap.get(Character.toUpperCase(primeiraLetra));
		return contatos;
	}

	/**
	 * Busca os contatos que contém parte do nome, exemplo: parteNome = "ano" o
	 * resultado desta busca é Juliano e o seu número de telefone.
	 * 
	 * @param parteNome O nome do contato que pode este padrão de string.
	 */
	public List<Contato> buscarContatosPorParteNome(String parteNome) {
		parteNome = parteNome.toUpperCase();

		String regex = "(\\w)*" + parteNome + "(\\w)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = null;

		// Armazena os contatos achados na busca.
		List<Contato> contatosAchados = new ArrayList<>();

		Collection<Contato> contatos = contatoMap.values();

		for (Contato contato : contatos) {
			m = pattern.matcher(contato.getNome().toUpperCase());

			// Se encontrar o contato com o padrão do regex, guarda o contato na lista.
			if (m.matches()) {
				contatosAchados.add(contato);
			}
		}

		return contatosAchados;
	}

	/**
	 * Verifica se o contato existe na coleção map.
	 * 
	 * @param contato Contato para verificar existência.
	 * @throws AgendaException Lança a exceção se o contato não existe.
	 */
	private void verificaExistenciaContato(Contato contato) throws AgendaException {
		if (!contatoMap.containsKey(contato.getNome())) {
			throw new AgendaException("Contato não existe!");
		}
	}

	/**
	 * Obtém o contato da agenda.
	 * 
	 * @param nome Nome do contato
	 * @return Retorna o objeto Contato. Se o contato não existir é retornado nulo.
	 */
	public Contato obterContato(String nome) {
		return contatoMap.get(nome);
	}
}
