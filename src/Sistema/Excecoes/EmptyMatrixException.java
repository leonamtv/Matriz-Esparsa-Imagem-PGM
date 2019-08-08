package Sistema.Excecoes;

/**
 * @brief Classe EmptyMatrixException
 * @author Leonam Teixeira de Vasconcelos
 * @date   01/06/2018
 */
public class EmptyMatrixException extends Exception{
    /**
     * Construtor vazio da Exceção;
     */
    public EmptyMatrixException(){
        super("Matriz vazia");
    }
    
    /**
     * Construtor da Exceção que recebe como parametro uma String contendo a
     * mensagem;
     * @param message String com a mensagem que será impressa no lançamento
     * da exceção;
     */
    public EmptyMatrixException(String message){
        super(message);
    }
}
