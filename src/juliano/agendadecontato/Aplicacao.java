package juliano.agendadecontato;

import java.util.List;

import juliano.agendadecontato.io.Console;

/**
 * Classe que interagi com o usuário, para executar ações na agenda.
 * 
 * @author Juliano R. Américo
 *
 */
public class Aplicacao {

	/**
	 * Representa a agenda.
	 */
	private Agenda agenda;

	public Aplicacao() {
		agenda = new Agenda();
	}

	/**
	 * Inicia a aplicação.
	 */
	public void iniciar() {

		System.out.println("=======================");
		System.out.println("||      CONTATOS     ||");
		System.out.println("=======================");

		while (true) {
			try {

				int opcaoEscolhida = Menu.exibirMenu();

				switch (opcaoEscolhida) {
				case Menu.CRIAR:
					criarContato();
					break;
				case Menu.EDITAR:
					editarContato();
					break;
				case Menu.EXCLUIR:
					excluirContato();
					break;
				case Menu.BUSCAR_POR_LETRA:
					buscarContatosComLetra();
					break;
				case Menu.BUSCAR:
					buscarContatosPorParteNome();
					break;
				case Menu.SAIR:
					System.out.println("Terminando aplicação...");
					Thread.sleep(2000);
					System.exit(0);
				}
			} catch (AgendaException | InterruptedException e) {
				System.err.println(e.getMessage());
				System.out.println();
			}
		}
	}

	/**
	 * Cria o contato na agenda.
	 * 
	 * @throws AgendaException
	 */
	private void criarContato() throws AgendaException {
		System.out.print("Nome: ");
		String nome = Console.readString();

		System.out.print("Telefone: ");
		String telefone = Console.readString();

		Contato contato = new Contato(nome, telefone);

		agenda.criarContato(contato);

		System.out.println("Contato criado!");
	}

	/**
	 * Edita o contato
	 * 
	 * @throws AgendaException
	 */
	private void editarContato() throws AgendaException {
		System.out.print("Nome: ");
		String nome = Console.readString();

		System.out.print("Novo telefone: ");
		String telefone = Console.readString();

		// Cria objeto de contato e valida os dados do contato.
		Contato contato = new Contato(nome, telefone);
		contato.validarDados();

		// Após validação, carrega os dados do contato existente e edita o telefone.
		contato = agenda.obterContato(nome);
		if (contato == null) {
			throw new AgendaException("Contato não existe!");
		}

		contato.setTelefone(telefone);

		agenda.editarContato(contato);

		System.out.println("Contato foi editado!");
	}

	/**
	 * Exclui o contato
	 * 
	 * @throws AgendaException
	 */
	private void excluirContato() throws AgendaException {
		System.out.println("Nome: ");
		String nome = Console.readString();

		Contato contato = agenda.obterContato(nome);

		if (contato == null) {
			throw new AgendaException("Contato não existe!");
		}

		agenda.excluirContato(contato);
		System.out.println("Contato foi excluído!");
	}

	/**
	 * Busca os contatos que começa com a letra escolhida pelo usuário.
	 * 
	 * @throws AgendaException
	 */
	private void buscarContatosComLetra() throws AgendaException {
		System.out.print("Letra: ");
		char primeiraLetra = Console.readChar();

		List<Contato> contatos = agenda.buscarContatosComLetra(primeiraLetra);
		verificaListaVazia(contatos);

		for (Contato contato : contatos) {
			System.out.println(contato);
		}

	}

	/**
	 * Busca contatos com parte do nome escolhido pelo usuário.
	 * 
	 * @throws AgendaException
	 */
	private void buscarContatosPorParteNome() throws AgendaException {
		System.out.print("Parte do nome: ");
		String parteNome = Console.readString();

		System.out.println();
		List<Contato> contatos = agenda.buscarContatosPorParteNome(parteNome);

		// Lista vazia lança exceção.
		verificaListaVazia(contatos);

		// Senão lançar a exceção mostra os contatos encontrados
		for (Contato contato : contatos) {
			System.out.println(contato);
		}
	}

	private void verificaListaVazia(List<Contato> contatos) throws AgendaException {
		if (contatos.isEmpty()) {
			throw new AgendaException("Nenhum contato encontrado!");
		}
	}

}
