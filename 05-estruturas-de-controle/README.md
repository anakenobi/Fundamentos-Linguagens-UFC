# Sistema de Biblioteca em Java
## Demonstração de Estruturas de Seleção, Repetição e Controle de Fluxo

---

## 📋 Índice
- [Visão Geral](#-visão-geral)
- [Estruturas Demonstradas](#-estruturas-demonstradas)
- [Código Principal](#-código-principal)
- [Classe Livro](#-classe-livro)
- [Análise das Estruturas](#-análise-das-estruturas)


---

## 🎯 Visão Geral

Este programa implementa um **Sistema de Gerenciamento de Biblioteca** que demonstra o uso prático de todas as principais estruturas de controle em Java:

### Funcionalidades:
- 📚 Listar todos os livros
- 🔍 Buscar livros por título/autor
- 📖 Emprestar livros
- 📥 Devolver livros
- ➕ Adicionar novos livros
- 📊 Gerar relatórios estatísticos

---

## 🔧 Estruturas Demonstradas

| Estrutura | Tipo | Onde é Usado |
|-----------|------|--------------|
| **if/else** | Seleção | Validações e verificações |
| **switch/case** | Seleção | Menu principal |
| **while** | Repetição | Menu e validações |
| **for** | Repetição | Listagens e contadores |
| **for-each** | Repetição | Percorrer coleções |
| **do-while** | Repetição | Devolução de livros |
| **break** | Controle | Sair de loops |
| **continue** | Controle | Pular iterações |
| **return** | Controle | Retorno antecipado |

---

## 💻 Código Principal

### SistemaBiblioteca.java

```java
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de Gerenciamento de Biblioteca
 * Demonstra estruturas de seleção, repetição e controle de fluxo em Java
 */
public class SistemaBiblioteca {
    
    // Lista para armazenar os livros
    private static List<Livro> biblioteca = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Inicializar com alguns livros
        inicializarBiblioteca();
        
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("Bem-vindo ao sistema de gerenciamento!");
        
        // ESTRUTURA DE REPETIÇÃO: while loop principal
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcao = lerOpcao();
            
            // ESTRUTURA DE SELEÇÃO: switch-case
            switch (opcao) {
                case 1:
                    listarLivros();
                    break;  // CONTROLE DE FLUXO: break
                case 2:
                    buscarLivro();
                    break;
                case 3:
                    emprestarLivro();
                    break;
                case 4:
                    devolverLivro();
                    break;
                case 5:
                    adicionarLivro();
                    break;
                case 6:
                    relatorioBiblioteca();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
            }
            
            // Pausa para melhor experiência do usuário
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Inicializa a biblioteca com alguns livros
     */
    private static void inicializarBiblioteca() {
        biblioteca.add(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, true));
        biblioteca.add(new Livro("1984", "George Orwell", 1949, true));
        biblioteca.add(new Livro("Dom Casmurro", "Machado de Assis", 1899, false));
        biblioteca.add(new Livro("Harry Potter", "J.K. Rowling", 1997, true));
        biblioteca.add(new Livro("O Alquimista", "Paulo Coelho", 1988, false));
    }
    
    /**
     * Mostra o menu principal
     */
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MENU PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1. 📚 Listar todos os livros");
        System.out.println("2. 🔍 Buscar livro");
        System.out.println("3. 📖 Emprestar livro");
        System.out.println("4. 📥 Devolver livro");
        System.out.println("5. ➕ Adicionar novo livro");
        System.out.println("6. 📊 Relatório da biblioteca");
        System.out.println("0. 🚪 Sair");
        System.out.println("=".repeat(40));
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Lê e valida a opção do usuário
     */
    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine().trim());
            return opcao;
        } catch (NumberFormatException e) {
            return -1; // Retorna valor inválido para tratar no switch
        }
    }
    
    /**
     * Lista todos os livros da biblioteca
     * DEMONSTRA: for-each loop e estruturas condicionais
     */
    private static void listarLivros() {
        System.out.println("\n📚 LISTA DE LIVROS:");
        System.out.println("-".repeat(50));
        
        // ESTRUTURA DE SELEÇÃO: if simples
        if (biblioteca.isEmpty()) {
            System.out.println("Nenhum livro encontrado na biblioteca.");
            return;  // CONTROLE DE FLUXO: return antecipado
        }
        
        // ESTRUTURA DE REPETIÇÃO: for-each loop
        int contador = 1;
        for (Livro livro : biblioteca) {
            // OPERADOR TERNÁRIO (forma compacta de if-else)
            String status = livro.isDisponivel() ? "✅ Disponível" : "❌ Emprestado";
            System.out.printf("%d. %s\n", contador++, livro.toString());
            System.out.printf("   Status: %s\n", status);
            System.out.println();
        }
    }
    
    /**
     * Busca livros por título ou autor
     * DEMONSTRA: for tradicional, if-else aninhado, continue
     */
    private static void buscarLivro() {
        System.out.print("\n🔍 Digite o título ou autor para buscar: ");
        String termo = scanner.nextLine().trim().toLowerCase();
        
        // ESTRUTURA DE SELEÇÃO: validação de entrada
        if (termo.isEmpty()) {
            System.out.println("❌ Termo de busca não pode estar vazio!");
            return;
        }
        
        System.out.println("\nResultados da busca:");
        System.out.println("-".repeat(30));
        
        boolean encontrou = false;
        
        // ESTRUTURA DE REPETIÇÃO: for tradicional
        for (int i = 0; i < biblioteca.size(); i++) {
            Livro livro = biblioteca.get(i);
            
            // ESTRUTURA CONDICIONAL COMPLEXA com CONTINUE
            if (!livro.getTitulo().toLowerCase().contains(termo) && 
                !livro.getAutor().toLowerCase().contains(termo)) {
                continue; // CONTROLE DE FLUXO: pula para próxima iteração
            }
            
            // Se chegou aqui, encontrou uma correspondência
            encontrou = true;
            String status = livro.isDisponivel() ? "✅ Disponível" : "❌ Emprestado";
            System.out.printf("%d. %s - Status: %s\n", i + 1, livro.toString(), status);
        }
        
        // ESTRUTURA DE SELEÇÃO: if-else
        if (!encontrou) {
            System.out.println("❌ Nenhum livro encontrado com o termo: " + termo);
        } else {
            System.out.println("\n✅ Busca concluída!");
        }
    }
    
    /**
     * Empresta um livro
     * DEMONSTRA: while loop infinito, break, validação
     */
    private static void emprestarLivro() {
        System.out.println("\n📖 EMPRÉSTIMO DE LIVRO");
        
        // Filtra livros disponíveis
        List<Livro> livrosDisponiveis = new ArrayList<>();
        for (Livro livro : biblioteca) {
            if (livro.isDisponivel()) {
                livrosDisponiveis.add(livro);
            }
        }
        
        // ESTRUTURA DE SELEÇÃO: verificação de disponibilidade
        if (livrosDisponiveis.isEmpty()) {
            System.out.println("❌ Não há livros disponíveis para empréstimo.");
            return;
        }
        
        // Mostra livros disponíveis
        System.out.println("Livros disponíveis:");
        for (int i = 0; i < livrosDisponiveis.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, livrosDisponiveis.get(i).toString());
        }
        
        // ESTRUTURA DE REPETIÇÃO: while infinito com break
        while (true) {
            System.out.print("Digite o número do livro (0 para cancelar): ");
            
            try {
                int escolha = Integer.parseInt(scanner.nextLine().trim());
                
                // ESTRUTURAS DE SELEÇÃO MÚLTIPLAS
                if (escolha == 0) {
                    System.out.println("Empréstimo cancelado.");
                    break; // CONTROLE DE FLUXO: sai do loop while
                } else if (escolha < 1 || escolha > livrosDisponiveis.size()) {
                    System.out.println("❌ Número inválido! Tente novamente.");
                    continue; // CONTROLE DE FLUXO: volta ao início do loop
                } else {
                    // Empresta o livro
                    Livro livroEscolhido = livrosDisponiveis.get(escolha - 1);
                    livroEscolhido.setDisponivel(false);
                    System.out.printf("✅ Livro '%s' emprestado com sucesso!\n", 
                                    livroEscolhido.getTitulo());
                    break; // CONTROLE DE FLUXO: sai do loop while
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite apenas números!");
            }
        }
    }
    
    /**
     * Devolve um livro emprestado
     * DEMONSTRA: do-while loop
     */
    private static void devolverLivro() {
        System.out.println("\n📥 DEVOLUÇÃO DE LIVRO");
        
        // Lista livros emprestados
        List<Livro> livrosEmprestados = new ArrayList<>();
        for (Livro livro : biblioteca) {
            if (!livro.isDisponivel()) {
                livrosEmprestados.add(livro);
            }
        }
        
        if (livrosEmprestados.isEmpty()) {
            System.out.println("❌ Não há livros emprestados para devolver.");
            return;
        }
        
        System.out.println("Livros emprestados:");
        for (int i = 0; i < livrosEmprestados.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, livrosEmprestados.get(i).toString());
        }
        
        // ESTRUTURA DE REPETIÇÃO: do-while (executa pelo menos uma vez)
        int escolha;
        do {
            System.out.print("Digite o número do livro para devolver (0 para cancelar): ");
            
            try {
                escolha = Integer.parseInt(scanner.nextLine().trim());
                
                if (escolha == 0) {
                    System.out.println("Devolução cancelada.");
                    return;
                } else if (escolha >= 1 && escolha <= livrosEmprestados.size()) {
                    Livro livroDevolvido = livrosEmprestados.get(escolha - 1);
                    livroDevolvido.setDisponivel(true);
                    System.out.printf("✅ Livro '%s' devolvido com sucesso!\n", 
                                    livroDevolvido.getTitulo());
                    return;
                } else {
                    System.out.println("❌ Número inválido!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite apenas números!");
                escolha = -1; // Força continuar o loop
            }
        } while (true);
    }
    
    /**
     * Adiciona um novo livro à biblioteca
     * DEMONSTRA: validação de entrada, estruturas condicionais complexas
     */
    private static void adicionarLivro() {
        System.out.println("\n➕ ADICIONAR NOVO LIVRO");
        
        System.out.print("Título do livro: ");
        String titulo = scanner.nextLine().trim();
        
        System.out.print("Autor do livro: ");
        String autor = scanner.nextLine().trim();
        
        // ESTRUTURA DE SELEÇÃO: validação com operadores lógicos
        if (titulo.isEmpty() || autor.isEmpty()) {
            System.out.println("❌ Título e autor são obrigatórios!");
            return;
        }
        
        int ano = 0;
        boolean anoValido = false;
        
        // ESTRUTURA DE REPETIÇÃO: while para validar ano
        while (!anoValido) {
            System.out.print("Ano de publicação: ");
            try {
                ano = Integer.parseInt(scanner.nextLine().trim());
                
                // OPERADOR TERNÁRIO + ESTRUTURA CONDICIONAL
                anoValido = (ano > 0 && ano <= 2024) ? true : false;
                
                if (!anoValido) {
                    System.out.println("❌ Ano deve estar entre 1 e 2024!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, digite um ano válido!");
            }
        }
        
        // Adiciona o livro (sempre disponível inicialmente)
        Livro novoLivro = new Livro(titulo, autor, ano, true);
        biblioteca.add(novoLivro);
        
        System.out.printf("✅ Livro '%s' adicionado com sucesso!\n", titulo);
    }
    
    /**
     * Gera relatório da biblioteca
     * DEMONSTRA: loops aninhados, contadores, estruturas condicionais complexas
     */
    private static void relatorioBiblioteca() {
        System.out.println("\n📊 RELATÓRIO DA BIBLIOTECA");
        System.out.println("=".repeat(50));
        
        int totalLivros = biblioteca.size();
        int livrosDisponiveis = 0;
        int livrosEmprestados = 0;
        int anoMaisAntigo = Integer.MAX_VALUE;
        int anoMaisRecente = Integer.MIN_VALUE;
        
        // ESTRUTURA DE REPETIÇÃO: for-each para calcular estatísticas
        for (Livro livro : biblioteca) {
            // ESTRUTURA CONDICIONAL para contagem
            if (livro.isDisponivel()) {
                livrosDisponiveis++;
            } else {
                livrosEmprestados++;
            }
            
            // ESTRUTURAS CONDICIONAIS para encontrar extremos
            if (livro.getAno() < anoMaisAntigo) {
                anoMaisAntigo = livro.getAno();
            }
            if (livro.getAno() > anoMaisRecente) {
                anoMaisRecente = livro.getAno();
            }
        }
        
        // Exibe estatísticas básicas
        System.out.printf("📚 Total de livros: %d\n", totalLivros);
        System.out.printf("✅ Livros disponíveis: %d\n", livrosDisponiveis);
        System.out.printf("❌ Livros emprestados: %d\n", livrosEmprestados);
        
        // ESTRUTURA CONDICIONAL para evitar valores inválidos
        if (totalLivros > 0) {
            System.out.printf("📅 Livro mais antigo: %d\n", anoMaisAntigo);
            System.out.printf("📅 Livro mais recente: %d\n", anoMaisRecente);
            
            // Cálculo de porcentagem
            double percentualDisponivel = (double) livrosDisponiveis / totalLivros * 100;
            System.out.printf("📈 Percentual disponível: %.1f%%\n", percentualDisponivel);
        }
        
        // ANÁLISE POR DÉCADA: demonstra loops aninhados
        System.out.println("\n📊 Livros por década:");
        int[] decadas = new int[13]; // 1900-2020+ (índices 0-12)
        
        // LOOP PARA CONTAR POR DÉCADA
        for (Livro livro : biblioteca) {
            int decada = (livro.getAno() / 10) * 10; // Arredonda para década
            int indice = Math.max(0, Math.min(12, (decada - 1900) / 10));
            decadas[indice]++;
        }
        
        // LOOP PARA EXIBIR ESTATÍSTICAS
        for (int i = 0; i < decadas.length; i++) {
            if (decadas[i] > 0) {
                int anoDecada = 1900 + (i * 10);
                String simbolos = "█".repeat(decadas[i]); // Gráfico simples
                System.out.printf("%ds: %d livros %s\n", anoDecada, decadas[i], simbolos);
            }
        }
    }
}
```

---

## 📚 Classe Livro

```java
/**
 * Classe auxiliar para representar um livro
 * Demonstra encapsulamento e métodos básicos
 */
class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private boolean disponivel;
    
    // CONSTRUTOR
    public Livro(String titulo, String autor, int ano, boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.disponivel = disponivel;
    }
    
    // MÉTODOS GETTERS E SETTERS
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAno() { return ano; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    
    // MÉTODO toString() SOBRESCRITO
    @Override
    public String toString() {
        return String.format("'%s' por %s (%d)", titulo, autor, ano);
    }
}
```

---

## 🔍 Análise das Estruturas

### 🎛️ **Estruturas de Seleção**

#### **1. if/else Simples**
```java
if (biblioteca.isEmpty()) {
    System.out.println("Nenhum livro encontrado na biblioteca.");
    return;
}
```

#### **2. if/else Composto**
```java
if (escolha == 0) {
    System.out.println("Empréstimo cancelado.");
    break;
} else if (escolha < 1 || escolha > livrosDisponiveis.size()) {
    System.out.println("❌ Número inválido!");
    continue;
} else {
    // Processar empréstimo
}
```

#### **3. switch/case**
```java
switch (opcao) {
    case 1:
        listarLivros();
        break;
    case 2:
        buscarLivro();
        break;
    // ... outros cases
    default:
        System.out.println("❌ Opção inválida!");
}
```

#### **4. Operador Ternário**
```java
String status = livro.isDisponivel() ? "✅ Disponível" : "❌ Emprestado";
```

### 🔄 **Estruturas de Repetição**

#### **1. while - Loop Principal**
```java
boolean continuar = true;
while (continuar) {
    mostrarMenu();
    // ... processar opção
}
```

#### **2. for Tradicional**
```java
for (int i = 0; i < biblioteca.size(); i++) {
    Livro livro = biblioteca.get(i);
    // ... processar livro
}
```

#### **3. for-each (Enhanced for)**
```java
for (Livro livro : biblioteca) {
    if (livro.isDisponivel()) {
        livrosDisponiveis++;
    }
}
```

#### **4. do-while**
```java
int escolha;
do {
    System.out.print("Digite o número do livro: ");
    // ... processar entrada
} while (true);
```

### ⚡ **Controle de Fluxo**

#### **1. break - Sair de Loops**
```java
if (escolha == 0) {
    System.out.println("Operação cancelada.");
    break; // Sai do loop while
}
```

#### **2. continue - Pular Iteração**
```java
if (!livro.getTitulo().toLowerCase().contains(termo)) {
    continue; // Pula para próxima iteração do for
}
```

#### **3. return - Retorno Antecipado**
```java
if (termo.isEmpty()) {
    System.out.println("❌ Termo não pode estar vazio!");
    return; // Sai do método imediatamente
}
```

---



