# Passagem por Valor vs Referência
## Demonstração Prática em C e Python

---

## 📋 Índice
- [Conceitos Fundamentais](#-conceitos-fundamentais)
- [Exemplos em C](#-exemplos-em-c)
- [Exemplos em Python](#-exemplos-em-python)
- [Comparação Prática](#-comparação-prática)
- [Casos Especiais](#-casos-especiais)
- [Resumo e Boas Práticas](#-resumo-e-boas-práticas)

---

## 🎯 Conceitos Fundamentais

### **Passagem por Valor**
- Uma **cópia** do valor é passada para a função
- Modificações na função **não afetam** a variável original
- Mais seguro, mas pode ser menos eficiente para dados grandes

### **Passagem por Referência**
- O **endereço de memória** é passado para a função
- Modificações na função **afetam** a variável original
- Mais eficiente, mas requer cuidado para evitar efeitos colaterais

---

## ⚡ Exemplos em C

### 1. 🔢 **Tipos Primitivos - Passagem por Valor**

```c
#include <stdio.h>

// Função que recebe valor por cópia
void tentarModificar(int numero) {
    printf("  Dentro da função - antes: %d\n", numero);
    numero = 999;  // Modifica apenas a CÓPIA local
    printf("  Dentro da função - depois: %d\n", numero);
}

// Função que recebe por referência (ponteiro)
void modificarPorReferencia(int *numero) {
    printf("  Dentro da função - antes: %d\n", *numero);
    *numero = 999;  // Modifica o valor ORIGINAL
    printf("  Dentro da função - depois: %d\n", *numero);
}

int main() {
    printf("=== PASSAGEM POR VALOR ===\n");
    int valor = 42;
    
    printf("Valor original: %d\n", valor);
    tentarModificar(valor);
    printf("Valor após função: %d\n\n", valor);  // Não mudou!
    
    printf("=== PASSAGEM POR REFERÊNCIA ===\n");
    printf("Valor original: %d\n", valor);
    modificarPorReferencia(&valor);  // Passa o ENDEREÇO
    printf("Valor após função: %d\n\n", valor);  // Mudou!
    
    return 0;
}
```

**Saída:**
```
=== PASSAGEM POR VALOR ===
Valor original: 42
  Dentro da função - antes: 42
  Dentro da função - depois: 999
Valor após função: 42

=== PASSAGEM POR REFERÊNCIA ===
Valor original: 42
  Dentro da função - antes: 42
  Dentro da função - depois: 999
Valor após função: 999
```

### 2. 📊 **Arrays - Sempre por Referência**

```c
#include <stdio.h>

// Arrays são SEMPRE passados por referência em C
void modificarArray(int arr[], int tamanho) {
    printf("  Modificando array dentro da função:\n");
    for (int i = 0; i < tamanho; i++) {
        arr[i] = arr[i] * 2;  // Modifica o array ORIGINAL
        printf("    arr[%d] = %d\n", i, arr[i]);
    }
}

// Função que tenta "modificar" o ponteiro (não funciona)
void tentarModificarPonteiro(int *arr) {
    printf("  Tentando modificar ponteiro...\n");
    arr = NULL;  // Modifica apenas a CÓPIA do ponteiro
    printf("  Ponteiro local agora é NULL\n");
}

// Função que modifica o ponteiro por referência
void modificarPonteiro(int **arr) {
    printf("  Modificando ponteiro por referência...\n");
    *arr = NULL;  // Modifica o ponteiro ORIGINAL
}

int main() {
    printf("=== ARRAYS EM C ===\n");
    int numeros[4] = {1, 2, 3, 4};
    
    printf("Array original: ");
    for (int i = 0; i < 4; i++) {
        printf("%d ", numeros[i]);
    }
    printf("\n");
    
    modificarArray(numeros, 4);
    
    printf("Array após função: ");
    for (int i = 0; i < 4; i++) {
        printf("%d ", numeros[i]);
    }
    printf("\n\n");
    
    printf("=== PONTEIROS ===\n");
    int *ptr = numeros;
    printf("Ponteiro antes: %p\n", (void*)ptr);
    
    tentarModificarPonteiro(ptr);
    printf("Ponteiro após tentativa: %p\n", (void*)ptr);  // Não mudou!
    
    modificarPonteiro(&ptr);
    printf("Ponteiro após modificação: %p\n\n", (void*)ptr);  // Mudou!
    
    return 0;
}
```

### 3. 🏗️ **Estruturas (structs)**

```c
#include <stdio.h>
#include <string.h>

typedef struct {
    char nome[50];
    int idade;
    float salario;
} Pessoa;

// Passagem por valor - cria uma CÓPIA da struct
void modificarPorValor(Pessoa p) {
    printf("  Modificando cópia da struct:\n");
    strcpy(p.nome, "João Silva");
    p.idade = 30;
    p.salario = 5000.0;
    printf("    Nome: %s, Idade: %d, Salário: %.2f\n", 
           p.nome, p.idade, p.salario);
}

// Passagem por referência - modifica a struct ORIGINAL
void modificarPorReferencia(Pessoa *p) {
    printf("  Modificando struct original:\n");
    strcpy(p->nome, "Maria Santos");
    p->idade = 25;
    p->salario = 4500.0;
    printf("    Nome: %s, Idade: %d, Salário: %.2f\n", 
           p->nome, p->idade, p->salario);
}

// Função que retorna uma struct (cópia)
Pessoa criarPessoa(const char *nome, int idade, float salario) {
    Pessoa nova;
    strcpy(nova.nome, nome);
    nova.idade = idade;
    nova.salario = salario;
    return nova;  // Retorna uma CÓPIA
}

int main() {
    printf("=== STRUCTS - PASSAGEM POR VALOR ===\n");
    Pessoa funcionario = {"Ana Costa", 28, 3500.0};
    
    printf("Original: %s, %d anos, R$ %.2f\n", 
           funcionario.nome, funcionario.idade, funcionario.salario);
    
    modificarPorValor(funcionario);
    
    printf("Após modificação por valor: %s, %d anos, R$ %.2f\n\n", 
           funcionario.nome, funcionario.idade, funcionario.salario);
    
    printf("=== STRUCTS - PASSAGEM POR REFERÊNCIA ===\n");
    printf("Original: %s, %d anos, R$ %.2f\n", 
           funcionario.nome, funcionario.idade, funcionario.salario);
    
    modificarPorReferencia(&funcionario);
    
    printf("Após modificação por referência: %s, %d anos, R$ %.2f\n\n", 
           funcionario.nome, funcionario.idade, funcionario.salario);
    
    printf("=== RETORNO DE STRUCT ===\n");
    Pessoa novaPessoa = criarPessoa("Carlos Lima", 35, 6000.0);
    printf("Nova pessoa: %s, %d anos, R$ %.2f\n", 
           novaPessoa.nome, novaPessoa.idade, novaPessoa.salario);
    
    return 0;
}
```

---

## 🐍 Exemplos em Python

### 1. 🔢 **Tipos Imutáveis - Comportamento "por Valor"**

```python
def tentar_modificar_imutalvel(numero, texto, tupla):
    """
    Tipos imutáveis: int, float, str, tuple, frozenset
    Comportam-se como passagem por valor
    """
    print(f"  Dentro da função - antes:")
    print(f"    numero: {numero}, texto: '{texto}', tupla: {tupla}")
    
    # Estas "modificações" criam NOVOS objetos
    numero = 999
    texto = "modificado"
    tupla = (99, 88)
    
    print(f"  Dentro da função - depois:")
    print(f"    numero: {numero}, texto: '{texto}', tupla: {tupla}")

def main_imutaveis():
    print("=== TIPOS IMUTÁVEIS (comportamento como valor) ===")
    
    num = 42
    txt = "original"
    tup = (1, 2, 3)
    
    print(f"Antes da função:")
    print(f"  numero: {num}, texto: '{txt}', tupla: {tup}")
    
    tentar_modificar_imutalvel(num, txt, tup)
    
    print(f"Depois da função:")
    print(f"  numero: {num}, texto: '{txt}', tupla: {tup}")
    print("  → Valores NÃO mudaram (imutáveis)\n")

# Executar exemplo
main_imutaveis()
```

### 2. 📋 **Tipos Mutáveis - Passagem por Referência**

```python
def modificar_mutaveis(lista, dicionario, conjunto):
    """
    Tipos mutáveis: list, dict, set, objetos customizados
    São passados por referência
    """
    print(f"  Dentro da função - antes:")
    print(f"    lista: {lista}")
    print(f"    dict: {dicionario}")
    print(f"    set: {conjunto}")
    
    # Estas modificações afetam os objetos ORIGINAIS
    lista.append(99)
    lista[0] = "modificado"
    
    dicionario["nova_chave"] = "novo_valor"
    dicionario["nome"] = "João"
    
    conjunto.add("novo_item")
    conjunto.discard("item1")
    
    print(f"  Dentro da função - depois:")
    print(f"    lista: {lista}")
    print(f"    dict: {dicionario}")
    print(f"    set: {conjunto}")

def tentar_reatribuir(lista):
    """
    Reatribuição não afeta a variável original
    """
    print(f"  Tentando reatribuir lista: {lista}")
    lista = [99, 88, 77]  # Cria nova lista local
    print(f"  Nova lista local: {lista}")

def main_mutaveis():
    print("=== TIPOS MUTÁVEIS (passagem por referência) ===")
    
    minha_lista = [1, 2, 3]
    meu_dict = {"nome": "Ana", "idade": 25}
    meu_set = {"item1", "item2", "item3"}
    
    print(f"Antes da função:")
    print(f"  lista: {minha_lista}")
    print(f"  dict: {meu_dict}")
    print(f"  set: {meu_set}")
    
    modificar_mutaveis(minha_lista, meu_dict, meu_set)
    
    print(f"Depois da função:")
    print(f"  lista: {minha_lista}")
    print(f"  dict: {meu_dict}")
    print(f"  set: {meu_set}")
    print("  → Valores MUDARAM (mutáveis)\n")
    
    print("=== REATRIBUIÇÃO ===")
    print(f"Lista antes da reatribuição: {minha_lista}")
    tentar_reatribuir(minha_lista)
    print(f"Lista depois da reatribuição: {minha_lista}")
    print("  → Lista original NÃO mudou (reatribuição é local)\n")

# Executar exemplo
main_mutaveis()
```

### 3. 🏗️ **Classes e Objetos**

```python
class Pessoa:
    def __init__(self, nome, idade, salario):
        self.nome = nome
        self.idade = idade
        self.salario = salario
    
    def __str__(self):
        return f"{self.nome}, {self.idade} anos, R$ {self.salario:.2f}"

def modificar_objeto(pessoa):
    """
    Objetos são sempre passados por referência
    """
    print(f"  Modificando objeto pessoa...")
    pessoa.nome = "Nome Modificado"
    pessoa.idade = 99
    pessoa.salario = 9999.99

def reatribuir_objeto(pessoa):
    """
    Reatribuição cria nova referência local
    """
    print(f"  Reatribuindo objeto pessoa...")
    pessoa = Pessoa("Novo Objeto", 0, 0.0)
    print(f"  Objeto local: {pessoa}")

def modificar_atributos_lista(pessoa):
    """
    Modificando lista que é atributo do objeto
    """
    print(f"  Adicionando habilidades...")
    pessoa.habilidades.append("Python")
    pessoa.habilidades.append("JavaScript")

def main_objetos():
    print("=== OBJETOS E CLASSES ===")
    
    funcionario = Pessoa("Maria Silva", 30, 4500.0)
    funcionario.habilidades = ["Java", "C++"]
    
    print(f"Objeto original: {funcionario}")
    print(f"Habilidades: {funcionario.habilidades}")
    
    print("\n--- Modificando atributos ---")
    modificar_objeto(funcionario)
    print(f"Após modificação: {funcionario}")
    print("  → Objeto MUDOU (passagem por referência)")
    
    print("\n--- Tentando reatribuir ---")
    print(f"Antes reatribuição: {funcionario}")
    reatribuir_objeto(funcionario)
    print(f"Após reatribuição: {funcionario}")
    print("  → Objeto original NÃO mudou (reatribuição é local)")
    
    print("\n--- Modificando lista do objeto ---")
    print(f"Habilidades antes: {funcionario.habilidades}")
    modificar_atributos_lista(funcionario)
    print(f"Habilidades depois: {funcionario.habilidades}")
    print("  → Lista MUDOU (mutável dentro do objeto)\n")

# Executar exemplo
main_objetos()
```

### 4. 🔄 **Cópia vs Referência Explícita**

```python
import copy

def demonstrar_copias():
    print("=== CÓPIA SHALLOW vs DEEP ===")
    
    # Lista aninhada original
    original = [[1, 2], [3, 4], [5, 6]]
    print(f"Lista original: {original}")
    
    # Atribuição simples (mesma referência)
    referencia = original
    
    # Cópia shallow (copia apenas o primeiro nível)
    copia_shallow = copy.copy(original)
    # ou: copia_shallow = original.copy()
    # ou: copia_shallow = list(original)
    
    # Cópia deep (copia recursivamente)
    copia_deep = copy.deepcopy(original)
    
    print("\n--- Modificando lista original ---")
    original[0][0] = 999  # Modifica lista interna
    original.append([7, 8])  # Adiciona nova lista
    
    print(f"Original após modificação: {original}")
    print(f"Referência: {referencia}")
    print(f"Cópia shallow: {copia_shallow}")
    print(f"Cópia deep: {copia_deep}")
    
    print("\nAnálise:")
    print("  → Referência: mudou tudo (mesma referência)")
    print("  → Shallow: mudou lista interna, não adicionou nova")
    print("  → Deep: não mudou nada (cópia completa)\n")

# Executar exemplo
demonstrar_copias()
```

---

## 🔄 Comparação Prática

### **Exemplo: Função de Troca (Swap)**

#### **C - Diferentes Abordagens**

```c
#include <stdio.h>

// ❌ INCORRETO - Passagem por valor
void swap_incorreto(int a, int b) {
    printf("  Dentro função (antes): a=%d, b=%d\n", a, b);
    int temp = a;
    a = b;
    b = temp;
    printf("  Dentro função (depois): a=%d, b=%d\n", a, b);
}

// ✅ CORRETO - Passagem por referência
void swap_correto(int *a, int *b) {
    printf("  Dentro função (antes): *a=%d, *b=%d\n", *a, *b);
    int temp = *a;
    *a = *b;
    *b = temp;
    printf("  Dentro função (depois): *a=%d, *b=%d\n", *a, *b);
}

int main() {
    printf("=== FUNÇÃO SWAP EM C ===\n");
    
    int x = 10, y = 20;
    
    printf("Valores originais: x=%d, y=%d\n", x, y);
    
    printf("\n--- Swap incorreto (por valor) ---\n");
    swap_incorreto(x, y);
    printf("Após swap incorreto: x=%d, y=%d\n", x, y);
    
    printf("\n--- Swap correto (por referência) ---\n");
    swap_correto(&x, &y);
    printf("Após swap correto: x=%d, y=%d\n", x, y);
    
    return 0;
}
```

#### **Python - Diferentes Estratégias**

```python
def swap_nao_funciona(a, b):
    """❌ Não funciona - reatribuição é local"""
    print(f"  Dentro função (antes): a={a}, b={b}")
    a, b = b, a  # Apenas variáveis locais
    print(f"  Dentro função (depois): a={a}, b={b}")
    return a, b  # Precisa retornar

def swap_lista(lista, i, j):
    """✅ Funciona - modifica lista mutável"""
    print(f"  Trocando posições {i} e {j}")
    print(f"  Antes: lista[{i}]={lista[i]}, lista[{j}]={lista[j]}")
    lista[i], lista[j] = lista[j], lista[i]
    print(f"  Depois: lista[{i}]={lista[i]}, lista[{j}]={lista[j]}")

def swap_objeto(obj1, obj2, atributo):
    """✅ Funciona - modifica atributos de objetos"""
    print(f"  Trocando atributo '{atributo}'")
    valor1 = getattr(obj1, atributo)
    valor2 = getattr(obj2, atributo)
    print(f"  Antes: obj1.{atributo}={valor1}, obj2.{atributo}={valor2}")
    setattr(obj1, atributo, valor2)
    setattr(obj2, atributo, valor1)
    print(f"  Depois: obj1.{atributo}={getattr(obj1, atributo)}, obj2.{atributo}={getattr(obj2, atributo)}")

class Exemplo:
    def __init__(self, valor):
        self.valor = valor

def main_swap_python():
    print("=== FUNÇÃO SWAP EM PYTHON ===")
    
    # Tentativa com variáveis simples
    x, y = 10, 20
    print(f"Valores originais: x={x}, y={y}")
    
    print("\n--- Swap que não funciona ---")
    swap_nao_funciona(x, y)
    print(f"Após tentativa: x={x}, y={y}")
    print("  → Não funcionou! Precisa usar o retorno:")
    x, y = swap_nao_funciona(x, y)
    print(f"Usando retorno: x={x}, y={y}")
    
    # Com lista (funciona!)
    print("\n--- Swap em lista ---")
    numeros = [100, 200, 300]
    print(f"Lista original: {numeros}")
    swap_lista(numeros, 0, 2)
    print(f"Lista após swap: {numeros}")
    
    # Com objetos (funciona!)
    print("\n--- Swap em objetos ---")
    obj1 = Exemplo(111)
    obj2 = Exemplo(222)
    print(f"Objetos originais: obj1.valor={obj1.valor}, obj2.valor={obj2.valor}")
    swap_objeto(obj1, obj2, 'valor')
    print(f"Objetos após swap: obj1.valor={obj1.valor}, obj2.valor={obj2.valor}")

# Executar exemplo
main_swap_python()
```

---

## ⚠️ Casos Especiais

### **1. Python - Reatribuição de Parâmetros**

```python
def exemplo_reatribuicao():
    print("=== REATRIBUIÇÃO EM PYTHON ===")
    
    def modificar_lista_metodo(lista):
        """Modificação através de métodos - afeta original"""
        lista.append(999)
        lista[0] = "modificado"
    
    def reatribuir_lista(lista):
        """Reatribuição - NÃO afeta original"""
        lista = [888, 777, 666]
        return lista
    
    def modificar_depois_reatribuir(lista):
        """Reatribui E modifica - só modificação local"""
        lista = [111, 222]  # Nova lista local
        lista.append(333)   # Modifica apenas lista local
        return lista
    
    original = [1, 2, 3]
    print(f"Lista original: {original}")
    
    # Modificação por método
    modificar_lista_metodo(original)
    print(f"Após modificação por método: {original}")
    
    # Reatribuição (sem usar retorno)
    reatribuir_lista(original)
    print(f"Após reatribuição (sem usar retorno): {original}")
    
    # Reatribuição (usando retorno)
    nova_lista = reatribuir_lista(original)
    print(f"Nova lista do retorno: {nova_lista}")
    
    # Modificação após reatribuição
    resultado = modificar_depois_reatribuir(original)
    print(f"Original após função complexa: {original}")
    print(f"Resultado da função: {resultado}")

exemplo_reatribuicao()
```

### **2. C - Ponteiros para Ponteiros**

```c
#include <stdio.h>
#include <stdlib.h>

void alocar_memoria_incorreto(int *ptr, int tamanho) {
    // ❌ Modifica apenas a CÓPIA do ponteiro
    ptr = (int*)malloc(tamanho * sizeof(int));
    if (ptr != NULL) {
        ptr[0] = 42;
        printf("  Memória alocada (cópia local): %p\n", (void*)ptr);
    }
}

void alocar_memoria_correto(int **ptr, int tamanho) {
    // ✅ Modifica o ponteiro ORIGINAL
    *ptr = (int*)malloc(tamanho * sizeof(int));
    if (*ptr != NULL) {
        (*ptr)[0] = 42;
        printf("  Memória alocada (original): %p\n", (void*)*ptr);
    }
}

int main() {
    printf("=== PONTEIROS PARA PONTEIROS ===\n");
    
    int *meu_ptr = NULL;
    printf("Ponteiro original: %p\n", (void*)meu_ptr);
    
    printf("\n--- Alocação incorreta ---\n");
    alocar_memoria_incorreto(meu_ptr, 5);
    printf("Ponteiro após função incorreta: %p\n", (void*)meu_ptr);
    
    printf("\n--- Alocação correta ---\n");
    alocar_memoria_correto(&meu_ptr, 5);
    printf("Ponteiro após função correta: %p\n", (void*)meu_ptr);
    
    if (meu_ptr != NULL) {
        printf("Valor no ponteiro: %d\n", meu_ptr[0]);
        free(meu_ptr);
    }
    
    return 0;
}
```

---

## 📊 Resumo e Boas Práticas

### **🔍 Tabela Comparativa**

| Aspecto | C | Python |
|---------|---|--------|
| **Tipos Primitivos** | Por valor (cópia) | Por valor (imutáveis) |
| **Arrays/Listas** | Por referência | Por referência (mutáveis) |
| **Structs/Objetos** | Por valor (padrão) | Por referência |
| **Ponteiros** | Explícitos (`*`, `&`) | Implícitos (transparentes) |
| **Controle** | Manual (programador) | Automático (linguagem) |

### **✅ Boas Práticas**

#### **Em C:**
```c
// ✅ Use const para parâmetros que não devem ser modificados
void imprimir_array(const int arr[], int tamanho);

// ✅ Use ponteiros para modificar valores
void modificar_valor(int *valor);

// ✅ Use ponteiros para ponteiros quando necessário
void alocar_memoria(int **ptr, int tamanho);

// ✅ Documente se a função modifica os parâmetros
void ordenar_array(int arr[], int tamanho);  // Modifica arr
int calcular_soma(const int arr[], int tamanho);  // Não modifica arr
```

#### **Em Python:**
```python
# ✅ Use cópias quando não quiser modificar o original
def processar_lista(lista):
    lista_local = lista.copy()  # ou list(lista)
    # ... modificar lista_local

# ✅ Documente efeitos colaterais
def adicionar_item(lista, item):
    """Adiciona item à lista (MODIFICA a lista original)"""
    lista.append(item)

def criar_nova_lista(lista, item):
    """Retorna nova lista com item adicionado (NÃO modifica original)"""
    return lista + [item]

# ✅ Use type hints para clareza
from typing import List

def modificar_lista(numeros: List[int]) -> None:
    """Indica que modifica a lista e não retorna nada"""
    numeros.sort()

def criar_lista_ordenada(numeros: List[int]) -> List[int]:
    """Indica que retorna nova lista"""
    return sorted(numeros)
```

### **🎯 Regras Gerais**

1. **📋 Documente comportamento**: Sempre deixe claro se a função modifica os parâmetros
2. **🔒 Use const/imutável**: Para parâmetros que não devem ser modificados
3. **📦 Prefira retorno**: Em vez de modificar parâmetros, considere retornar novos valores
4. **⚠️ Cuidado com mutáveis**: Em Python, listas/dicts são modificados por padrão
5. **🧠 Entenda a linguagem**: C é explícito, Python é mais implícito mas previsível

---

<div align="center">
  <strong>🎓 Compreender valor vs referência é fundamental para programação eficiente e sem bugs!</strong>
</div>
