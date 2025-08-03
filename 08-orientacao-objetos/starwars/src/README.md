# Sistema de Personagens Star Wars 🌟

Um projeto Java que implementa um sistema de hierarquia de classes baseado no universo Star Wars, demonstrando conceitos de Orientação a Objetos como herança, polimorfismo e encapsulamento.

## 📋 Descrição

Este projeto modela diferentes tipos de personagens do universo Star Wars utilizando uma estrutura hierárquica de classes. Cada tipo de personagem possui características específicas enquanto compartilha atributos comuns através de herança.

## 🏗️ Estrutura do Projeto

```
src/
├── Main.java
└── entities/
    ├── Personagem.java      # Classe base (superclasse)
    ├── Sith.java           # Personagens do lado sombrio
    ├── Jedi.java           # Cavaleiros Jedi
    └── Droide.java         # Droides e robôs
```

## 🎯 Funcionalidades

### Classe Base: Personagem
- **Atributos comuns**: Nome, planeta de origem
- **Método**: `apresentar()` - exibe informações do personagem

### Classes Especializadas

#### 🔴 Sith
- **Atributo específico**: Título Sith (ex: "Lorde Sombrio dos Sith")
- **Características**: Personagens do lado sombrio da Força

#### 🔵 Jedi
- **Atributo específico**: Cor do sabre de luz
- **Características**: Cavaleiros e Mestres Jedi

#### 🤖 Droide
- **Atributo específico**: Tipo de droide
- **Características**: Robôs e inteligências artificiais

## 💻 Como Executar

### Pré-requisitos
- Java Development Kit (JDK) 8 ou superior
- IDE Java (IntelliJ IDEA, Eclipse, VS Code) ou terminal


## 🎮 Exemplo de Uso

```java
public class Main {
    public static void main(String[] args) {
        // Criando diferentes tipos de personagens
        Personagem um = new Sith("Darth Vader", "Tatooine", "Lorde Sombrio dos Sith");
        Personagem dois = new Droide("C3PO", "Tatooine", "droide de protocolo");
        Personagem tres = new Jedi("Obi Wan Kenobi", "Stewjon", "azul");
        
        // Polimorfismo em ação - mesmo método, comportamentos diferentes
        um.apresentar();    // Apresentação estilo Sith
        dois.apresentar();  // Apresentação estilo Droide
        tres.apresentar();  // Apresentação estilo Jedi
    }
}
```

## 🏛️ Conceitos de POO Demonstrados

### 1. **Herança**
- Todas as classes filhas herdam de `Personagem`
- Reutilização de código e atributos comuns

### 2. **Polimorfismo**
- Mesmo método `apresentar()` com comportamentos específicos
- Referências da superclasse apontam para objetos das subclasses

### 3. **Encapsulamento**
- Atributos privados com métodos de acesso
- Controle de como os dados são manipulados


