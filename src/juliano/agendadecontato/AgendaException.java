package juliano.agendadecontato;

@SuppressWarnings("serial")
public class AgendaException extends Exception {

	public AgendaException(String message) {
		super(message);
	}

	public AgendaException(Throwable cause) {
		super(cause);
	}

	public AgendaException(String message, Throwable cause) {
		super(message, cause);
	}
}
