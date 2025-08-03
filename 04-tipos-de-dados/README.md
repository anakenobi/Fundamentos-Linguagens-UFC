# Comparação de Tipagem: Python vs C vs JavaScript

## Resumo dos Sistemas de Tipagem

| Característica | Python | C | JavaScript |
|----------------|--------|---|------------|
| **Tipo** | Dinâmica | Estática | Dinâmica |
| **Força** | Forte | Forte | Fraca |
| **Verificação** | Runtime | Compile-time | Runtime |
| **Declaração** | Implícita | Explícita | Implícita |

---

## 1. 🐍 Python - Tipagem Dinâmica e Forte

### Características:
- **Dinâmica**: tipos determinados em tempo de execução
- **Forte**: não permite conversões implícitas perigosas
- **Duck Typing**: "se anda como pato e faz quack como pato, é um pato"

```python
# Variável pode mudar de tipo durante execução
x = 42          # int
print(type(x))  # <class 'int'>

x = "hello"     # agora é string
print(type(x))  # <class 'str'>

x = [1, 2, 3]   # agora é lista
print(type(x))  # <class 'list'>

# Tipagem forte - não permite conversões perigosas
numero = 5
texto = "10"
# resultado = numero + texto  # TypeError! Não converte automaticamente

# Conversão deve ser explícita
resultado = numero + int(texto)  # 15

# Duck Typing em ação
class Pato:
    def fazer_som(self):
        return "Quack!"

class Cachorro:
    def fazer_som(self):
        return "Au au!"

def animal_fala(animal):
    # Não importa o tipo, apenas se tem o método
    return animal.fazer_som()

pato = Pato()
cachorro = Cachorro()
print(animal_fala(pato))      # "Quack!"
print(animal_fala(cachorro))  # "Au au!"

# Type Hints (Python 3.5+) - opcional, apenas documentação
def somar(a: int, b: int) -> int:
    return a + b

# Ainda aceita outros tipos em runtime
print(somar(1.5, 2.5))  # 4.0 (funciona, mas type checker avisa)
```

---

## 2. ⚡ C - Tipagem Estática e Forte

### Características:
- **Estática**: tipos verificados em tempo de compilação
- **Forte**: conversões explícitas necessárias (com exceções)
- **Declaração obrigatória**: todo tipo deve ser declarado

```c
#include <stdio.h>

int main() {
    // Declaração obrigatória com tipo específico
    int idade = 25;
    float salario = 1500.50f;
    char inicial = 'J';
    char nome[50] = "João";
    
    printf("Idade: %d\n", idade);
    printf("Salário: %.2f\n", salario);
    
    // Erro de compilação se tentar mudar tipo
    // idade = "vinte e cinco";  // ERRO: incompatible types
    
    // Conversões devem ser explícitas (cast)
    float resultado = (float)idade + salario;
    printf("Resultado: %.2f\n", resultado);
    
    // Conversão implícita permitida em alguns casos
    int valor_truncado = salario;  // 1500 (perde decimais)
    printf("Truncado: %d\n", valor_truncado);
    
    // Ponteiros têm tipos específicos
    int *ptr_idade = &idade;
    // char *ptr_char = &idade;  // ERRO: incompatible pointer types
    
    // Arrays têm tamanho fixo
    int numeros[5] = {1, 2, 3, 4, 5};
    // numeros[10] = 42;  // Sem verificação de bounds! (perigoso)
    
    return 0;
}

// Funções com tipos específicos
int calcular_quadrado(int n) {
    return n * n;
}

// Sobrecarga não existe - nomes devem ser únicos
float calcular_quadrado_float(float n) {
    return n * n;
}
```

---

## 3. 🟨 JavaScript - Tipagem Dinâmica e Fraca

### Características:
- **Dinâmica**: tipos determinados em tempo de execução
- **Fraca**: permite conversões implícitas agressivas (coerção)
- **Flexível**: mesma variável pode ser qualquer tipo

```javascript
// Variáveis podem mudar de tipo livremente
let valor = 42;
console.log(typeof valor);  // "number"

valor = "hello";
console.log(typeof valor);  // "string"

valor = true;
console.log(typeof valor);  // "boolean"

valor = [1, 2, 3];
console.log(typeof valor);  // "object" (arrays são objects)

// TIPAGEM FRACA - Conversões implícitas (coerção)
console.log(5 + "3");       // "53" (number → string)
console.log("5" - 3);       // 2 (string → number)
console.log(true + 1);      // 2 (boolean → number)
console.log("5" * "2");     // 10 (ambos → number)

// Comparações com coerção (==) vs sem coerção (===)
console.log(5 == "5");      // true (coerção)
console.log(5 === "5");     // false (sem coerção)
console.log(0 == false);    // true (coerção)
console.log(0 === false);   // false (sem coerção)

// Valores "falsy" e "truthy"
if ("") console.log("string vazia é falsy");           // não executa
if ("hello") console.log("string não-vazia é truthy"); // executa
if (0) console.log("zero é falsy");                    // não executa
if (42) console.log("número não-zero é truthy");       // executa

// Objetos são dinâmicos
let pessoa = {
    nome: "Ana",
    idade: 30
};

// Pode adicionar propriedades em runtime
pessoa.email = "ana@email.com";
pessoa.falar = function() { return "Olá!"; };

console.log(pessoa.falar());  // "Olá!"

// Arrays podem conter tipos mistos
let misto = [1, "texto", true, {x: 42}, [1, 2]];
console.log(misto);

// Funções são objetos de primeira classe
function somar(a, b) {
    return a + b;  // funciona com qualquer tipo que suporte +
}

console.log(somar(1, 2));        // 3
console.log(somar("a", "b"));    // "ab"
console.log(somar(1, "2"));      // "12" (coerção!)

// TypeScript adiciona tipagem estática opcional
// function somarTS(a: number, b: number): number {
//     return a + b;
// }
```

---

## 4. 🔄 Comparação Prática: Mesmo Algoritmo

### Calculadora de IMC em todas as linguagens:

#### Python:
```python
def calcular_imc(peso, altura):
    """
    Tipagem dinâmica forte:
    - Aceita int ou float
    - Não converte strings automaticamente
    """
    return peso / (altura ** 2)

# Uso
imc = calcular_imc(70.5, 1.75)
print(f"IMC: {imc:.1f}")

# Com type hints (opcional)
def calcular_imc_tipado(peso: float, altura: float) -> float:
    return peso / (altura ** 2)
```

#### C:
```c
#include <stdio.h>

// Tipagem estática forte: tipos obrigatórios
float calcular_imc(float peso, float altura) {
    return peso / (altura * altura);
}

int main() {
    float peso = 70.5f;
    float altura = 1.75f;
    
    float imc = calcular_imc(peso, altura);
    printf("IMC: %.1f\n", imc);
    
    return 0;
}
```

#### JavaScript:
```javascript
// Tipagem dinâmica fraca: aceita qualquer coisa
function calcularIMC(peso, altura) {
    return peso / (altura * altura);
}

// Uso normal
let imc = calcularIMC(70.5, 1.75);
console.log(`IMC: ${imc.toFixed(1)}`);

// Coerção automática (pode dar problemas)
console.log(calcularIMC("70.5", "1.75"));  // 23.0 (strings → numbers)
console.log(calcularIMC("70kg", "1.75m")); // NaN (conversão falha)
```

---

## 5. ⚡ Vantagens e Desvantagens

### Python
**✅ Vantagens:**
- Flexibilidade sem sacrificar segurança
- Código mais conciso
- Duck typing permite polimorfismo natural

**❌ Desvantagens:**
- Erros de tipo só aparecem em runtime
- Performance menor
- Pode ser confuso em projetos grandes

### C
**✅ Vantagens:**
- Erros detectados em compilação
- Performance máxima
- Previsibilidade total

**❌ Desvantagens:**
- Verbosidade
- Menos flexibilidade
- Gerenciamento manual de memória

### JavaScript
**✅ Vantagens:**
- Extrema flexibilidade
- Prototipagem rápida
- Dinâmico e expressivo

**❌ Desvantagens:**
- Coerção pode causar bugs sutis
- Comportamentos inesperados
- Difícil de debugar em projetos grandes

---





