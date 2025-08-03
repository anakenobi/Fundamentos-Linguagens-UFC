# Pilha de Chamadas 

A **pilha de chamadas** é uma estrutura de dados que o Python usa para rastrear as chamadas de função durante a execução do programa. Quando uma função é chamada, suas informações são "empilhadas" no topo da pilha, e quando ela termina, são "desempilhadas".

## Exemplo: Fatorial Recursivo

```python
def fatorial(n):
    print(f"Chamando fatorial({n})")
    
    # Caso base - condição de parada
    if n <= 1:
        print(f"Caso base atingido: fatorial({n}) = 1")
        return 1
    
    # Chamada recursiva
    resultado = n * fatorial(n - 1)
    print(f"Retornando de fatorial({n}) = {resultado}")
    return resultado

# Exemplo de uso
print("=== Calculando fatorial(4) ===")
resultado_final = fatorial(4)
print(f"Resultado final: {resultado_final}")
```

### Saída do programa:
```
=== Calculando fatorial(4) ===
Chamando fatorial(4)
Chamando fatorial(3)
Chamando fatorial(2)
Chamando fatorial(1)
Caso base atingido: fatorial(1) = 1
Retornando de fatorial(2) = 2
Retornando de fatorial(3) = 6
Retornando de fatorial(4) = 24
Resultado final: 24
```

## Visualização da Pilha de Chamadas

### Estado da pilha durante `fatorial(4)`:

```
Pilha de Chamadas (crescendo para cima):
┌─────────────────┐
│ fatorial(1)     │  ← Topo (última chamada)
├─────────────────┤
│ fatorial(2)     │
├─────────────────┤
│ fatorial(3)     │
├─────────────────┤
│ fatorial(4)     │  ← Base (primeira chamada)
└─────────────────┘
```

### Sequência detalhada de eventos:

1. **`fatorial(4)` é chamada** → empilhada
   - Variáveis locais: `n = 4`
   - Precisa calcular: `4 * fatorial(3)`

2. **`fatorial(3)` é chamada** → empilhada sobre `fatorial(4)`
   - Variáveis locais: `n = 3`
   - Precisa calcular: `3 * fatorial(2)`

3. **`fatorial(2)` é chamada** → empilhada sobre `fatorial(3)`
   - Variáveis locais: `n = 2`
   - Precisa calcular: `2 * fatorial(1)`

4. **`fatorial(1)` é chamada** → empilhada sobre `fatorial(2)`
   - Variáveis locais: `n = 1`
   - **Caso base atingido!** Retorna `1`

5. **`fatorial(1)` retorna 1** → desempilhada
   - A pilha volta para `fatorial(2)`

6. **`fatorial(2)` calcula `2 × 1 = 2`** → desempilhada
   - A pilha volta para `fatorial(3)`

7. **`fatorial(3)` calcula `3 × 2 = 6`** → desempilhada
   - A pilha volta para `fatorial(4)`

8. **`fatorial(4)` calcula `4 × 6 = 24`** → desempilhada
   - Pilha vazia, retorna resultado final

## Versão com Visualização de Níveis

```python
def fatorial_visual(n, nivel=0):
    indent = "  " * nivel  # Indentação baseada no nível
    print(f"{indent}→ Entrando em fatorial({n})")
    
    if n <= 1:
        print(f"{indent}  Caso base: fatorial({n}) = 1")
        print(f"{indent}← Saindo de fatorial({n}) com resultado 1")
        return 1
    
    print(f"{indent}  Fazendo chamada recursiva: fatorial({n-1})")
    resultado = n * fatorial_visual(n - 1, nivel + 1)
    
    print(f"{indent}  Calculando: {n} × {resultado//n} = {resultado}")
    print(f"{indent}← Saindo de fatorial({n}) com resultado {resultado}")
    return resultado

# Exemplo de uso
print("Calculando fatorial(4) com visualização da pilha:")
fatorial_visual(4)
```

### Saída com indentação:
```
Calculando fatorial(4) com visualização da pilha:
→ Entrando em fatorial(4)
  Fazendo chamada recursiva: fatorial(3)
  → Entrando em fatorial(3)
    Fazendo chamada recursiva: fatorial(2)
    → Entrando em fatorial(2)
      Fazendo chamada recursiva: fatorial(1)
      → Entrando em fatorial(1)
        Caso base: fatorial(1) = 1
      ← Saindo de fatorial(1) com resultado 1
      Calculando: 2 × 1 = 2
    ← Saindo de fatorial(2) com resultado 2
    Calculando: 3 × 2 = 6
  ← Saindo de fatorial(3) com resultado 6
  Calculando: 4 × 6 = 24
← Saindo de fatorial(4) com resultado 24
```

## Características Importantes da Pilha de Chamadas

### 1. **LIFO (Last In, First Out)**
- A última função chamada é a primeira a retornar
- As funções são "desempilhadas" na ordem inversa de como foram "empilhadas"

### 2. **Contexto Preservado**
- Cada chamada mantém suas próprias variáveis locais
- As variáveis de `fatorial(4)` não interferem nas de `fatorial(3)`

### 3. **Limite de Profundidade**
- Python tem um limite padrão de ~1000 chamadas recursivas
- Previne **stack overflow** em recursões infinitas

### 4. **Memória por Frame**
- Cada chamada de função ocupa espaço na memória
- Recursões muito profundas podem esgotar a memória

## Exemplo de Stack Overflow

```python
import sys

def recursao_infinita(n):
    print(f"Chamada {n}")
    return recursao_infinita(n + 1)  # Sem caso base!

# Isto causará um erro de RecursionError
try:
    recursao_infinita(1)
except RecursionError as e:
    print(f"Erro: {e}")
    print(f"Limite atual de recursão: {sys.getrecursionlimit()}")
```

A pilha de chamadas é fundamental para entender como a recursão funciona e por que ela é uma ferramenta poderosa para resolver problemas que podem ser divididos em subproblemas menores e similares.
