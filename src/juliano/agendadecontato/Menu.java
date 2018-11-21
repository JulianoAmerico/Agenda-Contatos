package juliano.agendadecontato;

import juliano.agendadecontato.io.Console;

/**
 * Classe que representa o menu de ações que o usuário pode escolher.
 * 
 * @author Juliano R. Américo
 *
 */
public class Menu {

	// Opções possíveis do menu.
	public static final int CRIAR = 1;
	public static final int EDITAR = 2;
	public static final int EXCLUIR = 3;
	public static final int BUSCAR_POR_LETRA = 4;
	public static final int BUSCAR = 5;
	public static final int SAIR = 6;

	public static int exibirMenu() throws AgendaException {
		System.out.println();
		System.out.println("* * * * MENU * * * *");
		System.out.println("1. Criar novo contato");
		System.out.println("2. Editar contato");
		System.out.println("3. Excluir contato");
		System.out.println("4. Buscar contatos por letra");
		System.out.println("5. Procurar contatos");
		System.out.println("6. Sair");
		System.out.println();
		System.out.print("Escolha uma opção: ");

		int opcaoEscolhida = Console.readInt();

		if (opcaoEscolhida < CRIAR || opcaoEscolhida > SAIR) {
			throw new AgendaException("Opção inválida");
		}

		return opcaoEscolhida;
	}

}
