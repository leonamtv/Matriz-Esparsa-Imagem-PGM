package Sistema;

import Sistema.Excecoes.EmptyMatrixException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief Classe Sistema
 * @author Leonam Teixeira de Vasconcelos
 * @date 31/05/2018
 * @since 31/05/2018
 */

public class Sistema {
    /**
     * Scanner scan para leitura de opções nos menus;
     */
    private static Scanner scan;
    
    /**
     *  Matriz Esparsa utilizada para o processamento de imagem;
     */
    private static MatrizEsparsa<Integer> imagem;
    
    /**
     * Scanner para a leitura do arquivo contendo as informações da imagem ;
     */
    private static Scanner ler;
    
    /**
     * Integer contendo o valor máximo da imagem lido do arquivo pgm que contem a imagem;
     */
    private static Integer maxVal;
    
    /**
     * String contendo o nome da imagem;
     */
    private static String nome_imagem;
    
    /**
     * Método main(): método principal do código;
     * @param args
     */
    
    public static void main(String args[]) {
        menu();
    }
    
    /**
     * Método menu(): imprime o menu principal interativo para o processamento
     * de imagem ser realizado;
     */
    
    public static void menu(){
        System.out.println("====================================OPÇÕES=====================================");
        System.out.println("Digite: ");
        System.out.println("1 - Para realizar o processamento com a imagem de exemplo ('exemplo.pgm');");
        System.out.println("2 - Para ler uma imagem que esteja no diretório raiz do projeto; ");
        System.out.println("3 - Para sair.");
        System.out.println("-------------------------------------------------------------------------------");
        scan = new Scanner(System.in);
        int i = scan.nextInt();
        switch(i){
            case 1: ler_imagem("../Trabalho1.pgm");menu_imagem("Trabalho1.pgm"); break;
            case 2: ler_nome(); break;
            case 3: System.exit(0);
            default: System.exit(0);
        }
    }
    
    /**
     * Método ler_nome(): lê o nome do arquivo que será utilizado;
     */
    
    public static void ler_nome(){
        System.out.println("Digite o nome da imagem exatamente da forma como está escrito, \nsem a extensão '.pgm': ");
        scan = new Scanner(System.in);
        nome_imagem = scan.nextLine();
        nome_imagem = new StringBuilder().append(nome_imagem).append(".pgm").toString();
        ler_imagem(nome_imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método imprime_imagem(): imprime a matriz imagem no terminal;
     */
    
    public static void imprime_imagem(){ 
        System.out.println(imagem); menu_imagem(nome_imagem); 
    }
    
    /**
     * Método rotaciona_salva(): rotaciona a matriz imagem e salva como um arquivo pgm na pasta
     * raiz do projeto;
     */
    
    public static void rotaciona_salva(){ 
        imagem = imagem.rotacionar_sentidoHorario(); 
        escreverArquivo(geraNome("IMG_Rotacionada"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método rotaciona_imprime_salva(): rotaciona a matriz imagem, imprime no terminal e salva 
     * como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void rotaciona_imprime_salva(){ 
        imagem = imagem.rotacionar_sentidoHorario(); 
        System.out.println(imagem);escreverArquivo(geraNome("IMG_Rotacionada"), imagem);
        menu_imagem(nome_imagem);}
    
    /**
     * Método inverte_cores_salva(): inverte as cores da matriz imagem e salva como um arquivo pgm
     * na pasta raiz do projeto;
     */
    
    public static void inverte_cores_salva(){
        try{
            imagem.inverterCores(255);
            escreverArquivo(geraNome("IMG_CorInvert"), imagem);
            menu_imagem(nome_imagem);
        }catch (NullPointerException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyMatrixException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método inverte_cores_imprime_salva(): inverte as cores da matriz imagem, imprime no terminal
     * e salva como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void inverte_cores_imprime_salva(){
        try{
            imagem.inverterCores(255);
            System.out.println(imagem);
            escreverArquivo(geraNome("IMG_CorInvert"), imagem);
            menu_imagem(nome_imagem);
        } catch (NullPointerException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyMatrixException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método insere_borda_salva(): insere bordas de 3 pixels com o valor 255 na matriz imagem e salva como
     * um arquivo pgm na pasta rais do projeto;
     */
    
    public static void insere_borda_salva(){
        imagem.insereBordas(3, 255);
        escreverArquivo(geraNome("IMG_Borda"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método insere_borda_imprime_salva(): insere bordas de 3 pixels com o valor de 255 na matriz imagem,
     * imprime no terminal e salva como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void insere_borda_imprime_salva(){
        imagem.insereBordas(3, 255);
        System.out.println(imagem);
        escreverArquivo(geraNome("IMG_Borda"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     *  Método inverte_horizontalmente_salva(): inverte a matriz imagem horizontalmente e salva como
     *  um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void inverte_horizontalmente_salva(){
        imagem = imagem.inverterHorizontalmente();
        escreverArquivo(geraNome("IMG_InvertidaHoriz"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     *  Método inverte_horizontalmente_imprime_salva(): inverte a matriz imagem horizontalmente, imprime
     *  no terminal e salva como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void inverte_horizontalmente_imprime_salva(){
        imagem = imagem.inverterHorizontalmente(); 
        System.out.println(imagem);
        escreverArquivo(geraNome("IMG_InvertidaHoriz"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     *  Método inverte_verticalmente_salva(): inverte a matriz imagem verticalmente e salva como um arquivo
     *  pgm na pasta raiz do projeto;
     */
    
    public static void inverte_verticalmente_salva(){
        imagem = imagem.inverterVerticalmente(); 
        escreverArquivo(geraNome("IMG_InvertidaVert"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método inverte_verticalmente_imprime_salva(): inverte a matriz imagem verticalmente, imprime no terminal
     * e salva como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void inverte_verticalmente_imprime_salva(){
        imagem = imagem.inverterVerticalmente(); 
        System.out.println(imagem);
        escreverArquivo(geraNome("IMG_InvertidaVert"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método transposta_salva(): gera a matriz transposta da matriz imagem e salva como um arquivo pgm na pasta
     * raiz do projeto;
     */
    
    public static void transposta_salva(){
        imagem = imagem.transposta(); 
        escreverArquivo(geraNome("IMG_Transposta"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método transposta_imprime_salva(): gera a matriz transposta da matriz imagem, imprime no terminal e salva
     * como um arquivo pgm na pasta raiz do projeto;
     */
    
    public static void transposta_imprime_salva(){
        imagem = imagem.transposta();
        System.out.println(imagem);
        escreverArquivo(geraNome("IMG_Transposta"), imagem);
        menu_imagem(nome_imagem);
    }
    
    /**
     * Método menu_borda: imprime um menu para a inserção de bordas;
     * @param t Váriavel de controle para saber se a matriz será impressa ou não após
     * as bordas serem inseridas;
     */
    
    public static void menu_borda(boolean t){
        scan = new Scanner(System.in);
        int c = (imagem.linhas < imagem.colunas) ? (int)(imagem.linhas/2) : (int)(imagem.colunas/2);
        System.out.println("Insira o número de pixels desejado para a borda: (valor entre 1 e "+ c );
        int pix = scan.nextInt();
        System.out.println("Insira o tom da escala de ciza para a borda: (valor entre 0 e "+maxVal);
        int cor = scan.nextInt();
        
        if(pix < 1 || pix > c || cor<0 || cor > maxVal){
            System.out.println("Bordas não inseridas. Valores fora do permitido. ");
            menu_imagem(nome_imagem);
        }else{
            if(t){
                imagem.insereBordas(pix, cor);
                escreverArquivo(geraNome("IMG_Borda"), imagem);
                menu_imagem(nome_imagem);
            }else{
                imagem.insereBordas(pix, cor);
                System.out.println(imagem);
                escreverArquivo(geraNome("IMG_Borda"), imagem);
                menu_imagem(nome_imagem);
            }
        }
    }
    
    /**
     * <p>Método menu_imagem(): imprime o menu de processamento da imagem com todas as opções
     * <p>disponíveis para o processamento: <br>
     * <p>-Imprimir a imagem no terminal; <br>
     * <p>-Rotacionar a imagem 90º sentido horário  e salvar; <br>
     * <p>-Rotacionar a imagem 90º sentido horário, imprimir após rotacionar e salvar; <br>
     * <p>-Inverter as cores da imagem e salvar; inverter as cores da imagem, imprimir e salvar; <br>
     * <p>-Inserir bordas de 3pxl na imagem e salvar; inserir bordas de 3pxl, imprimir e salvar;<br>
     * <p>-Inserir bordas com largura a definir e salvar;<br>
     * <p>-Inserir bordas com largura a definir, imprimir e salvar;<br>
     * <p>-Inverter a imagem horizontalmente e salvar;<br>
     * <p>-Inverter a imagem horizontalmente, imprimir e salvar;<br>
     * <p>-Inverter a imagem verticalmente e salvar;<br>
     * <p>-Inverter a imagem verticalmente, imprimir e salvar;<br>
     * <p>-Gerar a transposta da imagem e salvar;<br>
     * <p>-Gerar a transposta da imagem, imprimir e salvar;<br>
     * @param nome Nome da imagem;
     */
    
    public static void menu_imagem(String nome){
        System.out.println("====================================OPÇÕES=====================================");
        System.out.println("Digite: ");
        System.out.println("1 - Para imprimir a imagem; ");
        System.out.println("2 - Para rotacionar a imagem (90º sentido horário) e salvar; ");
        System.out.println("3 - Para rotacionar a imagem (90º sentido horário), imprimir e salvar; ");
        System.out.println("4 - Para inverter as cores da imagem e salvar; ");
        System.out.println("5 - Para inverter as cores da imagem, imprimir e salvar; ");
        System.out.println("6 - Para inserir bordas (3 pixels) na imagem e salvar; ");
        System.out.println("7 - Para inserir bordas (3 pixels) na imagem, imprimir e salvar; ");
        System.out.println("8 - Para inserir bordas na imagem com largura a definir, e salvar; ");
        System.out.println("9 - Para inserir bordas na imagem com largura a definir, imprimir e salvar; ");
        System.out.println("10 - Para inverter a imagem horizontalmente e salvar; ");
        System.out.println("11 - Para inverter a imagem horizontalmente, imprimir e salvar; ");
        System.out.println("12 - Para inverter a imagem verticalmente e salvar; ");
        System.out.println("13 - Para inverter a imagem verticalmente, imprimir e salvar; ");
        System.out.println("14 - Para gerar a transposta da imagem e salvar;");
        System.out.println("15 - Para gerar a transposta da imagem, imprimir e salvar;");
        System.out.println("16 - Para retornar ao menu inicial;");
        System.out.println("17 - Para sair;");
        System.out.println("OBS.: AS IMAGENS SÃO SALVAS NA PASTA RAIZ DO PROJETO COM O NOME NO SEGUINTE FORMATO: \nIMG_Borda_'Data'.pgm, IMG_CorInvert_'Data'.pgm, etc.");
        System.out.println("-------------------------------------------------------------------------------");
        scan = new Scanner(System.in);
        int i = scan.nextInt();
        switch(i){
            case 1: imprime_imagem();break;
            case 2: rotaciona_salva();break;
            case 3: rotaciona_imprime_salva();break;
            case 4: inverte_cores_salva();break;
            case 5: inverte_cores_imprime_salva();break;
            case 6: insere_borda_salva();break;
            case 7: insere_borda_imprime_salva();break;
            case 8: menu_borda(true);break;
            case 9: menu_borda(false);break;
            case 10: inverte_horizontalmente_salva();break;
            case 11: inverte_horizontalmente_imprime_salva();break;
            case 12: inverte_verticalmente_salva();break;
            case 13: inverte_verticalmente_imprime_salva();break;
            case 14: transposta_salva();break;
            case 15: transposta_imprime_salva();break;
            case 16: menu();break;
            case 17: System.exit(0);break;
            default: System.exit(0);break;
        }
    }
    
    /**
     * Método ler_imagem(): lê um arquivo pgm para a Matriz Esparsa imagem;
     * @param arquivo Nome da imagem a ser lida;
     */
    
    public static void ler_imagem(String arquivo){
        File a = new File(arquivo);
        try {
            ler = new Scanner(a);
        } catch (FileNotFoundException ex) {
            System.err.println("O arquivo não foi encontrado!");
            ler_nome();
        }
        String tipo;
        int linhas, colunas;
        if(a.exists()){
            tipo = ler.next();
            if(!tipo.equals("P2")){
                System.err.println("Imagem não suportada. ");
                System.exit(1);
            }
            while(!ler.hasNextInt()){
                ler.next();
            }   
            colunas = ler.nextInt();
            linhas = ler.nextInt();
            maxVal = ler.nextInt();
            imagem = new MatrizEsparsa<>(linhas, colunas);

            for(int i = 0; i<linhas; i++){
                for(int j = 0; j<colunas; j++){
                    int b = ler.nextShort();
                    if(b!=0)
                        imagem.insere(b, i, j);
                }
            }
        }
       
    }
    
    /**
     * Método escreverArquivo(): escreve um arquivo no formato pgm e salva na pasta
     * raiz do projeto
     * @param nome Nome do arquivo a ser salvo;
     * @param imagem Matriz Esparsa contendo a informação a ser salva;
     */
    
    public static void escreverArquivo(String nome, MatrizEsparsa<Integer> imagem){
        BufferedWriter arquivo;
        try {
            arquivo = new BufferedWriter(new FileWriter(nome));
            String img = imagem.gerarStringPGM(maxVal);
            arquivo.write(img);
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método geraNome(): gera uma String em função da String passada por parâmetro e a data e hora atual;
     * @param t String inicial a ser concatenada com o nome gerado;
     * @return  String contendo um nome;
     */
    
    public static String geraNome(String t){
        Date data = new Date();
        Calendar hoje = Calendar.getInstance();
        hoje.setTime(data);
        return new StringBuilder().append(t).append("_").append(hoje.get(Calendar.DAY_OF_MONTH)).append("-").
                append(hoje.get(Calendar.MONTH)).append("-").append(hoje.get(Calendar.YEAR)).append("_").append(hoje.get(Calendar.HOUR)).
                append("h").append(hoje.get(Calendar.MINUTE)).append("m").append(hoje.get(Calendar.SECOND)).
                append("s").append(".pgm").toString();
    }
}
