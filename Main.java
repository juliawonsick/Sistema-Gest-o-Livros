// TRABALHO EM DUPLA: JÚLIA WONSICK PAZZINATTO - RA: 1136562 E LAURA CEMIN IORA - RA: 1131015

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    static Biblioteca biblio = new Biblioteca();
    static Scanner input = new Scanner(System.in);

    private static int inputNumerico(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        System.out.println(mensagem);
        do {
            String valorStr = input.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Erro, por favor informe um número inteiro");
            }
        } while (!entradaValida);
        return valor;
    }

    public static void clear() {
        // Limpa o console usando sequências de escape ANSI
        System.out.print("\033[H\033[2J");
        System.out.flush(); // Garante que o buffer de saída seja limpo
    }

    private static void listar() {
        clear(); // Limpa a tela ao listar
        List<Livro> livros = biblio.pesquisarTodos();
        livros.sort(Comparator.comparing(Livro::getTitulo));
        System.out.println("======== LISTA DE LIVROS =========");
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            for (Livro livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Ano: " + livro.getAnoPublicacao());
                System.out.println("N. Páginas: " + livro.getnPaginas());
                System.out.println("-------------------------");
            }
        }
        System.out.println("Pressione Enter para continuar...");
        input.nextLine(); // espera o usuário dar um enter para continuar
    }

    private static void adicionar() {
        clear(); // Limpa a tela ao adicionar
        Livro novoLivro = new Livro();
        System.out.println("======== ADICIONANDO NOVO LIVRO ========");
        System.out.print("Informe o título do livro: ");
        novoLivro.setTitulo(input.nextLine());

        System.out.print("Informe o nome do autor: ");
        novoLivro.setAutor(input.nextLine());

        int anoPublicacao;
        while (true) {
            System.out.print("Informe o ano de publicação (1400 a " + LocalDate.now().getYear() + "): ");
            anoPublicacao = inputNumerico("");
            if (anoPublicacao < 1400 || anoPublicacao > LocalDate.now().getYear()) {
                System.out.println("ERRO: Livros anteriores a 1400 e superiores a " + LocalDate.now().getYear() + " não podem ser cadastrados.");
            } else {
                break; // Sai do loop se o ano for válido
            }
        }
        novoLivro.setAnoPublicacao(anoPublicacao);
        novoLivro.setnPaginas(inputNumerico("Informe o número de páginas: "));

        try {
            biblio.adicionar(novoLivro);
            System.out.println("Livro adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        System.out.println("Pressione Enter para continuar...");
        input.nextLine(); // espera o usuário dar um enter para continuar
    }

    private static void pesquisar() {
        clear(); // Limpa a tela ao pesquisar
        System.out.print("Informe o título do livro a ser pesquisado: ");
        String titulo = input.nextLine();
        List<Livro> livrosEncontrados = biblio.pesquisarPorTitulo(titulo);
        
        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título informado.");
        } else {
            System.out.println("======== LIVROS ENCONTRADOS =========");
            for (Livro livro : livrosEncontrados) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Ano: " + livro.getAnoPublicacao());
                System.out.println("N. Páginas: " + livro.getnPaginas());
                System.out.println("-------------------------");
            }
        }
        System.out.println("Pressione Enter para continuar...");
        input.nextLine(); // espera o usuário dar um enter para continuar
    }

    private static void remover() {
        clear(); // Limpa a tela ao remover
        System.out.print("Informe o título do livro a ser removido: ");
        String titulo = input.nextLine();
        
        List<Livro> livrosAntes = biblio.pesquisarPorTitulo(titulo);
        if (livrosAntes.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título informado.");
        } else {
            biblio.removerPorTitulo(titulo);
            System.out.println("Livro removido com sucesso.");
        }
        System.out.println("Pressione Enter para continuar...");
        input.nextLine(); // espera o usuário dar um enter para continuar
    }

    public static void main(String[] args) {
        String menu = """
                SISTEMA DE GERENCIAMENTO DE BIBLIOTECA
                Escolha uma das opções:
                1 - Adicionar novo livro;
                2 - Listar todos os livros;
                3 - Pesquisar livro;
                4 - Remover livro;
                0 - Sair;
                """;
        
        int opcao;
        do {
            clear(); // Limpa a tela ao carregar o menu
            System.out.println(menu);
            opcao = inputNumerico("Escolha uma opção:");
            switch (opcao) {
                case 0:
                    System.out.println("VOLTE SEMPRE!!!");
                    break;
                case 1:
                    adicionar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    pesquisar();
                    break;
                case 4:
                    remover();
                    break;
                default:
                    System.out.println("Opção Inválida!!!");
                    break;
            }
        } while (opcao != 0);
    }
}