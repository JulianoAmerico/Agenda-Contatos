package juliano.agendadecontato.io;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe que trabalha com Console para coletar dados inseridos pelo usu�rio.
 * 
 * @author Juliano R. Am�rico
 *
 */
public class Console {

	/**
	 * L� a linha digitada no console.
	 * 
	 * @return Retorna a String lida na linha.
	 */
	public static String readLine() {
		String linha = "";

		try {
			linha = scanner().nextLine();
		} catch (NoSuchElementException e) {
		}

		return linha;
	}

	/**
	 * L� o token completo.
	 * 
	 * @return Retorna o token lido.
	 */
	public static String readString() {
		String palavra = "";

		try {
			palavra = scanner().next();
		} catch (NoSuchElementException e) {
		}

		return palavra;
	}

	/**
	 * L� a primeira letra escrita no console.
	 * 
	 * @return Retorna a letra lida.
	 */
	public static Character readChar() {
		Character c = ' ';

		try {
			c = scanner().next().charAt(0);
		} catch (NoSuchElementException e) {
		}

		return c;
	}

	/**
	 * L� o valor inteiro lido no console.
	 * 
	 * @return Retorna o valor inteiro.
	 */
	public static int readInt() {
		int numero = 0;
		try {
			numero = scanner().nextInt();
		} catch (InputMismatchException e) {
		}

		return numero;
	}

	/**
	 * Carrega a classe Scanner para ler dados do console.
	 * 
	 * @return Retorna a inst�ncia de Scanner.
	 */
	private static Scanner scanner() {
		Scanner sc = new Scanner(System.in);
		return sc;
	}
}
