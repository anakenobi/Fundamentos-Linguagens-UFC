# MiniLang - Linguagem de Programação Fictícia

## Visão Geral
MiniLang é uma linguagem simples com variáveis, operações aritméticas, condicionais e loops básicos.

## Gramática BNF (Backus-Naur Form)

```bnf
<programa> ::= <declaracao>*

<declaracao> ::= <declaracao_variavel>
               | <atribuicao>
               | <condicional>
               | <loop>
               | <expressao> ";"

<declaracao_variavel> ::= "var" <identificador> "=" <expressao> ";"

<atribuicao> ::= <identificador> "=" <expressao> ";"

<condicional> ::= "se" "(" <expressao> ")" "{" <declaracao>* "}"
                | "se" "(" <expressao> ")" "{" <declaracao>* "}" "senao" "{" <declaracao>* "}"

<loop> ::= "enquanto" "(" <expressao> ")" "{" <declaracao>* "}"

<expressao> ::= <termo> (("+" | "-") <termo>)*

<termo> ::= <fator> (("*" | "/") <fator>)*

<fator> ::= <numero>
          | <identificador>
          | "(" <expressao> ")"

<identificador> ::= <letra> (<letra> | <digito>)*

<numero> ::= <digito>+ ("." <digito>+)?

<letra> ::= "a" | "b" | ... | "z" | "A" | "B" | ... | "Z"

<digito> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
```

## Tokens da Linguagem

### Palavras-chave (Keywords)
- `var` - declaração de variável
- `se` - condicional if
- `senao` - condicional else
- `enquanto` - loop while

### Operadores
- `=` - atribuição
- `+` - adição
- `-` - subtração
- `*` - multiplicação
- `/` - divisão

### Delimitadores
- `(` `)` - parênteses
- `{` `}` - chaves
- `;` - ponto e vírgula

### Literais
- **NUMERO**: sequência de dígitos com ponto decimal opcional
- **IDENTIFICADOR**: sequência de letras e dígitos começando com letra

## Exemplo de Código MiniLang

```minilang
var idade = 25;
var nome = joao;
var salario = 1500.50;

se (idade > 18) {
    salario = salario * 1.1;
    var bonus = 200;
}

enquanto (salario < 2000) {
    salario = salario + 100;
}
```

## Análise Léxica - Exemplo Detalhado

### Código de Entrada:
```minilang
var x = 10 + 5;
se (x > 12) {
    x = x - 2;
}
```

### Processo de Tokenização:

#### Passo a Passo:

**Linha 1: `var x = 10 + 5;`**

| Posição | Caractere(s) | Token Identificado | Tipo do Token |
|---------|--------------|-------------------|---------------|
| 0-2     | `var`        | var               | KEYWORD       |
| 4       | `x`          | x                 | IDENTIFIER    |
| 6       | `=`          | =                 | ASSIGN        |
| 8-9     | `10`         | 10                | NUMBER        |
| 11      | `+`          | +                 | PLUS          |
| 13      | `5`          | 5                 | NUMBER        |
| 14      | `;`          | ;                 | SEMICOLON     |

**Linha 2: `se (x > 12) {`**

| Posição | Caractere(s) | Token Identificado | Tipo do Token |
|---------|--------------|-------------------|---------------|
| 0-1     | `se`         | se                | KEYWORD       |
| 3       | `(`          | (                 | LPAREN        |
| 4       | `x`          | x                 | IDENTIFIER    |
| 6       | `>`          | >                 | GREATER       |
| 8-9     | `12`         | 12                | NUMBER        |
| 10      | `)`          | )                 | RPAREN        |
| 12      | `{`          | {                 | LBRACE        |

**Linha 3: `    x = x - 2;`**

| Posição | Caractere(s) | Token Identificado | Tipo do Token |
|---------|--------------|-------------------|---------------|
| 4       | `x`          | x                 | IDENTIFIER    |
| 6       | `=`          | =                 | ASSIGN        |
| 8       | `x`          | x                 | IDENTIFIER    |
| 10      | `-`          | -                 | MINUS         |
| 12      | `2`          | 2                 | NUMBER        |
| 13      | `;`          | ;                 | SEMICOLON     |

**Linha 4: `}`**

| Posição | Caractere(s) | Token Identificado | Tipo do Token |
|---------|--------------|-------------------|---------------|
| 0       | `}`          | }                 | RBRACE        |

### Lista Final de Tokens:

```
[
  (KEYWORD, "var", linha=1, coluna=0),
  (IDENTIFIER, "x", linha=1, coluna=4),
  (ASSIGN, "=", linha=1, coluna=6),
  (NUMBER, "10", linha=1, coluna=8),
  (PLUS, "+", linha=1, coluna=11),
  (NUMBER, "5", linha=1, coluna=13),
  (SEMICOLON, ";", linha=1, coluna=14),
  (KEYWORD, "se", linha=2, coluna=0),
  (LPAREN, "(", linha=2, coluna=3),
  (IDENTIFIER, "x", linha=2, coluna=4),
  (GREATER, ">", linha=2, coluna=6),
  (NUMBER, "12", linha=2, coluna=8),
  (RPAREN, ")", linha=2, coluna=10),
  (LBRACE, "{", linha=2, coluna=12),
  (IDENTIFIER, "x", linha=3, coluna=4),
  (ASSIGN, "=", linha=3, coluna=6),
  (IDENTIFIER, "x", linha=3, coluna=8),
  (MINUS, "-", linha=3, coluna=10),
  (NUMBER, "2", linha=3, coluna=12),
  (SEMICOLON, ";", linha=3, coluna=13),
  (RBRACE, "}", linha=4, coluna=0)
]
```

## Tratamento de Espaços e Comentários

### Caracteres Ignorados:
- Espaços em branco (` `)
- Tabulações (`\t`)
- Quebras de linha (`\n`, `\r`)

### Comentários (extensão futura):
```minilang
// Este é um comentário de linha
var x = 5; // comentário no final da linha

/* 
   Este é um comentário
   de múltiplas linhas
*/
```

## Implementação do Analisador Léxico (Pseudocódigo)

```python
def analisar_lexicamente(codigo):
    tokens = []
    i = 0
    linha = 1
    coluna = 0
    
    while i < len(codigo):
        char = codigo[i]
        
        # Ignorar espaços
        if char in [' ', '\t']:
            i += 1
            coluna += 1
            continue
            
        # Nova linha
        if char == '\n':
            linha += 1
            coluna = 0
            i += 1
            continue
            
        # Números
        if char.isdigit():
            numero = ''
            while i < len(codigo) and (codigo[i].isdigit() or codigo[i] == '.'):
                numero += codigo[i]
                i += 1
                coluna += 1
            tokens.append(('NUMBER', numero, linha, coluna - len(numero)))
            continue
            
        # Identificadores e palavras-chave
        if char.isalpha():
            identificador = ''
            while i < len(codigo) and (codigo[i].isalnum() or codigo[i] == '_'):
                identificador += codigo[i]
                i += 1
                coluna += 1
            
            if identificador in ['var', 'se', 'senao', 'enquanto']:
                tokens.append(('KEYWORD', identificador, linha, coluna - len(identificador)))
            else:
                tokens.append(('IDENTIFIER', identificador, linha, coluna - len(identificador)))
            continue
            
        # Operadores e delimitadores
        operadores = {
            '=': 'ASSIGN', '+': 'PLUS', '-': 'MINUS',
            '*': 'MULTIPLY', '/': 'DIVIDE', '>': 'GREATER',
            '(': 'LPAREN', ')': 'RPAREN', '{': 'LBRACE',
            '}': 'RBRACE', ';': 'SEMICOLON'
        }
        
        if char in operadores:
            tokens.append((operadores[char], char, linha, coluna))
            i += 1
            coluna += 1
            continue
            
        # Caractere não reconhecido
        raise ErroLexico(f"Caractere não reconhecido '{char}' na linha {linha}, coluna {coluna}")
    
    return tokens
```

