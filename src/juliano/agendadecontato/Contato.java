package juliano.agendadecontato;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que representa contato da agenda
 * 
 * @author Juliano R. Am�rico
 *
 */
public class Contato {
	/**
	 * Nome do contato
	 */
	private String nome;

	/**
	 * Telefone do contato
	 */
	private String telefone;

	/**
	 * Construtor.
	 * 
	 * @param nome     Nome do contato
	 * @param telefone Telefone do contato
	 */
	public Contato(String nome, String telefone) {
		this.nome = nome;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Valida os dados do contato: nome e telefone.
	 * 
	 * @throws AgendaException Lan�a exce��o caso os dados s�o inv�lidos.
	 */
	public void validarDados() throws AgendaException {
		validarNome();
		validarTelefone();
	}

	/**
	 * Valida o nome do contato. O nome n�o pode ser nulo ou vazio.
	 */
	public void validarNome() throws AgendaException {
		if (this.nome == null || this.nome.trim().length() == 0) {
			throw new AgendaException("O nome do contato n�o pode ser vazio");
		}
	}

	/**
	 * O telefone deve seguir o padr�o: XXXXX-XXXX, sendo que no primeiro conjunto
	 * antes h�fen pode ter 4 ou 5 n�meros.
	 * 
	 * @throws AgendaException Lan�a exce��o se o telefone n�o corresponde ao
	 *                         padr�o.
	 */
	public void validarTelefone() throws AgendaException {
		String regex = "\\d\\d\\d\\d(\\d)?-\\d\\d\\d\\d";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(this.telefone);

		if (!m.matches()) {
			throw new AgendaException("O telefone � inv�lido");
		}
	}

	/**
	 * Formata nome e telefone para ser gravado no arquivo.
	 * 
	 * @return Retorna nome e telefone formatado.
	 */
	public String formatarNomeTelefone() {
		return nome + ";" + telefone;
	}

	/**
	 * Ao ser chamado pelo println, � impresso nome e telefone.
	 */
	@Override
	public String toString() {
		return nome + " => " + telefone;
	}
}
