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
 * @author Juliano R. Am�rico
 *
 */
public class Agenda {

	/**
	 * Mapeia o nome do contato como chave, e o valor s�o as informa��es do contato.
	 */
	Map<String, Contato> contatoMap = new TreeMap<>();

	/**
	 * Mapeia os contatos pela primeira letra, para realizar a busca r�pida dos
	 * contatos que cont�m a primeira letra igual.
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
	 * Carrega os contatos do arquivo para a cole��o map.
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
			throw new AgendaException("Contato j� existe!");
		}

		contato.validarDados();

		contatoMap.put(contato.getNome(), contato);

		// Grava cole��o na busca r�pida.
		gravarContatosPorLetra(contato);

		// Grava os contatos no arquivo.
		arquivoAgenda.gravarContatos(contatoMap.values());
	}

	/**
	 * � mapeado na nova cole��o os contatos separados por letra. Exemplo: todos os
	 * contatos que come�am com a letra 'a' s�o colocados juntos com a chave 'a' no
	 * mapa, isto � feito para facilitar a busca r�pida pela primeira letra do nome.
	 * Este m�todo deve ser sempre chamado ao criar um novo contato.
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

		// Refer�ncia o mesmo objeto na mem�ria, de que foi gravado na cole��o.
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

		// Exclui o contato da cole��o de busca r�pida.
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
	 * @param contato Novas informa��es do contato para editar.
	 * @throws AgendaException Lan�a exece��o se o contato n�o existe.
	 */
	public void editarContato(Contato contato) throws AgendaException {
		contato.validarDados();

		// Sen�o existir � lan�ada exce��o.
		verificaExistenciaContato(contato);

		// Grava os contatos no arquivo, ap�s edi��o do contato.
		arquivoAgenda.gravarContatos(contatoMap.values());
	}

	/**
	 * Busca os contatos que cont�m a primeira letra que o usu�rio solicitou. Por
	 * exemplo: 'j' e ser� listado todos os contatos que come�am com a letra 'j'.
	 * 
	 * @param primeiraLetra Representa a primeira letra do nome do contato.
	 */
	public List<Contato> buscarContatosComLetra(char primeiraLetra) {
		// Autoboxing
		List<Contato> contatos = contatosPorLetraMap.get(Character.toUpperCase(primeiraLetra));
		return contatos;
	}

	/**
	 * Busca os contatos que cont�m parte do nome, exemplo: parteNome = "ano" o
	 * resultado desta busca � Juliano e o seu n�mero de telefone.
	 * 
	 * @param parteNome O nome do contato que pode este padr�o de string.
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

			// Se encontrar o contato com o padr�o do regex, guarda o contato na lista.
			if (m.matches()) {
				contatosAchados.add(contato);
			}
		}

		return contatosAchados;
	}

	/**
	 * Verifica se o contato existe na cole��o map.
	 * 
	 * @param contato Contato para verificar exist�ncia.
	 * @throws AgendaException Lan�a a exce��o se o contato n�o existe.
	 */
	private void verificaExistenciaContato(Contato contato) throws AgendaException {
		if (!contatoMap.containsKey(contato.getNome())) {
			throw new AgendaException("Contato n�o existe!");
		}
	}

	/**
	 * Obt�m o contato da agenda.
	 * 
	 * @param nome Nome do contato
	 * @return Retorna o objeto Contato. Se o contato n�o existir � retornado nulo.
	 */
	public Contato obterContato(String nome) {
		return contatoMap.get(nome);
	}
}
