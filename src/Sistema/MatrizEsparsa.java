package Sistema;

import Sistema.Excecoes.EmptyMatrixException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <T>
 * @brief Classe MatrizEsparsa
 * @author Leonam Teixeira de Vasconcelos
 * @date   24/05/2018
 * @since  24/05/2018
 */

@SuppressWarnings("unchecked")
public class MatrizEsparsa <T>{
    /**
     * Número de linhas da Matriz Esparsa;
     */
    public final int linhas;
    
    /**
     * Número de colunas da Matriz Esparsa;
     */
    public final int colunas;
    
    /**
     * Célula cabeça, onde será ligada todas as outras cabeças;
     */
    private Celula head;
    
    /**
     * Número de células com conteúdo não nulo na Matriz Esparsa;
     */
    private long numero_de_celulas;
  
    /**
     * @author Leonam Teixeira de Vasconcelos
     * @brief  Classe privada Célula
     * Classe privada Célula. Contém referências para duas outras células: A que está a direita da mesma e a que
     * está abaixo. Tambem contem 3 construtores, um sendo inicializado apenas com o item, outro com item e posição
     * e por último, um que incicializa o objeto com apenas a posição;
     */
    
    private class Celula{
        public Celula abaixo, direita;
        public int linha, coluna;  
        public T item;
        public Celula(T item, int linha, int coluna){
            this.item = item;
            this.linha = linha;
            this.coluna = coluna;
        }
        public Celula(T item){
            this.item = item;
        }
        public Celula(int linha, int coluna){
            this.linha = linha;
            this.coluna = coluna;
        }
    }
    
    /**
     *  Construtor da matriz esparsa, que recebe por parametro linhas e colunas, inicializa os atributos 
     *  e chama o metodo cabeca() que inicializa as cabecas;
     * @param linhas Número de linhas com o qual a matriz será inicializada;
     * @param colunas Número de colunas com o qual a matriz será inicializada;
     *
    */
    
    public MatrizEsparsa(int linhas, int colunas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.head = new Celula(-1,-1);
        this.head.direita = head;
        this.head.abaixo = head;
        this.numero_de_celulas = 0;
        this.cabeca();
    }
    
    /**
    *   Metodo getCabeca: retorna a Celula Cabeça que está na posicao (linha, coluna)
    *   @param linha Coordenada da linha onde a célula cabeça está localizada;
    *   @param coluna Coordenada da coluna onde a célula cabeça está localizada;
    *   @return Celula Encontrada na posição (linha, coluna);
    */
    
    private Celula getCabeca(int linha, int coluna){
        Celula aux = this.head, aux2;
        do{
            aux = aux.direita;
            if(aux.coluna==coluna)
                break;
            
        }while(aux!=head);
        aux2=aux;
        do{
            aux2 = aux2.abaixo;
            if(aux2.linha==linha)
                break;
            
        }while(aux!=aux2);    
        return aux2;
    }

    /**
     * getCelulaAt: retorna a Celula que estã localizada na posicao linha, coluna da matriz
     * @param linha Coordenada da linha onde a célula está localizada;
     * @param coluna Coordenada da coluna onde a célula está localizada;
     * @return Celula Encontrada na posição (linha, coluna), caso a mesma não seja nula;
     */
    
    private Celula getCelulaAt(int linha, int coluna){
        Celula aux = head, a = aux;
        do{
            do{
                if(a.linha == linha && a.coluna == coluna){
                    return a;
                }
                a = a.direita;
            }while(a!=aux);
            aux = aux.abaixo;
            a = aux;
        }while(aux!=head);
        return null;
    }

    /**
     *  Metodo void cabeca(): utiliza os numeros de linha e coluna para inicializar as cabecas
     *  que são celulas comuns apenas com o valor de linha ou coluna iguais a -1;
    */
    
    private void cabeca(){
        Celula aux = this.head;
        for(int i = 0; i<this.linhas; i++){
            Celula nova = new Celula(null, i, -1);
            nova.abaixo = aux.abaixo;
            nova.direita = nova;
            aux.abaixo = nova;
            aux = aux.abaixo;
        }
        aux = this.head;
        for(int i = 0; i<this.colunas; i++){
            Celula nova = new Celula(null, -1, i);
            nova.direita = aux.direita;
            nova.abaixo = nova;
            aux.direita = nova;
            aux = aux.direita;
        }
    }

    /**
     * Método insere: adiciona um objeto item na posicao linha, coluna da matriz.
     * @param item Objeto do tipo T a ser inserido na posição (linha, coluna);
     * @param linha Coordenada da linha, onde o item a será inserido;
     * @param coluna Coordenada da coluna, onde o item a será inserido;
     * @throws NullPointerException Caso o item passado seja nulo;
     * @throws ArrayIndexOutOfBoundsException  Caso a posição passada viole os limites da Matriz;
     */
    
    public void insere(T item, int linha, int coluna) throws NullPointerException, ArrayIndexOutOfBoundsException{
        if(item==null){
            throw new NullPointerException();
        }else{
            if(linha>=this.linhas && linha<0 || coluna>=this.colunas && coluna<0){
                throw new ArrayIndexOutOfBoundsException();
            }else{
                Celula ch = this.getCabeca(linha,-1), cv = this.getCabeca(-1, coluna), nova = new Celula(item, linha, coluna);
                if(ch.direita!=ch || cv.abaixo!=cv){
                    Celula a = ch, b = cv;
                    do{
                        if(a.coluna<coluna && a.direita.coluna>coluna){
                            break;
                        }
                        a = a.direita;
                    }while(a!=ch);
                    do{
                        if(b.linha<linha && b.abaixo.linha>linha){
                            break;
                        }
                        b = b.abaixo;
                    }while(b!=cv);
                    
                    nova.direita = a.direita;
                    nova.abaixo = b.abaixo;
                    a.direita = nova;
                    b.abaixo = nova;
                }else{
                    if(ch.direita==ch){
                        nova.direita = ch.direita;
                        ch.direita = nova;
                    }
                    if(cv.abaixo==cv){
                        nova.abaixo = cv.abaixo;
                        cv.abaixo = nova;
                    }
                }
                this.numero_de_celulas++;
            }
        }
    }

    /**
     * Método celulas_ocupadas(): retorna o número de celulas não nulas presentes na matriz;
     * @return Número de células não nulas da matriz;
     */
    
    public long celulas_ocupadas(){
        return this.numero_de_celulas;
    }
    
    /**
     * Método toString(): retorna uma String que contém toda a matriz, com um '.' no lugar das células
     * nulas;
     * @return String contendo a matriz onde nas posições nulas, foi inserido um caracter '.';
     */
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<this.linhas; i++){
            for(int j = 0; j<this.colunas; j++){
                Celula aux = getCelulaAt(i, j);
                if(aux==null){
                    sb.append(".");
                }else if(aux.item!=null){
                    sb.append(aux.item);
                }
                sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Método excluir(): remove uma célula (caso ela exista), da posição i,j da matriz;
     * @param i Coordenada da linha, onde o item a ser excluído se encontra;
     * @param j Coordenada da coluna, onde o item a ser excluído se encontra;
     * @return Objeto do tipo T contendo o item que foi excluído na posição (i,j);
     * @throws NullPointerException Caso não exista célula não nula na posição (i,j);
     * @throws EmptyMatrixException Caso a matriz esteja vazia;
     * @throws ArrayIndexOutOfBoundsException  Caso os indices violem os limites da matriz;
     */
    
    public T excluir(int i, int j) throws NullPointerException, EmptyMatrixException, ArrayIndexOutOfBoundsException{
        if(i>=this.linhas || j>=this.colunas)
            throw new ArrayIndexOutOfBoundsException();
        else if(this.numero_de_celulas==0)
            throw new EmptyMatrixException();
        else if(this.getCelulaAt(i, j)==null)
            throw new NullPointerException();
        else {
            Celula ch = this.getCabeca(i,-1), cv = this.getCabeca(-1, j), a = ch, b = cv, ap = null, bp = null;
            if(ch.direita!=ch && cv.abaixo !=cv){
                do{
                    ap = a;
                    a = a.direita;
                    if(a.coluna == j){
                        break;
                    }
                }while(a!=ch);
                do{
                    bp = b;
                    b = b.abaixo;
                    if(b.linha==i)
                        break;
                }while(b!=cv);
                ap.direita = a.direita;
                bp.abaixo = b.abaixo;
                this.numero_de_celulas--;
                return a.item;
            }
        }
        return null;
    }

    /**
     * Método insereBordas(): recebe a largura da borda e o valor que será usado na construção da mesma.
     * É um método que chama outro, passando os valores necessários para que as bordas sejam criadas;
     * @param largura Largura das bordas a serem inseridas;
     * @param valor Valor que será inserido nas bordas; 
     */
    
    public void insereBordas(int largura, T valor){
        bordas(valor, largura, (this.linhas-1), 0, 0, (this.colunas-1));
    }
    
    /**
     * Método transposta(): retorna uma Matriz Esparsa transposta à atual; 
     * @return Matriz Esparsa transposta;
     */
    
    public MatrizEsparsa<T> transposta(){
        MatrizEsparsa<T> transposta = new MatrizEsparsa<>(this.colunas, this.linhas);
        for(int i = 0; i< transposta.linhas; i++){
            for(int j = 0; j<this.linhas; j++){
                T item = this.itemAt(j, i);
                if(item!=null)
                    transposta.insere(item, i, j);                
            }
        }
        return transposta;
    }
    
    /**
     * Método rotacionnar_sentidoHorario(): retorna a Matriz Esparsa atual rotacionada 90º no sentido horário;
     * @return Matriz Esparsa rotacionada 90º no sentido horário;
     */
    
    public MatrizEsparsa<T> rotacionar_sentidoHorario(){
        MatrizEsparsa<T> rotacionada = new MatrizEsparsa<>(this.colunas,this.linhas);
        int k;
        for(int i = 0; i< rotacionada.linhas; i++){
            k = rotacionada.colunas ;
            for(int j = 0; j<rotacionada.colunas; j++){
                k--;
                T item = this.itemAt(k, i);
                if(item!=null)
                     rotacionada.insere(item, i, j);
            }
        }
        return rotacionada;
    }
    
    /**
     * Método inverteVerticalmente(): retorna a Matriz Esparsa atual invertida verticalmente;
     * @return Matriz Esparsa invertida verticalmente;
     */
    
    public MatrizEsparsa<T> inverterVerticalmente(){
        return this.rotacionar_sentidoHorario().transposta();
    }
    
    /**
     * Método inverteHorizontalmente(): retorna a Matriz Esparsa atual invertida horizontalmente;
     * @return Matriz Esparsa invertida horizontalmente;
     */
    
    public MatrizEsparsa<T> inverterHorizontalmente(){
        return this.transposta().rotacionar_sentidoHorario();
    }
    
    /**
     * Método inverteCores(): inverte o valor de todas as células da matriz com base no valor 
     * máximo permitido passado por parâmetro
     * @param maxVal Valor máximo inserido na matriz;
     * @throws NullPointerException Caso item a ser invetido seja nulo;
     * @throws EmptyMatrixException Caso a matriz esteja vazia;
     */

    public void inverterCores(T maxVal) throws NullPointerException, EmptyMatrixException{
        for(int i = 0; i< this.linhas; i++){
            for(int j = 0; j <this.colunas; j++){
                T item = itemAt(i, j);
                if(item==null){
                    this.insere(maxVal, i, j);
                }else if(item.equals(maxVal)){
                    try {
                        this.excluir(i, j);
                    } catch (NullPointerException ex) {
                        Logger.getLogger(MatrizEsparsa.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (EmptyMatrixException ex) {
                        Logger.getLogger(MatrizEsparsa.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        Logger.getLogger(MatrizEsparsa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    int a = (int) maxVal;
                    int b = (int) item;
                    b = a - b;
                    if((int)b==0){
                        this.excluir(i, j);
                    }else{
                        T t = (T)(Object)b;
						getCelulaAt(i,j).item = t;
                    }
                }
            }
        }
    }
    
    /**
     * Método gerarStringPGM(): retorna uma String padronizada para gerar o arquivo pgm com a matriz atual;
     * @param maxVal Valor máximo inserido na matriz;
     * @return String contendo as informações para gerar um arquivo pgm;
     */
    
    public String gerarStringPGM(T maxVal){
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append("P2\n");
        sb.append(this.colunas).append(" ").append(this.linhas).append("\n");
        sb.append(maxVal).append("\n");
        for(int i = 0; i<this.linhas; i++){
            for(int j = 0; j<this.colunas; j++){
                Celula aux = getCelulaAt(i, j);
                if(aux==null){
                    sb.append("0");
                }else if(aux.item!=null){
                    sb.append(aux.item);
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Método recursivo bordas(): chama a si mesmo inserindo a borda pixel por pixel até que o a largura da borda passada
     * seja alcançada;
     * @param valor Valor que será inserido nas bordas;
     * @param largura Número de pixel das bordas;
     * @param b Coordenada da linha onde a borda inferior será inserida;
     * @param t Coordenada da linha onde a borda superior será inserida;
     * @param l Coordenada da coluna onde a borda esquerda será inserida;
     * @param r Coordenada da coluna onde a borda direita será inserida;
     */
    
    private void bordas(T valor, int largura,int b, int t, int l, int r){
        if(t<largura){
            for(int i = l; i<=r; i++){
                this.insere(valor,t,i);
                this.insere(valor,b,i);
            }
            for(int i = (t+1); i<b; i++){
                this.insere(valor,i,l);
                this.insere(valor,i,r);
            }
            this.bordas(valor, largura, (b-1), (t+1), (l+1), (r-1));
        }
    }
    
    /**
     * Método itemAt(): retorna o item localizado na posicao(linha, coluna) da matriz atual se existir. 
     * Se não, retorna nulo; 
     * @param linha Coordenada da linha, onde o item se encontra;
     * @param coluna Coordenada da coluna, onde o item se encontra;
     * @return Objeto do tipo T localizado em (linha, coluna) da matriz atual;
     */
    
    public T itemAt(int linha, int coluna){
        if(this.getCelulaAt(linha, coluna)==null){
            return null;
        }else
            return getCelulaAt(linha, coluna).item;
    }
}
