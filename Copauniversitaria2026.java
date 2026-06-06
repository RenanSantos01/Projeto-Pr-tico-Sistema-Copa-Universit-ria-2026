// Autores: Renan Cardoso Portela dos Santos e Fabrício Cerqueira Almeida
// Projeto Pratico AV2: Sistema Copa Universitaria 2026
// Disciplina: BES005 - Logica de Programacao e Algoritmos
// UCSal - Universidade Catolica do Salvador

import java.util.Scanner;

public class CopaUniversitaria2026 {

    static Scanner scanner = new Scanner(System.in);

    // Constantes da competicao
    static final int NUM_SELECOES     = 5;   // 5 selecoes
    static final int NUM_JOGADORES    = 11;  // 11 jogadores por selecao
    static final int JOGOS_POR_SELECAO = 8;  // cada selecao joga 8 partidas

    // Dados das selecoes (indice 0 = selecao 1, indice 1 = selecao 2, ...)
    static String[] nomeSelecao  = new String[NUM_SELECOES];
    static int[]    pontosSelecao = new int[NUM_SELECOES];

    // Dados dos jogadores [indice_selecao 0-4][indice_jogador 0-10]
    static String[][] nomeJogador = new String[NUM_SELECOES][NUM_JOGADORES];
    static int[][]    golsJogador = new int[NUM_SELECOES][NUM_JOGADORES];

    // =================================================================
    // METODO PRINCIPAL
    // =================================================================

    public static void main(String[] args) {
        System.out.println("*==========================================*");
        System.out.println("*      COPA UNIVERSITARIA 2026 - UCSal    *");
        System.out.println("*==========================================*");

        // Etapa 1: Cadastrar as 5 selecoes
        cadastrarSelecoes();

        // Etapa 2: Cadastrar os 11 jogadores de cada selecao
        for (int i = 1; i <= NUM_SELECOES; i++) {
            cadastrarJogadores(i);
        }

        // Etapa 3: Registrar os resultados de todas as 20 partidas
        registrarPartidas();

        // Etapa 4: Menu de consultas (loop ate o usuario encerrar)
        menuConsultas();

        System.out.println("\nObrigado por usar o Sistema Copa Universitaria 2026!");
        scanner.close();
    }

    // =========================
    // FUNCOES OBRIGATORIAS 
    // =========================

    /**
     * Recebe o numero de inscricao da selecao (1 a 5) e retorna o nome.
     * Retorna "Invalido" se o numero estiver fora do intervalo valido.
     */
    static String verNomeSelecao(int numInscricao) {
        if (numInscricao < 1 || numInscricao > NUM_SELECOES) {
            return "Invalido";
        }
        return nomeSelecao[numInscricao - 1];
    }

    /**
     * Recebe o numero do jogador (1 a 11) e da selecao (1 a 5), retorna o nome do jogador.
     * Retorna "Invalido" se algum numero estiver fora do intervalo valido.
     * Retorna "Nao Localizado" se o jogador nao pertencer a selecao (caso necessario).
     */
    static String verNomeJogador(int numJogador, int numSelecao) {
        if (numSelecao < 1 || numSelecao > NUM_SELECOES) {
            return "Invalido";
        }
        if (numJogador < 1 || numJogador > NUM_JOGADORES) {
            return "Invalido";
        }
        return nomeJogador[numSelecao - 1][numJogador - 1];
    }

    /**
     * Recebe o nome da selecao e retorna o numero de inscricao (1 a 5).
     * Retorna -1 se a selecao nao for encontrada.
     */
    static int verNumInscricaoSelecao(String nome) {
        for (int i = 0; i < NUM_SELECOES; i++) {
            if (nomeSelecao[i] != null && nomeSelecao[i].equalsIgnoreCase(nome)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Recebe o nome do jogador e da selecao, retorna o numero de inscricao do jogador (como String).
     * Retorna "-1" se o jogador ou a selecao nao for encontrado.
     * Retorna "Nao Localizado" se o jogador existir, mas nao jogar pela selecao informada.
     * OBS: retorna String para suportar todos os valores de retorno exigidos pela especificacao.
     */
    static String verNumInscricaoJogador(String nomeJog, String nomeSel) {
        int numSel = verNumInscricaoSelecao(nomeSel);
        if (numSel == -1) {
            return "-1"; // selecao nao encontrada
        }

        int selIdx = numSel - 1;

        // Busca o jogador dentro da selecao informada
        for (int j = 0; j < NUM_JOGADORES; j++) {
            if (nomeJogador[selIdx][j] != null &&
                nomeJogador[selIdx][j].equalsIgnoreCase(nomeJog)) {
                return String.valueOf(j + 1); // encontrado!
            }
        }

        // Jogador nao encontrado na selecao: verifica se existe em outra selecao
        for (int i = 0; i < NUM_SELECOES; i++) {
            for (int j = 0; j < NUM_JOGADORES; j++) {
                if (nomeJogador[i][j] != null &&
                    nomeJogador[i][j].equalsIgnoreCase(nomeJog)) {
                    return "Nao Localizado"; // existe, mas nao e dessa selecao
                }
            }
        }

        return "-1"; // jogador nao existe em nenhuma selecao
    }

    /**
     * Recebe o nome da selecao e retorna o total de gols marcados pelos seus jogadores.
     * Retorna -1 se a selecao nao for encontrada.
     */
    static int verGolsSelecao(String nomeSel) {
        int numSel = verNumInscricaoSelecao(nomeSel);
        if (numSel == -1) {
            return -1;
        }
        int selIdx = numSel - 1;
        int total = 0;
        for (int j = 0; j < NUM_JOGADORES; j++) {
            total += golsJogador[selIdx][j];
        }
        return total;
    }

    /**
     * Recebe o nome do jogador e da selecao, retorna o numero de gols marcados pelo jogador.
     * Retorna -1 se o jogador ou a selecao nao for encontrado.
     */
    static int verGolsJogador(String nomeJog, String nomeSel) {
        int numSel = verNumInscricaoSelecao(nomeSel);
        if (numSel == -1) {
            return -1;
        }
        int selIdx = numSel - 1;
        for (int j = 0; j < NUM_JOGADORES; j++) {
            if (nomeJogador[selIdx][j] != null &&
                nomeJogador[selIdx][j].equalsIgnoreCase(nomeJog)) {
                return golsJogador[selIdx][j];
            }
        }
        return -1; // jogador nao encontrado
    }

    /**
     * Realiza o cadastro das 5 selecoes do campeonato.
     * Solicita o nome de cada selecao e inicia os pontos com 0.
     */
    static void cadastrarSelecoes() {
        System.out.println("\n=== CADASTRO DAS SELECOES ===");
        System.out.println("Informe o nome das 5 selecoes participantes:\n");

        for (int i = 0; i < NUM_SELECOES; i++) {
            System.out.print("Nome da Selecao " + (i + 1) + ": ");
            String nome = scanner.nextLine().trim();

            // Validacao: nome nao pode ser vazio
            while (nome.isEmpty()) {
                System.out.println("  Erro: o nome nao pode ser vazio!");
                System.out.print("Nome da Selecao " + (i + 1) + ": ");
                nome = scanner.nextLine().trim();
            }

            nomeSelecao[i]  = nome;
            pontosSelecao[i] = 0;
        }
        System.out.println("\nSelecoes cadastradas com sucesso!");
    }

    /**
     * Recebe o numero de inscricao da selecao e realiza o cadastro dos 11 jogadores.
     * Retorna false se o numero de inscricao for invalido; true em caso de sucesso.
     */
    static boolean cadastrarJogadores(int numInscricaoSelecao) {
        if (numInscricaoSelecao < 1 || numInscricaoSelecao > NUM_SELECOES) {
            System.out.println("Erro: numero de inscricao invalido!");
            return false;
        }

        int selIdx = numInscricaoSelecao - 1;
        System.out.println("\n=== CADASTRO DOS JOGADORES ===");
        System.out.println("Selecao: " + nomeSelecao[selIdx]);
        System.out.println("Informe o nome dos " + NUM_JOGADORES + " jogadores:\n");

        for (int j = 0; j < NUM_JOGADORES; j++) {
            System.out.print("Jogador " + (j + 1) + ": ");
            String nome = scanner.nextLine().trim();

            // Validacao: nome nao pode ser vazio
            while (nome.isEmpty()) {
                System.out.println("  Erro: o nome nao pode ser vazio!");
                System.out.print("Jogador " + (j + 1) + ": ");
                nome = scanner.nextLine().trim();
            }

            nomeJogador[selIdx][j] = nome;
            golsJogador[selIdx][j] = 0;
        }

        System.out.println("Jogadores cadastrados com sucesso!\n");
        return true;
    }

    // =================================================================
    // REGISTRO DE PARTIDAS
    // =================================================================

    /**
     * Gera e registra todas as 20 partidas do campeonato.
     * Formato: todos contra todos com jogo de ida e de volta.
     * Pares: (1x2, 2x1), (1x3, 3x1), (1x4, 4x1), (1x5, 5x1),
     *        (2x3, 3x2), (2x4, 4x2), (2x5, 5x2),
     *        (3x4, 4x3), (3x5, 5x3), (4x5, 5x4)
     */
    static void registrarPartidas() {
        System.out.println("\n*==========================================*");
        System.out.println("*           REGISTRO DAS PARTIDAS         *");
        System.out.println("*==========================================*");
        System.out.println("20 partidas no total (todos contra todos, ida e volta)\n");

        int numPartida = 1;

        for (int i = 0; i < NUM_SELECOES; i++) {
            for (int j = i + 1; j < NUM_SELECOES; j++) {

                // Jogo de ida: i recebe j
                System.out.println("\n--- Partida " + numPartida + "/20 ---");
                registrarResultado(i, j);
                numPartida++;

                // Jogo de volta: j recebe i
                System.out.println("\n--- Partida " + numPartida + "/20 ---");
                registrarResultado(j, i);
                numPartida++;
            }
        }

        System.out.println("\nTodas as partidas foram registradas com sucesso!");
    }

    /**
     * Registra o resultado de uma partida.
     * timeCasa e timeVisitante sao os indices das selecoes (0 a 4).
     */
    static void registrarResultado(int timeCasa, int timeVisitante) {
        System.out.println(nomeSelecao[timeCasa] + " x " + nomeSelecao[timeVisitante]);

        // Ler o placar (nao pode ser negativo)
        int golsCasa      = lerInteiroPositivo("  Gols de " + nomeSelecao[timeCasa] + ": ");
        int golsVisitante = lerInteiroPositivo("  Gols de " + nomeSelecao[timeVisitante] + ": ");

        // Registrar os goleadores de cada time
        if (golsCasa > 0) {
            System.out.println("\n  Registrando gols de " + nomeSelecao[timeCasa] + ":");
            registrarGoleadores(timeCasa, golsCasa);
        }
        if (golsVisitante > 0) {
            System.out.println("\n  Registrando gols de " + nomeSelecao[timeVisitante] + ":");
            registrarGoleadores(timeVisitante, golsVisitante);
        }

        // Atualizar pontuacao conforme o resultado
        if (golsCasa > golsVisitante) {
            pontosSelecao[timeCasa] += 3;
            System.out.println("\n  Resultado: Vitoria de " + nomeSelecao[timeCasa] + "! (+3 pontos)");
        } else if (golsVisitante > golsCasa) {
            pontosSelecao[timeVisitante] += 3;
            System.out.println("\n  Resultado: Vitoria de " + nomeSelecao[timeVisitante] + "! (+3 pontos)");
        } else {
            pontosSelecao[timeCasa]      += 1;
            pontosSelecao[timeVisitante] += 1;
            System.out.println("\n  Resultado: Empate! (+1 ponto para cada selecao)");
        }
    }

    /**
     * Para cada gol de uma selecao, pergunta se foi gol contra.
     * Se nao foi gol contra, solicita o nome do jogador que marcou
     * e atualiza a contagem de gols dele.
     */
    static void registrarGoleadores(int selIdx, int numGols) {
        for (int g = 1; g <= numGols; g++) {
            System.out.println("    Gol " + g + ":");
            System.out.print("    Foi gol contra? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();

            // Validacao da resposta s/n
            while (!resp.equals("s") && !resp.equals("n")) {
                System.out.println("    Resposta invalida! Digite 's' ou 'n'.");
                System.out.print("    Foi gol contra? (s/n): ");
                resp = scanner.nextLine().trim().toLowerCase();
            }

            if (resp.equals("s")) {
                System.out.println("    Gol contra registrado (nao atribuido a nenhum jogador).");
                continue; // vai para o proximo gol
            }

            // Pede o nome do jogador; repete ate encontrar um valido
            boolean encontrado = false;
            while (!encontrado) {
                System.out.print("    Nome do jogador que marcou: ");
                String nomeGoleador = scanner.nextLine().trim();

                if (nomeGoleador.isEmpty()) {
                    System.out.println("    Erro: o nome nao pode ser vazio!");
                    continue;
                }

                // Busca o jogador na selecao correta
                for (int j = 0; j < NUM_JOGADORES; j++) {
                    if (nomeJogador[selIdx][j].equalsIgnoreCase(nomeGoleador)) {
                        golsJogador[selIdx][j]++;
                        System.out.println("    Gol registrado para " + nomeJogador[selIdx][j] + "!");
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("    Jogador nao encontrado em " + nomeSelecao[selIdx] + "!");
                    System.out.println("    Jogadores disponiveis:");
                    for (int j = 0; j < NUM_JOGADORES; j++) {
                        System.out.println("      " + (j + 1) + ". " + nomeJogador[selIdx][j]);
                    }
                }
            }
        }
    }

    // =================================================================
    // MENU DE CONSULTAS
    // =================================================================

    static void menuConsultas() {
        int opcao;
        do {
            System.out.println("\n*==========================================*");
            System.out.println("*             MENU DE CONSULTAS           *");
            System.out.println("*==========================================*");
            System.out.println("1. Selecao vencedora");
            System.out.println("2. Artilheiro(s) do torneio");
            System.out.println("3. % de jogadores com mais de 5 gols");
            System.out.println("4. % de jogadores de uma selecao com >= 1 gol");
            System.out.println("5. Desempenho de todas as selecoes");
            System.out.println("6. Desempenho de todos os jogadores");
            System.out.println("7. Ranking das selecoes [BONUS]");
            System.out.println("0. Encerrar programa");

            opcao = lerOpcaoMenu("Escolha uma opcao: ", 0, 7);

            switch (opcao) {
                case 1: consultarVencedora();          break;
                case 2: consultarArtilheiros();        break;
                case 3: percentualMaisCincoGols();     break;
                case 4: percentualPeloMenosUmGol();    break;
                case 5: listarDesempenhoSelecoes();    break;
                case 6: listarDesempenhoJogadores();   break;
                case 7: rankingSelecoes();             break;
                case 0: System.out.println("Encerrando o programa..."); break;
            }
        } while (opcao != 0);
    }

    // =================================================================
    // IMPLEMENTACAO DE CADA CONSULTA
    // =================================================================

    /**
     * Exibe a selecao vencedora.
     * Criterios de desempate (nessa ordem):
     *   1. Maior pontuacao
     *   2. Maior numero de gols marcados pelos jogadores
     *   3. Menor numero de inscricao
     */
    static void consultarVencedora() {
        int vencedorIdx = 0; // comeca assumindo que a selecao 1 e a vencedora

        for (int i = 1; i < NUM_SELECOES; i++) {
            boolean substituir = false;

            if (pontosSelecao[i] > pontosSelecao[vencedorIdx]) {
                // Mais pontos: substitui
                substituir = true;
            } else if (pontosSelecao[i] == pontosSelecao[vencedorIdx]) {
                // Empate em pontos: desempata pelo maior numero de gols
                int golsI    = calcularGolsSelecao(i);
                int golsVenc = calcularGolsSelecao(vencedorIdx);
                if (golsI > golsVenc) {
                    substituir = true;
                }
                // Se gols tambem empatarem: mantem a de menor inscricao
                // (vencedorIdx ja tem indice menor, entao nao faz nada)
            }

            if (substituir) {
                vencedorIdx = i;
            }
        }

        System.out.println("\n=== SELECAO VENCEDORA ===");
        System.out.println("Nome        : " + nomeSelecao[vencedorIdx]);
        System.out.println("Pontuacao   : " + pontosSelecao[vencedorIdx] + " ponto(s)");
        System.out.println("Total de gols: " + calcularGolsSelecao(vencedorIdx));
    }

    /**
     * Exibe o(s) artilheiro(s) do torneio.
     * Em caso de empate, exibe todos os jogadores com o mesmo numero de gols.
     * Exibe tambem a media de gols por partida (total de gols / 8 jogos).
     */
    static void consultarArtilheiros() {
        // Encontra o maximo de gols entre todos os jogadores
        int maxGols = 0;
        for (int i = 0; i < NUM_SELECOES; i++) {
            for (int j = 0; j < NUM_JOGADORES; j++) {
                if (golsJogador[i][j] > maxGols) {
                    maxGols = golsJogador[i][j];
                }
            }
        }

        System.out.println("\n=== ARTILHEIRO(S) DO TORNEIO ===");

        if (maxGols == 0) {
            System.out.println("Nenhum gol foi marcado na competicao.");
            return;
        }

        double media = (double) maxGols / JOGOS_POR_SELECAO;

        for (int i = 0; i < NUM_SELECOES; i++) {
            for (int j = 0; j < NUM_JOGADORES; j++) {
                if (golsJogador[i][j] == maxGols) {
                    System.out.printf("Jogador: %-20s | Selecao: %-15s | Gols: %2d | Media: %.2f/jogo%n",
                            nomeJogador[i][j], nomeSelecao[i], maxGols, media);
                }
            }
        }
    }

    /**
     * Exibe o percentual de jogadores (de todas as selecoes) com mais de 5 gols.
     */
    static void percentualMaisCincoGols() {
        int totalJogadores = NUM_SELECOES * NUM_JOGADORES; // 55 jogadores
        int contador = 0;

        for (int i = 0; i < NUM_SELECOES; i++) {
            for (int j = 0; j < NUM_JOGADORES; j++) {
                if (golsJogador[i][j] > 5) {
                    contador++;
                }
            }
        }

        double percentual = (double) contador / totalJogadores * 100;
        System.out.println("\n=== JOGADORES COM MAIS DE 5 GOLS ===");
        System.out.println("Total de jogadores no torneio: " + totalJogadores);
        System.out.println("Com mais de 5 gols           : " + contador);
        System.out.printf( "Percentual                   : %.2f%%%n", percentual);
    }

    /**
     * Exibe o percentual de jogadores de uma selecao especifica com pelo menos 1 gol.
     */
    static void percentualPeloMenosUmGol() {
        System.out.print("\nNome da selecao: ");
        String nome = scanner.nextLine().trim();

        int numSel = verNumInscricaoSelecao(nome);
        if (numSel == -1) {
            System.out.println("Erro: selecao \"" + nome + "\" nao encontrada!");
            return;
        }

        int selIdx = numSel - 1;
        int contador = 0;

        for (int j = 0; j < NUM_JOGADORES; j++) {
            if (golsJogador[selIdx][j] >= 1) {
                contador++;
            }
        }

        double percentual = (double) contador / NUM_JOGADORES * 100;
        System.out.println("\n=== JOGADORES DE " + nomeSelecao[selIdx].toUpperCase() + " COM >= 1 GOL ===");
        System.out.println("Total de jogadores: " + NUM_JOGADORES);
        System.out.println("Com pelo menos 1 gol: " + contador);
        System.out.printf( "Percentual          : %.2f%%%n", percentual);
    }

    /**
     * Lista o nome, pontuacao e media de gols por partida de todas as selecoes.
     */
    static void listarDesempenhoSelecoes() {
        System.out.println("\n=== DESEMPENHO DE TODAS AS SELECOES ===");
        System.out.printf("%-4s %-20s %8s %12s%n", "Nr.", "Selecao", "Pontos", "Media Gols");
        System.out.println("-".repeat(48));

        for (int i = 0; i < NUM_SELECOES; i++) {
            int gols   = calcularGolsSelecao(i);
            double media = (double) gols / JOGOS_POR_SELECAO;
            System.out.printf("%-4d %-20s %8d %12.2f%n",
                    (i + 1), nomeSelecao[i], pontosSelecao[i], media);
        }
    }

    /**
     * Lista o nome, selecao e numero de gols de cada jogador.
     */
    static void listarDesempenhoJogadores() {
        System.out.println("\n=== DESEMPENHO DE TODOS OS JOGADORES ===");

        for (int i = 0; i < NUM_SELECOES; i++) {
            System.out.println("\n  Selecao: " + nomeSelecao[i]);
            System.out.printf("  %-4s %-25s %5s%n", "Nr.", "Nome", "Gols");
            System.out.println("  " + "-".repeat(36));

            for (int j = 0; j < NUM_JOGADORES; j++) {
                System.out.printf("  %-4d %-25s %5d%n",
                        (j + 1), nomeJogador[i][j], golsJogador[i][j]);
            }
        }
    }

    /**
     * BONUS: Exibe o ranking das selecoes ordenadas por pontuacao (maior para menor).
     * Utiliza Bubble Sort para ordenar sem alterar os dados originais.
     */
    static void rankingSelecoes() {
        // Array com os indices das selecoes (serao ordenados pelo Bubble Sort)
        int[] ranking = {0, 1, 2, 3, 4};

        // Bubble Sort decrescente por pontuacao
        for (int i = 0; i < NUM_SELECOES - 1; i++) {
            for (int j = 0; j < NUM_SELECOES - 1 - i; j++) {
                if (pontosSelecao[ranking[j]] < pontosSelecao[ranking[j + 1]]) {
                    // Troca os indices
                    int temp      = ranking[j];
                    ranking[j]    = ranking[j + 1];
                    ranking[j + 1] = temp;
                }
            }
        }

        System.out.println("\n=== RANKING DAS SELECOES ===");
        System.out.printf("%-9s %-20s %8s%n", "Posicao", "Selecao", "Pontos");
        System.out.println("-".repeat(40));

        for (int pos = 0; pos < NUM_SELECOES; pos++) {
            int idx = ranking[pos];
            System.out.printf("%-9d %-20s %8d%n",
                    (pos + 1), nomeSelecao[idx], pontosSelecao[idx]);
        }
    }

    // =================================================================
    // FUNCAO AUXILIAR INTERNA
    // =================================================================

    /**
     * Calcula o total de gols marcados pelos jogadores de uma selecao.
     * Recebe o indice da selecao (0 a 4).
     */
    static int calcularGolsSelecao(int selIdx) {
        int total = 0;
        for (int j = 0; j < NUM_JOGADORES; j++) {
            total += golsJogador[selIdx][j];
        }
        return total;
    }

    // =================================================================
    // FUNCOES DE LEITURA COM VALIDACAO DE ENTRADA
    // =================================================================

    /**
     * Le um numero inteiro >= 0 do teclado.
     * Repete a solicitacao ate o usuario digitar um valor valido.
     */
    static int lerInteiroPositivo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= 0) {
                    return valor;
                } else {
                    System.out.println("  Erro: o numero de gols nao pode ser negativo!");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Erro: digite um numero inteiro valido!");
            }
        }
    }

    /**
     * Le uma opcao do menu entre min e max.
     * Repete a solicitacao ate o usuario digitar um valor dentro do intervalo.
     */
    static int lerOpcaoMenu(String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("  Opcao invalida! Escolha entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Erro: digite um numero inteiro valido!");
            }
        }
    }
}